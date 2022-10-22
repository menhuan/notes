#!/bin/bash
# 用来确定分割符
IFS=`echo -e "\n"`
OLDIFS=$IFS
name="我的私妹妹 看到最后有惊喜"
# musics=('https://wpy3.10cha.cn/yuyin/hecheng/outmusic/1665923246000wpy5843.mp3' 'https://wpyz.10cha.cn/yuyin/hecheng/outmusic/1665923342000wpy917.mp3' 'https://wpy2.10cha.cn/yuyin/hecheng/outmusic/1665923414000wpy318.mp3')
# srts=('https://wpy3.10cha.cn/yuyin/hecheng/zimu/1665923246000wpy5843.srt' 'https://wpyz.10cha.cn/yuyin/hecheng/zimu/1665923342000wpy917.srt' 'https://wpy2.10cha.cn/yuyin/hecheng/zimu/1665923414000wpy318.srt')
# for index in ${!musics[@]}
#     do 
#     wget "${musics[$index]}" -O  ${name}'('$(($index+1))')'',我的私妹妹''('$(($index+1))')'.mp3
#     echo "$index" "${musics[$index]}"
#     done

# for index in ${!srts[@]}
#     do 
#     wget "${srts[$index]}" -O  ${name}'('$(($index+1))')'',我的私妹妹''('$(($index+1))')'.srt
#     echo "$index" "${srts[$index]}"
#     done

sshpass -pFeng930409@ rsync -avup --remove-source-files  *.srt *.mp3  ubuntu@101.43.210.78:/home/ubuntu/docker-compose/douyin/output/musics/