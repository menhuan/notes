#!/bin/bash
# 用来确定分割符
IFS=`echo -e "\n"`
OLDIFS=$IFS
name="给你50万去亲男神 愿意吗?"
musics=('https://wpyu.10cha.cn/yuyin/hecheng/outmusic/1661649974000wpy5340.wav' 'https://wpy1.10cha.cn/yuyin/hecheng/outmusic/1661650129000wpy8491.wav' 'https://wpyo.10cha.cn/yuyin/hecheng/outmusic/1661650225000wpy6041.wav')
srts=('https://wpyu.10cha.cn/yuyin/hecheng/zimu/1661649974000wpy5340.srt' 'https://wpy1.10cha.cn/yuyin/hecheng/zimu/1661650129000wpy8491.srt' 'https://wpyo.10cha.cn/yuyin/hecheng/zimu/1661650225000wpy6041.srt')
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