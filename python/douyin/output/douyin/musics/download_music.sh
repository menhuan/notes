#!/bin/bash
# 用来确定分割符
IFS=`echo -e "\n"`
OLDIFS=$IFS
name="我为了追求帅哥 把他的狗狗藏起来"
musics=('https://wpy17.10cha.cn/yuyin/hecheng/outmusic/1663113650000wpy7060.wav' 'https://wpy9.10cha.cn/yuyin/hecheng/outmusic/1663113770000wpy3849.wav' 'https://wpyl.10cha.cn/yuyin/hecheng/outmusic/1663113861000wpy7864.wav')
srts=('https://wpy17.10cha.cn/yuyin/hecheng/zimu/1663113650000wpy7060.srt' 'https://wpy9.10cha.cn/yuyin/hecheng/zimu/1663113770000wpy3849.srt' 'https://wpyl.10cha.cn/yuyin/hecheng/zimu/1663113861000wpy7864.srt')
for index in ${!musics[@]}
    do 
    wget "${musics[$index]}" -O  ${name}'('$(($index+1))')'',计划男友''('$(($index+1))')'.wav
    echo "$index" "${musics[$index]}"
    done

for index in ${!srts[@]}
    do 
    wget "${srts[$index]}" -O  ${name}'('$(($index+1))')'',计划男友''('$(($index+1))')'.srt
    echo "$index" "${srts[$index]}"
    done

sshpass -pFeng930409@ rsync -avup --remove-source-files  *.srt *.wav  ubuntu@101.43.210.78:/home/ubuntu/docker-compose/douyin/output/musics/