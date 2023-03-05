# 用来前处理视频文件
import os
from time import sleep
import cv2
from moviepy.editor import *
import ffmpeg
import subprocess


root_path = os.getenv("ROOT_PATH","/workspaces/notes/python/douyin/output/douyin/")
output_path = os.path.join(root_path,os.getenv("CUTTING_VIDEOS",'cutting_videos'))
merge_path = os.path.join(root_path,os.getenv("MERGE_PATH",'merge/'))
merge_outpath =os.path.join(root_path,os.getenv("MERGE_OUT_PATH",'merge_out'))
bash_sh =   os.getenv("VIDEO_ADD_GBLUR","/workspaces/notes/python/douyin/video/video_add_gblur.sh")

def sub_video():
    dst_path = root_path + "pending/"
    relaxVideo_files = os.listdir(dst_path)
    relaxVideo_files_count = len(relaxVideo_files)
    print("开始获取视频文件进行裁剪:", relaxVideo_files_count)
    video_list = []  # 空列表
    for val in relaxVideo_files:
        if ((os.path.splitext(val)[1] == '.MP4' or os.path.splitext(val)[1] == '.mp4')):
            video_list.append(dst_path + val)

    for video in video_list:
        video_clip = VideoFileClip(video)
        x1, y1 = 0, 0
        x2, y2 = 1980, 935
        video_out_clip = video_clip.fx(vfx.crop, x1, y1, x2, y2)
        video_name = os.path.join(merge_path,video.split('/')[-1][:-4] + ".mp4")
        without_video_out_clip = video_out_clip.without_audio()
        speedx_video = without_video_out_clip.speedx(1.4)
        speedx_video.write_videofile(video_name)
        os.remove(video)
        print("完成视频裁剪", video)
    print("完成视频裁剪")


def cutting_video_by_minuts(video_dir_path):
    # 按照时长来额切割
    filelist = []  # 保存文件绝对路径的列表
    for dirpath, dirnames, filenames in os.walk(video_dir_path):
        for name in filenames:
            fname = os.path.join(dirpath, name)
            if fname.endswith(".mp4"):
                filelist.append(fname)
    cutting_minuts = os.getenv("VIDEO_CUTTING_MINUTS", 10)
    for item in filelist:
        clip = VideoFileClip(item)
        print("处理路径:", item, "总的时长是:", clip.duration)
        start_time = 0
        end_time = cutting_minuts
        for index in range(int(clip.duration/cutting_minuts) +1 ):
            local_end_time = end_time*(index+1)
            print("开始处理", item, index, start_time, local_end_time)
            if(start_time >= clip.duration):
                break
            if(end_time*(index+1) >= clip.duration):
                cutting_video = clip.subclip(int(start_time), clip.duration)
            else:
                cutting_video = clip.subclip(start_time, local_end_time)
            target_path = os.path.join(output_path ,
                item.split('/')[-1][:-4] + "_" + str(index) + ".mp4")
            cutting_video.to_videofile(target_path)
            start_time = local_end_time
            print("切割完成一部分", item, index)
        os.remove(item)


def gaussian_video(video_dir_path):
    files = []
    for dirpath, dirnames, filenames in os.walk(video_dir_path):
        for name in filenames:
            fname = os.path.join(dirpath, name)
            if fname.endswith(".mp4"):
                name_to_target =name.replace('|','').replace(' ','')
                fname_target = os.path.join(dirpath,name_to_target)
                os.rename(fname,fname_target)
                files.append(fname_target)
    if(len(files) > 0):
        result = subprocess.call(f"bash {bash_sh}",shell=True)
        print("result:{}",result)
    else:
        print("本次没有找到对应的文件,暂不处理")
   

def run():
    while(True):
        sub_video() 
        gaussian_video(merge_path)
        cutting_video_by_minuts(merge_outpath)
        print("本次处理完毕,等待下次处理")
        sleep(600)

if __name__ == "__main__":
    cutting_video_by_minuts("/workspaces/notes/python/douyin/output/douyin/merge_out/")