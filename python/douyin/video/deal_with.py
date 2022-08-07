# 用来前处理视频文件


import os
from cv2 import merge
from moviepy.editor import *
import ffmpeg
import subprocess


root_path = "/workspaces/notes/python/douyin/video/"
output_path = "/workspaces/notes/python/douyin/video/cutting_videos/"
merge_path = root_path + "merge/" 
merge_outpath = root_path+ "merge_out/"

def sub_video():
    dst_path = root_path + "pending/"
    relaxVideo_files = os.listdir(dst_path)
    relaxVideo_files_count = len(relaxVideo_files)
    print("开始获取视频文件进行裁剪:",relaxVideo_files_count)
    video_list = []  # 空列表
    for val in relaxVideo_files:
        if ((os.path.splitext(val)[1] == '.MP4' or os.path.splitext(val)[1] == '.mp4')):
            video_list.append(dst_path + val)

    for video in video_list:
        video_clip = VideoFileClip(video)
        x1,y1 = 0,0
        x2,y2 = 1980,935
        video_out_clip = video_clip.fx(vfx.crop,x1,y1,x2,y2)
        video_name =merge_path + video.split('/')[-1][:-4] + ".mp4"
        without_video_out_clip = video_out_clip.without_audio()
        speedx_video = without_video_out_clip.speedx(1.4)
        speedx_video.write_videofile(video_name)
        print("完成视频裁剪",video)
    # video = "/workspaces/notes/python/douyin/video/pending/test_0.mp4"
    # video_clip = VideoFileClip(video)
    # x1,y1 = 0,0
    # x2,y2 = 1980,935
    # video_out_clip = video_clip.fx(vfx.crop,x1,y1,x2,y2)
    # video_name =root_path +  "merge/" + video.split('/')[-1][:-4] + ".mp4"
    # without_video_out_clip = video_out_clip.without_audio()
    # speedx_video = without_video_out_clip.speedx(1.4)
    # speedx_video.write_videofile(video_name)
    print("完成视频裁剪")

def cutting_video_by_minuts(video_dir_path):
    # 按照时长来额切割
    filelist=[] # 保存文件绝对路径的列表    
    for dirpath, dirnames, filenames in os.walk(video_dir_path):
        for name in filenames:
            fname = os.path.join(dirpath, name)
            if fname.endswith(".mp4"):
                filelist.append(fname)
    cutting_minuts = os.getenv("VIDEO_CUTTING_MINUTS",4*60)
    for item in filelist:
        print("处理路径:",item)
        clip = VideoFileClip(item)
        start_time = 0 
        end_time = cutting_minuts
        for index in range(int(clip.duration/cutting_minuts)):
            if(end_time*(index+1) >= clip.duration ):
                cutting_video = clip.subclip(int(start_time * index,clip.duration))
            else:
                cutting_video = clip.subclip(start_time * index,end_time*(index+1))
            target_path = output_path + item.split('/')[-1][:-4]  + "_" + str(index) + ".mp4"
            cutting_video.to_videofile(target_path)
            print("切割完成一部分",item,index)
def gaussian_video():
    result = subprocess.call("bash /workspaces/notes/python/douyin/video/video_add_gblur.sh",shell=True)
    print("result:{}",result)

if __name__ == "__main__":
    #sub_video() 
    gaussian_video()
    cutting_video_by_minuts(merge_outpath)