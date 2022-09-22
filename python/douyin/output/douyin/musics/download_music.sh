#!/bin/bash
# 用来确定分割符
IFS=`echo -e "\n"`
OLDIFS=$IFS
name="我弟从小被我欺负到大 没想到现在他想欺负我"
musics=('https://wpyd.10cha.cn/yuyin/hecheng/outmusic/1663772297000wpy1999.mp3' 'https://wpyq.10cha.cn/yuyin/hecheng/outmusic/1663772356000wpy8717.mp3' 'https://wpym.10cha.cn/yuyin/hecheng/outmusic/1663772423000wpy3401.mp3' 'https://wpy5.10cha.cn/yuyin/hecheng/outmusic/1663772500000wpy9046.mp3' 'https://wpyl.10cha.cn/yuyin/hecheng/outmusic/1663772570000wpy1312.mp3' 'https://wpyu.10cha.cn/yuyin/hecheng/outmusic/1663772630000wpy9361.mp3')
srts=('https://wpyd.10cha.cn/yuyin/hecheng/zimu/1663772297000wpy1999.srt' 'https://wpyq.10cha.cn/yuyin/hecheng/zimu/1663772356000wpy8717.srt' 'https://wpym.10cha.cn/yuyin/hecheng/zimu/1663772423000wpy3401.srt' 'https://wpy5.10cha.cn/yuyin/hecheng/zimu/1663772500000wpy9046.srt' 'https://wpyl.10cha.cn/yuyin/hecheng/zimu/1663772570000wpy1312.srt' 'https://wpyu.10cha.cn/yuyin/hecheng/zimu/1663772630000wpy9361.srt')
for index in ${!musics[@]}
    do 
    wget "${musics[$index]}" -O  ${name}'('$(($index+1))')'',日记弟弟''('$(($index+1))')'.mp3
    echo "$index" "${musics[$index]}"
    done

for index in ${!srts[@]}
    do 
    wget "${srts[$index]}" -O  ${name}'('$(($index+1))')'',日记弟弟''('$(($index+1))')'.srt
    echo "$index" "${srts[$index]}"
    done

sshpass -pFeng930409@ rsync -avup --remove-source-files  *.srt *.mp3  ubuntu@101.43.210.78:/home/ubuntu/docker-compose/douyin/output/musics/