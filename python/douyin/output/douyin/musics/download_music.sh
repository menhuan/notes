#!/bin/bash
# 用来确定分割符
IFS=`echo -e "\n"`
OLDIFS=$IFS
name="别人喝酒误事 我喝酒睡上司"
musics=('https://wpyd.10cha.cn/yuyin/hecheng/outmusic/1661437379000wpy6000.wav' 'https://wpy8.10cha.cn/yuyin/hecheng/outmusic/1661437584000wpy2545.wav' )
srts=('https://wpyd.10cha.cn/yuyin/hecheng/zimu/1661437379000wpy6000.srt' 'https://wpy8.10cha.cn/yuyin/hecheng/zimu/1661437584000wpy2545.srt')
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