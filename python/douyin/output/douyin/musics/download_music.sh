#!/bin/bash
# 用来确定分割符
IFS=`echo -e "\n"`
OLDIFS=$IFS
name="小镇做题家的逆袭 打脸真是爽"
musics=('https://wpy6.10cha.cn/yuyin/hecheng/outmusic/1661731493000wpy2699.mp3' 'https://wpyu.10cha.cn/yuyin/hecheng/outmusic/1661731606000wpy7926.mp3' 'https://wpyu.10cha.cn/yuyin/hecheng/outmusic/1661731787000wpy3121.mp3')
srts=('https://wpy6.10cha.cn/yuyin/hecheng/zimu/1661731493000wpy2699.srt' 'https://wpyu.10cha.cn/yuyin/hecheng/zimu/1661731606000wpy7926.srt' 'https://wpyu.10cha.cn/yuyin/hecheng/zimu/1661731787000wpy3121.srt' )
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