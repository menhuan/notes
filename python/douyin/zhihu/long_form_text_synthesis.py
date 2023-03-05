#!/usr/bin/env python
# coding: utf-8

# Copyright (c) Microsoft. All rights reserved.
# Licensed under the MIT license. See LICENSE.md file in the project root for full license information.

import copy
import json
import logging
import time
import re
import xml.etree.ElementTree as ET
from multiprocessing.pool import ThreadPool
from pathlib import Path
from typing import List, Tuple

import azure.cognitiveservices.speech as speechsdk
import nltk
from nltk.tokenize import sent_tokenize
from tqdm import tqdm

from synthesizer_pool import SynthesizerPool
import os 

# Only needed for first run
#punk_path = os.path.join( nltk.data.path[0],"tokenizers","punkt.zip")
#if not os.path.exists(punk_path):
nltk.download('punkt')

logger = logging.getLogger(__name__)
logger.setLevel("DEBUG")
punc = '~`!#$%^&*()_+-=|\';":/.,?><~·！@#￥%……&*（）——+-=“：’；、。，？》《{}'
print()

def s2hms(x):      # 把毫秒转为时分秒
    s ,hm =divmod(x,1000)
    m, s = divmod(s, 60)
    h, m = divmod(m, 60)
    hms = "%02d:%02d:%s.%s"%(h,m,int(s),str(int(hm)).zfill(3))
    hms = hms.replace('.',',')       # 把小数点改为逗号
    return hms



class LongTextSynthesizer:
    def __init__(self, subscription: str = "ebc688a500a948829e956abc2f9b13df", region: str ="japaneast" , language: str = 'english',
                 voice: str = 'en-US-JennyNeural', parallel_threads: int = 1) -> None:
        self.is_ssml = None
        self.subscription = subscription
        self.region = region
        self.language = language
        self.voice = voice
        self.parallel_threads = parallel_threads
        self.synthesizer_pool = SynthesizerPool(self._create_synthesizer, self.parallel_threads)
        self.speechSynthesizer = None
        

    def _create_synthesizer(self) -> speechsdk.SpeechSynthesizer:
        if self.speechSynthesizer is None:
            config = speechsdk.SpeechConfig(subscription=self.subscription, region=self.region)
            config.set_speech_synthesis_output_format(speechsdk.SpeechSynthesisOutputFormat.Audio24Khz48KBitRateMonoMp3)
            config.set_property(
                speechsdk.PropertyId.SpeechServiceResponse_RequestSentenceBoundary, 'true')
            config.speech_synthesis_voice_name = self.voice
            return speechsdk.SpeechSynthesizer(config, audio_config=None)
        return self.speechSynthesizer

    def synthesize_text_once(self, text: str) -> Tuple[speechsdk.SpeechSynthesisResult,
                                                       List[speechsdk.SpeechSynthesisWordBoundaryEventArgs]]:
        logger.info("Synthesis started %s", text)
        text_boundaries = []
        finished = []
        def word_boundary_cb(evt: speechsdk.SpeechSynthesisWordBoundaryEventArgs) -> None:
            text_boundaries.append(evt)

        with self.synthesizer_pool.borrow_synthesizer() as synthesizer:
            synthesizer.synthesis_word_boundary.connect(word_boundary_cb)
            synthesizer.synthesis_completed.connect(lambda _: finished.append(True))
            synthesizer.synthesis_canceled.connect(lambda _: finished.append(True))
            for _ in range(3):  # retry count
                text_boundaries = []
                finished = []
                result = synthesizer.speak_ssml_async(text).get() if self.is_ssml else \
                    synthesizer.speak_text_async(text).get()
                if result.reason == speechsdk.ResultReason.SynthesizingAudioCompleted:
                    logger.debug("Synthesis completed %s", text)
                    while not finished:
                        time.sleep(2)
                    return result, text_boundaries
                elif result.reason == speechsdk.ResultReason.Canceled:
                    cancellation_details = result.cancellation_details
                    logger.warning("Synthesis canceled, error details %s", cancellation_details.error_details)
                    time.sleep(5)
                    if cancellation_details.error_code in \
                        [speechsdk.CancellationErrorCode.ConnectionFailure,
                         speechsdk.CancellationErrorCode.ServiceUnavailable,
                         speechsdk.CancellationErrorCode.ServiceTimeout]:
                        logger.info("Synthesis canceled with connection failure, retrying.")
                        continue
                    break
                
            logger.error("Synthesizer failed to synthesize text")
            return None, None

    def synthesize_text(self, text: str = None, ssml_path: Path = None, output_path: Path = Path.cwd(),file_name:str =None) -> None:
        output_path.mkdir(parents=True, exist_ok=True)
        all_word_boundaries, all_sentence_boundaries = [], []
        if text is not None:
            sentences = self.split_text(text)
            self.is_ssml = False
        elif ssml_path is not None:
            sentences = self.read_and_split_ssml(ssml_path)
            self.is_ssml = True
        else:
            raise ValueError('Either text or ssml_path must be provided')
        offset = 0
        with ThreadPool(processes=self.parallel_threads) as pool:
            if file_name:
                audio_path = output_path / f'{file_name}.mp3'
            else:
                audio_path = output_path / 'audio.mp3'
            with audio_path.open("wb") as f:
                for result, text_boundaries in tqdm(
                        pool.imap(self.synthesize_text_once, sentences), total=len(sentences)):
                #for  sentence in sentences:
