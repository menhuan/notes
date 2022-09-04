'''
  For more samples please visit https://github.com/Azure-Samples/cognitive-services-speech-sdk 
'''

import os
import azure.cognitiveservices.speech as speechsdk

root_path = os.getenv(
    "ROOT_PATH", "/workspaces/notes/python/douyin/output/douyin/")
musics_path = os.path.join(root_path, os.getenv("MUSICS", "musics/"))

def output(txt_to_aideo,file_name):
    ssml = ""
    for txt in txt_to_aideo.split(','):
        ssml += f"""
           <voice name="zh-CN-XiaochenNeural">
           <mstts:silence  type="Tailing"  value="200ms"/>
           <prosody rate="45%" pitch="25%">
             {txt}
             </
        </prosody>
        </voice>
           """ 

    text =f"""
    <speak xmlns="http://www.w3.org/2001/10/synthesis" xmlns:mstts="http://www.w3.org/2001/mstts" xmlns:emo="http://www.w3.org/2009/10/emotionml" version="1.0" xml:lang="en-US">
            {ssml}
    </speak>
    """
    # Creates an instance of a speech config with specified subscription key and service region.
    speech_key =os.getenv("SUBSCRIPTION_KEY","") ,
    service_region = os.getenv("REGION","eastasia"),

    speech_config = speechsdk.SpeechConfig(subscription=speech_key, region=service_region)
    # Note: the voice setting will not overwrite the voice element in input SSML.
    speech_config.speech_synthesis_voice_name = "zh-CN-XiaochenNeural"
    music_path = os.path.join(musics_path,f"{file_name}.wav")
    audio_config = speechsdk.audio.AudioOutputConfig(filename=music_path)

    # use the default speaker as audio output.
    speech_synthesizer = speechsdk.SpeechSynthesizer(speech_config=speech_config,audio_config=audio_config)

    result = speech_synthesizer.speak_ssml_async(text).get()
    # Check result
    if result.reason == speechsdk.ResultReason.SynthesizingAudioCompleted:

        print("Speech synthesized for text [{}]".format(text))
    elif result.reason == speechsdk.ResultReason.Canceled:
        cancellation_details = result.cancellation_details
        print("Speech synthesis canceled: {}".format(cancellation_details.reason))
        if cancellation_details.reason == speechsdk.CancellationReason.Error:
            print("Error details: {}".format(cancellation_details.error_details))


