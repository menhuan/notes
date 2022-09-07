#!/bin/bash
# 用来确定分割符
IFS=`echo -e "\n"`
OLDIFS=$IFS
name=""
musics=()
srts=()
for index in ${!musics[@]}
    do 
    wget "${musics[$index]}" -O  ${name}'('$(($index+1))')'',鼻血爱情'.wav
    echo "$index" "${musics[$index]}"
    done

for index in ${!srts[@]}
    do 
    wget "${srts[$index]}" -O  ${name}'('$(($index+1))')'',鼻血爱情'.srt
    echo "$index" "${srts[$index]}"
    done

sshpass -pFeng930409@ rsync -avup --remove-source-files  *.srt *.wav  ubuntu@101.43.210.78:/home/ubuntu/docker-compose/douyin/output/musics/