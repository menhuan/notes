# 根据小说的音频生成视频
# 运行程序 python3 add_music_to_video.py

import os

from random import sample
from time import sleep
from moviepy.editor import *
from mutagen.mp3 import MP3
from mutagen.wave import WAVE
from pip import main

out_image_name = 'out_image.png'
out_video_name = 'black_video.mp4'
bg_video_name = 'bg_video.mp4'

# 字体的色值
# https://encycolorpedia.cn/ff6781
text_color = "#000000"  # black ##F2B2C5
root_path = os.getenv("ROOT_PATH", "/workspaces/notes/python/douyin/video/")
font_name = root_path + os.getenv("FOOT_NAME", "fonts/miaohunti.ttf")
output_path = root_path + os.getenv("OUTPUT", "output/")
# 合并视频，获取解压的背景视频


def mergerVideo(videoDuration):

    dst_path = root_path + "relaxVideo"
    relaxVideo_files = os.listdir(dst_path)
    relaxVideo_files_count = len(relaxVideo_files)
    print(relaxVideo_files_count)

    video_list = []  # 空列表
    for index, val in enumerate(relaxVideo_files):

        if ((os.path.splitext(val)[1] == '.MP4' or os.path.splitext(val)[1] == '.mp4')):
            print(val)
            video_list.append(val)

    random = 25
    random = 2
    random = 0
    if(videoDuration < 180):
        random = 13
    elif (videoDuration < 240):
        random = 17
    elif (videoDuration < 300):
        random = 21
    else:
        random = 24

    random_list = sample(video_list, 5)  # 随机抽取元素
    print(random_list)
    # # 定义一个数组
    video_clip_list = []

    for file in random_list:
        video = VideoFileClip(root_path + "relaxVideo/" + file)
        # newvideo = video.volumex(0.2)#
        # video_clip_list.append(newvideo)
        video_clip_list.append(video)

    print("开始根据音频时长,合成视频")
    # 根据音频时长，裁剪视频
    final_clip = concatenate_videoclips(
        video_clip_list).subclip(0, videoDuration)

    # 添加背景音乐
    # video_audio_clip = final_clip.audio.volumex(0.1)
    dst_path = root_path + "/backgroundmusic"
    music_files = os.listdir(dst_path)
    musics_files_count = len(music_files)
    backgroundmusic_list = []
    for music_file in music_files:
        if (music_file.endswith('.mp3') or music_file.endswith('.wav')):
            music_name = root_path+"backgroundmusic/" + music_file
            backgroundmusic_list.append(music_name)

    music = sample(backgroundmusic_list, 1)  # 随机抽取1个元素

    musicStr = ''.join(music)

    print("开始合成背景音乐", musicStr)

    audio_clip = AudioFileClip(musicStr).volumex(0.1)

    audio = afx.audio_loop(audio_clip, duration=final_clip.duration)

    # audio_clip_add = CompositeAudioClip([video_audio_clip, audio])

    final_video = final_clip.set_audio(audio)

    # final_video.write_videofile(result_video_name)
    video_name = bg_video_name
    final_video.write_videofile(video_name, audio=True)

# 把小说配音添加到视频里面


def addMusicToVideo(audioFile, srt_name, title):
    video_clip = VideoFileClip(bg_video_name)
    # 背景音乐
    video_audio_clip = video_clip.audio.volumex(0.3)

    # 小说音频
    audio_clip = AudioFileClip(audioFile).volumex(0.9)
    # 设置背景音乐循环，时间与视频时间一致
    audio = afx.audio_loop(audio_clip, duration=video_clip.duration)
    # 叠加背景音
    new_audioclip = CompositeAudioClip([video_audio_clip, audio_clip])

    video_clip.audio = new_audioclip

    video_name = "music_" + audioFile.split('/')[1].split('.')[0] + ".mp4"

    video_clip.write_videofile(video_name, audio=True, audio_codec="aac")

    os.remove(bg_video_name)

    writeTextVideo(video_name, srt_name, title)

# 给视频添加标题 与字幕


def writeTextVideo(video_name, srt_name, title):
    video_clip = VideoFileClip(video_name)
    # cover_title=title;
    # a[2:-2] 表示去掉前面两个和后面两个
    # 如果光去掉后面的a[:-2]
    cover_title = title
    title_fond_size = 90
    txts = []

    w, h = video_clip.w, video_clip.h
    print("w:", w, "h:", h)
    title_text_clip = TextClip(txt=cover_title,
                               font=font_name,
                               fontsize=title_fond_size,
                               color=text_color, stroke_color="white", stroke_width=20, align='center', method="caption", size=(w - 20, 230)).set_position((10, h - 1500)).set_duration(video_clip.duration)
    title_text_clip2 = TextClip(txt=cover_title,
                                font=font_name,
                                fontsize=title_fond_size,
                                color=text_color, align='center', method="caption", size=(w - 20, 230)).set_position((10, h - 1500)).set_duration(video_clip.duration)
    txts.append(title_text_clip)
    txts.append(title_text_clip2)
    result = CompositeVideoClip([video_clip, *txts])  # 在视频上覆盖文本

    result_video_name = "title_" + video_name

    result.write_videofile(result_video_name, audio=True, audio_codec="aac")
    os.remove(video_name)
    # 给视频添加字幕
    videoAddSrt(result_video_name, srt_name, cover_title)


