'''
  For more samples please visit https://github.com/Azure-Samples/cognitive-services-speech-sdk 
'''

from fileinput import filename
from genericpath import exists
import os
import azure.cognitiveservices.speech as speechsdk
from pathlib import Path
from long_form_text_synthesis import LongTextSynthesizer

root_path = os.getenv(
    "ROOT_PATH", "/workspaces/notes/python/douyin/output/douyin/")
musics_path = os.path.join(root_path, os.getenv("MUSICS", "musics/"))
OS_RATE = str(os.getenv("OS_RATE","60")) +"%"
OS_PITCH = str(os.getenv("OS_PITCH","30")) + "%"
OS_VOLUME = str(os.getenv("OS_VOLUME","+20")) + "%"

BREAK_TIME= str(int(os.getenv("BREAK_TIME", "100"))) + "ms"

def output(txt_to_aideo,file_name):
    # <break time="{BREAK_TIME}" />            <mstts:silence  type="Tailing" value="{BREAK_TIME}"/>
    ssml = ""
    for txt in txt_to_aideo.split('，'):
        txt = txt.strip()
        if(len(txt) >0 ):
            ssml += f"""<voice name="zh-CN-XiaochenNeural"><p><prosody rate="{OS_RATE}" pitch="{OS_PITCH}" volume="{OS_VOLUME}" > {txt}。 </prosody><break time="{BREAK_TIME}" /></p></voice>""" 
    text =f"""<speak xmlns="http://www.w3.org/2001/10/synthesis" xmlns:mstts="http://www.w3.org/2001/mstts" xmlns:emo="http://www.w3.org/2009/10/emotionml" version="1.0" xml:lang="zh-CN">{ssml} </speak>"""
    ssml_path =  os.path.join(musics_path,file_name +".xml")
    Path(musics_path).mkdir(parents=True,exist_ok=True)
    with open(ssml_path,'w') as f:
        f.writelines(text)
    s = LongTextSynthesizer(subscription="d81e33380a984e25ba06f5582dbb46d7", region="eastasia",parallel_threads=2)
    # with Path('/workspaces/notes/python/douyin/zhihu/Gatsby-chapter1.txt').open('r', encoding='utf-8') as r:
    #     s.synthesize_text(r.read(), output_path=Path('./gatsby'))
    s.synthesize_text(ssml_path=Path(ssml_path), output_path=Path(musics_path),file_name=file_name)
    os.remove(ssml_path)


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
    output(txt_to_aideo=txt,file_name=f'{musics_path}text,test')
    pass
