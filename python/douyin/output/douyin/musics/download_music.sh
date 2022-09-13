#!/bin/bash
# 用来确定分割符
IFS=`echo -e "\n"`
OLDIFS=$IFS
name="为了恋爱减肥25斤 网恋对象却是个200斤大叔?"
musics=('https://wpyn.10cha.cn/yuyin/hecheng/outmusic/1663110255000wpy3241.wav' 'https://wpy7.10cha.cn/yuyin/hecheng/outmusic/1663110359000wpy7444.wav' )
srts=('https://wpyn.10cha.cn/yuyin/hecheng/zimu/1663110255000wpy3241.srt' 'https://wpy7.10cha.cn/yuyin/hecheng/zimu/1663110359000wpy7444.srt')
for index in ${!musics[@]}
    do 
    wget "${musics[$index]}" -O  ${name}'('$(($index+1))')'',拉手校霸''('$(($index+1))')'.wav
    echo "$index" "${musics[$index]}"
    done

for index in ${!srts[@]}
    do 
    wget "${srts[$index]}" -O  ${name}'('$(($index+1))')'',拉手校霸''('$(($index+1))')'.srt
    echo "$index" "${srts[$index]}"
    done

sshpass -pFeng930409@ rsync -avup --remove-source-files  *.srt *.wav  ubuntu@101.43.210.78:/home/ubuntu/docker-compose/douyin/output/musics/