# 读取字幕文件
def read_srt(path):
    content = ""
    with open(path, 'r', encoding='UTF-8') as f:
        content = f.read()
        return content
# 字幕拆分


def get_sequences(content):
    sequences = content.split('\n\n')
    sequences = [sequence.split('\n') for sequence in sequences]
    # 去除每一句空值
    sequences = [list(filter(None, sequence)) for sequence in sequences]
    # 去除整体空值
    return list(filter(None, sequences))

# 转换时间


def strFloatTime(tempStr):
    xx = tempStr.split(':')
    hour = int(xx[0])
    minute = int(xx[1])
    second = int(xx[2].split(',')[0])
    minsecond = int(xx[2].split(',')[1])
    allTime = hour * 60 * 60 + minute * 60 + second + minsecond / 1000
    return allTime

# 给视频添加字幕


def videoAddSrt(videoFile, srtFile, title):
    video = VideoFileClip(videoFile)
    # 获取视频的宽度和高度

    w, h = video.w, video.h
    print(w)
    print(h)

    txts = []
    txts2 = []

    content = read_srt(srtFile)

    sequences = get_sequences(content)

    for line in sequences:
        if len(line) < 3:
            continue
        sentences = line[2]
        start = line[1].split(' --> ')[0]
        end = line[1].split(' --> ')[1]

        start = strFloatTime(start)

        end = strFloatTime(end)

        start, end = map(float, (start, end))
        # sentences = "年会上我喝多了，玩游戏还输了,被迫要和同桌一个异性亲亲"
        span = end-start
        if (len(sentences) > 12):
            # method：可以设置为’label’或’caption’，设置为’label’时，
            # 图片将自动调整大小以适合剪辑的大小，这是该参数的缺省值。设置为’caption’时，
            # 文字将在size参数指定范围内显示，此时文字会自动换行
            txt = TextClip(sentences, fontsize=80, font=font_name, size=(w - 20, 200), align='center',
                           color=text_color, method='caption', stroke_color="white", stroke_width=20).set_position((10, h - 980)) .set_duration(span).set_start(start)
            txt2 = TextClip(sentences, fontsize=80, font=font_name, size=(w - 20, 200), align='center',
                            color=text_color, method='caption').set_position((10, h - 980)).set_duration(span).set_start(start)
        else:
            txt = TextClip(sentences, fontsize=80, font=font_name, size=(w - 20, 100), align='center',
                           color=text_color, stroke_color="white", stroke_width=20).set_position((10, h - 960)).set_duration(span).set_start(start)
            txt2 = TextClip(sentences, fontsize=80, font=font_name, size=(w - 20, 100), align='center',
                            color=text_color).set_position((10, h - 960)).set_duration(span).set_start(start)
        txts.append(txt)
        txts.append(txt2)
    # 合成视频，写入文件
    video = CompositeVideoClip([video, *txts])
    video.write_videofile(output_path + title + ".mp4")
    os.remove(videoFile)

if __name__ == "__main__":


    # 获取音乐文件夹
    dst_path = root_path + "/musics"
    music_files = os.listdir(dst_path)
    musics_files_count = len(music_files)
    sleep_time = os.getenv("SLEEP_TIME",10)
    while(True):
        if(len(music_files) == 0 ):
            print("本次没有音频文件,暂不合并")
            sleep(sleep_time)
        for music_file in music_files:
            if (music_file.endswith('.mp3') or music_file.endswith('.wav')):
                print(music_file)
                music_name = root_path + "musics/" + music_file
                srt_name = root_path + "musics/" + music_file.split('.')[0] + ".srt"
                print(srt_name)

                if music_file.endswith('.mp3'):
                    audioMp3 = MP3(music_name)
                    audio_duration = audioMp3.info.length
                else:
                    audio = WAVE(music_name)
                    audio_info = audio.info
                    audio_duration = int(audio_info.length)

                mergerVideo(audio_duration)

                print(music_file.split('.')[0])

                title = music_file.split('.')[0]

                addMusicToVideo(music_name, srt_name, title)
                os.remove(music_file)
                os.remove(srt_name)
            print("视频合成已完成",music_file)
        print("本次视频合成完毕")
        sleep(sleep_time)

# playsound.playsound('视频合成完成提示音.mp3')