'''
  For more samples please visit https://github.com/Azure-Samples/cognitive-services-speech-sdk 
'''

import os
import azure.cognitiveservices.speech as speechsdk

root_path = os.getenv(
    "ROOT_PATH", "/workspaces/notes/python/douyin/output/douyin/")
musics_path = os.path.join(root_path, os.getenv("MUSICS", "musics/"))
OS_RATE = str(os.getenv("OS_RATE","45")) +"%"
OS_PITCH = str(os.getenv("OS_PITCH","25")) + "%"
BREAK_TIME= str(int(int(os.getenv("BREAK_TIME", "120")) /(1 - int(os.getenv("OS_RATE","45"))* 0.01 ))) + "ms"

def output(txt_to_aideo,file_name):
    # <break time="{BREAK_TIME}" />            <mstts:silence  type="Tailing" value="{BREAK_TIME}"/>
    ssml = ""
    for txt in txt_to_aideo.split('，'):
        txt = txt.strip()
        if(len(txt) >0 ):
            ssml += f"""
                <prosody rate="{OS_RATE}" pitch="{OS_PITCH}"> {txt} </prosody><break time="{BREAK_TIME}" />
           """ 
    text =f"""
        <speak xmlns="http://www.w3.org/2001/10/synthesis" xmlns:mstts="http://www.w3.org/2001/mstts" xmlns:emo="http://www.w3.org/2009/10/emotionml" version="1.0" xml:lang="zh-CN">
            <voice name="zh-CN-XiaochenNeural"> 
            {ssml} 
            </voice>
    </speak>
    """
    # Creates an instance of a speech config with specified subscription key and service region.
    speech_key =os.getenv("SUBSCRIPTION_KEY","")
    service_region = os.getenv("REGION","eastasia")

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


if __name__ == "__main__":
    txt = """当我跟网恋忠犬男友发了信息，结果对面要跟我干架的高冷校霸的手机响了之后，
旁边的绿茶女慌了，
我也慌了，
不是，你搁这奶狗狼狗无缝切换呢，
你就是许墨墨，
被围堵在学校那条小巷口的时候，校霸顾翊阳站在我面前，冷眼瞧了我好久，这才幽幽蹦出这么一句话，
我闻言，看着顾翊阳挑衅的模样，以及他身后的一群面色不善的跟班们，激动地搓了搓手，我说我是的话，你们是不是要跟我打一架，
太棒了，好久没有在c大看见这么不怕4的愣头青了，
居然送上门来让手痒的我打架，还不止一个人，
谢谢，千里送人头，礼轻情意重，这份情谊，我许墨墨记下了，等会动手的时候肯定不打脸，
顾翊阳见状，眉头挑了一挑，朝站在他身边的女生问道，你确定她就是许墨墨，看起来脑子有点问题，
女生看着我，怯怯地揽住顾翊阳的手臂，是、是她，翊阳哥，要不然我们还是算了吧，学姐她一定不是故意的，
不行，阿姨嘱咐过我，不能让你在学校被欺负，既然她敢做就要敢当，
我在旁边等得有些不耐烦了，到底打不打啊，还有，什么故意不故意，
顾翊阳身边一个小弟看不下去了，勇猛地站了出来，指着我的鼻子道，你还装傻，就是你抢了我们老大干妹妹的男朋友，
哇哦，这关系听起来有点狗血啊，
我竖起了八卦的小耳朵，问小跟班，所以你们老大绿了吗，
你，你放肆，我们老大和我们老大的干妹妹是清白的，"""
    output(txt_to_aideo=txt,file_name='/app/output/text')
    pass
