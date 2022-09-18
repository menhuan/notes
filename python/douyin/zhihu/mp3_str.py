
# 运行程序 python3 daily_writing_template.py
#!/usr/bin/python3

###公众号日更写作模板

import azure.cognitiveservices.speech as speechsdk
import os
import time
import pprint
import json
import srt
import datetime


f = open("test.txt",encoding = "utf-8")

#输出读取到的数据
txt_to_aideo = f.read()
# txt_to_aideo = '我爱你呀，你爱不爱我'

#关闭文件

f.close()

wav_file_name = "test"

text =f"""
    <speak xmlns="http://www.w3.org/2001/10/synthesis" xmlns:mstts="http://www.w3.org/2001/mstts" xmlns:emo="http://www.w3.org/2009/10/emotionml" version="1.0" xml:lang="en-US">
        <voice name="zh-CN-XiaoxiaoNeural"><prosody rate="45%" pitch="0%">
        {txt_to_aideo}
        </prosody></voice></speak>
    """


speech_key = "d99b109acc4b4e40a4ffc1490c16751b"
service_region = "eastasia"

speech_config = speechsdk.SpeechConfig(subscription=speech_key, region=service_region)
# Note: the voice setting will not overwrite the voice element in input SSML.
speech_config.speech_synthesis_voice_name = "zh-CN-XiaochenNeural"
audio_config = speechsdk.audio.AudioOutputConfig(filename=f"{wav_file_name}.wav")

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


 

###生成字幕文件
path = os.getcwd()
speech_config = speechsdk.SpeechConfig(subscription=speech_key, region=service_region)
audio_filename = wav_file_name  + '.wav'
audio_input = speechsdk.audio.AudioConfig(filename=audio_filename)
# speech_config.speech_recognition_language="en-US"
speech_config.speech_recognition_language="zh-CN"
# speech_config.speech_synthesis_voice_name = "zh-CN-XiaochenNeural"
# speech_config.speech_synthesis_voice_name = "zh-CN-XiaochenNeural"
speech_config.request_word_level_timestamps()

speech_config.enable_dictation()
speech_config.output_format = speechsdk.OutputFormat(1)

speech_recognizer = speechsdk.SpeechRecognizer(speech_config=speech_config, audio_config=audio_input)

#result = speech_recognizer.recognize_once()
all_results = []
results = []
transcript = []
words = []


#https://docs.microsoft.com/en-us/python/api/azure-cognitiveservices-speech/azure.cognitiveservices.speech.recognitionresult?view=azure-python
def handle_final_result(evt):
    import json
    all_results.append(evt.result.text) 
    results = json.loads(evt.result.json)
    transcript.append(results['DisplayText'])
    confidence_list_temp = [item.get('Confidence') for item in results['NBest']]
    max_confidence_index = confidence_list_temp.index(max(confidence_list_temp))
    words.extend(results['NBest'][max_confidence_index]['Words'])



done = False

def stop_cb(evt):
    print('CLOSING on {}'.format(evt))
    speech_recognizer.stop_continuous_recognition()
    global done
    done= True
    
speech_recognizer.recognized.connect(handle_final_result) 
speech_recognizer.recognizing.connect(lambda evt: print('RECOGNIZING: {}'.format(evt)))
speech_recognizer.recognized.connect(lambda evt: print('RECOGNIZED: {}'.format(evt)))
speech_recognizer.session_started.connect(lambda evt: print('SESSION STARTED: {}'.format(evt)))
speech_recognizer.session_stopped.connect(lambda evt: print('SESSION STOPPED {}'.format(evt)))
speech_recognizer.canceled.connect(lambda evt: print('CANCELED {}'.format(evt)))
# stop continuous recognition on either session stopped or canceled events
speech_recognizer.session_stopped.connect(stop_cb)
speech_recognizer.canceled.connect(stop_cb)

speech_recognizer.start_continuous_recognition()

while not done:
    time.sleep(.5)
    
print("Printing all results:")
print(all_results)

speech_to_text_response = words

def convertduration(t):
    x= t/10000
    return int((x / 1000)), (x % 1000)


##-- Code to Create Subtitle --#
#3 Seconds
bin = 3.0
duration = 0 
transcriptions = []
transcript = ""
index,prev=0,0
wordstartsec,wordstartmicrosec=0,0
for i in range(len(speech_to_text_response)):
    #Forms the sentence until the bin size condition is met
    transcript = transcript + " " + speech_to_text_response[i]["Word"]
    #Checks whether the elapsed duration is less than the bin size
    if(int((duration / 10000000)) < bin): 
        wordstartsec,wordstartmicrosec=convertduration(speech_to_text_response[i]["Offset"])
        duration= duration+speech_to_text_response[i]["Offset"]-prev
        prev=speech_to_text_response[i]["Offset"]
                #transcript = transcript + " " + speech_to_text_response[i]["Word"]
    else : 
        index=index+1
        #transcript = transcript + " " + speech_to_text_response[i]["Word"]
        transcriptions.append(srt.Subtitle(index, datetime.timedelta(0, wordstartsec, wordstartmicrosec), datetime.timedelta(0, wordstartsec+bin, 0), transcript))
        duration = 0 
        #print(transcript)
        transcript=""

transcriptions.append(srt.Subtitle(index, datetime.timedelta(0, wordstartsec, wordstartmicrosec), datetime.timedelta(0, wordstartsec+bin, 0), transcript))
subtitles = srt.compose(transcriptions)

srt_name = wav_file_name + '.srt'

with open(srt_name, "w") as f:
    f.write(subtitles)