#                    result, text_boundaries = self.synthesize_text_once(sentence)      
                    if result is not None:
                        f.write(result.audio_data)
                        for text_boundary in text_boundaries:
                            text_boundary_dict = {
                                'audio_offset': offset + text_boundary.audio_offset / 10000,
                                'duration': text_boundary.duration.total_seconds() * 1000,
                                'text': text_boundary.text
                            }
                            if text_boundary.boundary_type == speechsdk.SpeechSynthesisBoundaryType.Sentence:
                                all_sentence_boundaries.append(text_boundary_dict)
                            else:
                                all_word_boundaries.append(text_boundary_dict)
                        # Calculate the offset for the next sentence,
                        offset += len(result.audio_data) / (48 / 8)
            # with (output_path / "word_boundaries.json").open("w", encoding="utf-8") as f:
            #     json.dump(all_word_boundaries, f, indent=4, ensure_ascii=False)
            # with (output_path / "sentence_boundaries.json").open("w", encoding="utf-8") as f:
            #     json.dump(all_sentence_boundaries, f, indent=4, ensure_ascii=False)
                
            with (output_path / f"{file_name}.srt").open("w", encoding="utf-8") as f:
                srt_content = [str(n+1)+'\n' + s2hms(i['audio_offset'])+' --> '+s2hms(i['audio_offset'] +i['duration'] )+'\n' + re.sub(r"[%s]+" %punc, "",i['text']) +'\n\n' for n,i in enumerate(all_sentence_boundaries)] # 序号+开始-->结束+内容
                f.writelines(srt_content)        

    
    def split_text(self, text: str) -> List[str]:
        sentences = sent_tokenize(text, language=self.language)
        logger.info(f'Splitting into {len(sentences)} sentences')
        logger.debug(sentences)
        return sentences

    @staticmethod
    def read_and_split_ssml(ssml_path: Path) -> List[str]:
        namespaces = dict([node for _, node in ET.iterparse(ssml_path, events=['start-ns'])])
        for ns in namespaces:
            ET.register_namespace(ns, namespaces[ns])
        root = ET.parse(ssml_path).getroot()
        sentences = []
        speak_element = copy.deepcopy(root)

        new_var = list(speak_element)
        for child in new_var:
            _, _, tag = child.tag.rpartition('}')
            if tag != 'voice':
                raise ValueError(f'Only voice element is supported, got {tag}')
            speak_element.remove(child)
        for child in root:
            single_voice = copy.deepcopy(speak_element)
            single_voice.append(child)
            sentences.append(ET.tostring(single_voice, encoding='unicode'))
        return sentences


if __name__ == "__main__":
    # result = s2hms(12842.0)
    # print(result)
    logging.basicConfig(level=logging.INFO)
    s = LongTextSynthesizer(subscription="9281010e1fe3468089c7d23c4d9b2f71", region="eastus2",parallel_threads=1)
    # with Path('/workspaces/notes/python/douyin/zhihu/Gatsby-chapter1.txt').open('r', encoding='utf-8') as r:
    #     s.synthesize_text(r.read(), output_path=Path('./gatsby'))
    s.synthesize_text(ssml_path=Path('/workspaces/notes/python/douyin/zhihu/multi-role.xml'), output_path=Path('/workspaces/notes/python/douyin/zhihu/multi-role'))
