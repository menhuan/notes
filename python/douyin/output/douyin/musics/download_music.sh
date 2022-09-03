#!/bin/bash
# 用来确定分割符
IFS=`echo -e "\n"`
OLDIFS=$IFS
name="作妖的堂妹让我真长见识 网友 送到精神病院吧"
musics=('https://wpyf.10cha.cn/yuyin/hecheng/outmusic/1662124883000wpy4671.wav' 'https://wpyv.10cha.cn/yuyin/hecheng/outmusic/1662125183000wpy6865.wav' 'https://wpyo.10cha.cn/yuyin/hecheng/outmusic/1662125256000wpy8389.wav' )
srts=('https://wpyf.10cha.cn/yuyin/hecheng/zimu/1662124883000wpy4671.srt' 'https://wpyv.10cha.cn/yuyin/hecheng/zimu/1662125183000wpy6865.srt' 'https://wpyo.10cha.cn/yuyin/hecheng/zimu/1662125256000wpy8389.srt')
for index in ${!musics[@]}
    do 
    wget "${musics[$index]}" -O  ${name}'('$(($index+1))')'.wav
    echo "$index" "${musics[$index]}"
    done

for index in ${!srts[@]}
    do 
    wget "${srts[$index]}" -O  ${name}'('$(($index+1))')'.srt
    echo "$index" "${srts[$index]}"
    done

sshpass -pFeng930409@ rsync -avup --remove-source-files  *.srt *.wav  ubuntu@101.43.210.78:/home/ubuntu/docker-compose/douyin/output/musics/