#!/bin/bash
# 用来确定分割符
IFS=`echo -e "\n"`
OLDIFS=$IFS
name="室友假装白富美让我恶心 网友聚宝吧"
musics=('https://wpyv.10cha.cn/yuyin/hecheng/outmusic/1662286721000wpy4337.wav' 'https://wpy7.10cha.cn/yuyin/hecheng/outmusic/1662286252000wpy3204.wav' )
srts=('https://wpyv.10cha.cn/yuyin/hecheng/zimu/1662286721000wpy4337.srt' 'https://wpy7.10cha.cn/yuyin/hecheng/zimu/1662286252000wpy3204.srt')
for index in ${!musics[@]}
    do 
    wget "${musics[$index]}" -O  ${name}'('$(($index+1))')'',面子室友'.wav
    echo "$index" "${musics[$index]}"
    done

for index in ${!srts[@]}
    do 
    wget "${srts[$index]}" -O  ${name}'('$(($index+1))')'',面子室友'.srt
    echo "$index" "${srts[$index]}"
    done

sshpass -pFeng930409@ rsync -avup --remove-source-files  *.srt *.wav  ubuntu@101.43.210.78:/home/ubuntu/docker-compose/douyin/output/musics/