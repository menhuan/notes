#!/bin/bash
# 用来确定分割符
IFS=`echo -e "\n"`
OLDIFS=$IFS
name="暴富之后把钱分给闺蜜是一种什么体验"
musics=('https://wpy5.10cha.cn/yuyin/hecheng/outmusic/1663590715000wpy1481.mp3' 'https://wpy9.10cha.cn/yuyin/hecheng/outmusic/1663590785000wpy8154.mp3' 'https://wpyo.10cha.cn/yuyin/hecheng/outmusic/1663590883000wpy9956.mp3')
srts=('https://wpy5.10cha.cn/yuyin/hecheng/zimu/1663590715000wpy1481.srt' 'https://wpy9.10cha.cn/yuyin/hecheng/zimu/1663590785000wpy8154.srt' 'https://wpyo.10cha.cn/yuyin/hecheng/zimu/1663590883000wpy9956.srt')
for index in ${!musics[@]}
    do 
    wget "${musics[$index]}" -O  ${name}'('$(($index+1))')'',给闺蜜发钱''('$(($index+1))')'.mp3
    echo "$index" "${musics[$index]}"
    done

for index in ${!srts[@]}
    do 
    wget "${srts[$index]}" -O  ${name}'('$(($index+1))')'',给闺蜜发钱''('$(($index+1))')'.srt
    echo "$index" "${srts[$index]}"
    done

sshpass -pFeng930409@ rsync -avup --remove-source-files  *.srt *.mp3  ubuntu@101.43.210.78:/home/ubuntu/docker-compose/douyin/output/musics/