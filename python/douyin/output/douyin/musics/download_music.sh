#!/bin/bash
# 用来确定分割符
IFS=`echo -e "\n"`
OLDIFS=$IFS
name="为了家里还能支撑下去 我选择嫁个老男人但确很幸福"
musics=('https://wpy17.10cha.cn/yuyin/hecheng/outmusic/1662897095000wpy5589.wav' 'https://wpy3.10cha.cn/yuyin/hecheng/outmusic/1662897264000wpy8439.wav' 'https://wpyq.10cha.cn/yuyin/hecheng/outmusic/1662897351000wpy4767.wav')
srts=('https://wpy17.10cha.cn/yuyin/hecheng/zimu/1662897095000wpy5589.srt' 'https://wpy3.10cha.cn/yuyin/hecheng/zimu/1662897264000wpy8439.srt' 'https://wpyq.10cha.cn/yuyin/hecheng/zimu/1662897351000wpy4767.srt')
for index in ${!musics[@]}
    do 
    wget "${musics[$index]}" -O  ${name}'('$(($index+1))')'',老男人结婚''('$(($index+1))')'.wav
    echo "$index" "${musics[$index]}"
    done

for index in ${!srts[@]}
    do 
    wget "${srts[$index]}" -O  ${name}'('$(($index+1))')'',老男人结婚''('$(($index+1))')'.srt
    echo "$index" "${srts[$index]}"
    done

sshpass -pFeng930409@ rsync -avup --remove-source-files  *.srt *.wav  ubuntu@101.43.210.78:/home/ubuntu/docker-compose/douyin/output/musics/