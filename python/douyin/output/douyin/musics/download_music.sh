#!/bin/bash
# 用来确定分割符
IFS=`echo -e "\n"`
OLDIFS=$IFS
name="#假基金经理"
musics=('https://wpy5.10cha.cn/yuyin/hecheng/outmusic/1663982115000wpy1458.mp3' 'https://wpym.10cha.cn/yuyin/hecheng/outmusic/1663982224000wpy8415.mp3' 'https://wpyz.10cha.cn/yuyin/hecheng/outmusic/1663982415000wpy4918.mp3' )
srts=('https://wpy5.10cha.cn/yuyin/hecheng/zimu/1663982115000wpy1458.srt' 'https://wpym.10cha.cn/yuyin/hecheng/zimu/1663982224000wpy8415.srt' 'https://wpyz.10cha.cn/yuyin/hecheng/zimu/1663982415000wpy4918.srt')
for index in ${!musics[@]}
    do 
    wget "${musics[$index]}" -O  ${name}'('$(($index+1))')'',假基金经理''('$(($index+1))')'.mp3
    echo "$index" "${musics[$index]}"
    done

for index in ${!srts[@]}
    do 
    wget "${srts[$index]}" -O  ${name}'('$(($index+1))')'',假基金经理''('$(($index+1))')'.srt
    echo "$index" "${srts[$index]}"
    done

sshpass -pFeng930409@ rsync -avup --remove-source-files  *.srt *.mp3  ubuntu@101.43.210.78:/home/ubuntu/docker-compose/douyin/output/musics/