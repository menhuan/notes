#!/bin/bash
# 用来确定分割符
IFS=`echo -e "\n"`
OLDIFS=$IFS
name="a"
musics=('https://wpy3.10cha.cn/yuyin/hecheng/outmusic/1661584181000wpy2962.wav' 'https://wpy5.10cha.cn/yuyin/hecheng/outmusic/1661584645000wpy9985.wav')
srts=('https://wpy3.10cha.cn/yuyin/hecheng/zimu/1661584181000wpy2962.srt' 'https://wpy5.10cha.cn/yuyin/hecheng/zimu/1661584645000wpy9985.srt')
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