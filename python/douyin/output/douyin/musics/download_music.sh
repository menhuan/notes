#!/bin/bash
# 用来确定分割符
IFS=`echo -e "\n"`
OLDIFS=$IFS
name="#追求者500万"
musics=('https://wpyq.10cha.cn/yuyin/hecheng/outmusic/1664239373000wpy1066.mp3' 'https://wpy7.10cha.cn/yuyin/hecheng/outmusic/1664239429000wpy8490.mp3' 'https://wpy17.10cha.cn/yuyin/hecheng/outmusic/1664239492000wpy7170.mp3' 'https://wpy5.10cha.cn/yuyin/hecheng/outmusic/1664240421000wpy8686.mp3' 'https://wpya.10cha.cn/yuyin/hecheng/outmusic/1664240499000wpy7069.mp3' 'https://wpy12.10cha.cn/yuyin/hecheng/outmusic/1664240663000wpy8055.mp3')
srts=('https://wpyq.10cha.cn/yuyin/hecheng/zimu/1664239373000wpy1066.srt' 'https://wpy7.10cha.cn/yuyin/hecheng/zimu/1664239429000wpy8490.srt' 'https://wpy17.10cha.cn/yuyin/hecheng/zimu/1664239492000wpy7170.srt' 'https://wpy5.10cha.cn/yuyin/hecheng/zimu/1664240421000wpy8686.srt' 'https://wpya.10cha.cn/yuyin/hecheng/zimu/1664240499000wpy7069.srt' 'https://wpy12.10cha.cn/yuyin/hecheng/zimu/1664240663000wpy8055.srt')
for index in ${!musics[@]}
    do 
    wget "${musics[$index]}" -O  ${name}'('$(($index+1))')'',追求者500万''('$(($index+1))')'.mp3
    echo "$index" "${musics[$index]}"
    done

for index in ${!srts[@]}
    do 
    wget "${srts[$index]}" -O  ${name}'('$(($index+1))')'',追求者500万''('$(($index+1))')'.srt
    echo "$index" "${srts[$index]}"
    done

sshpass -pFeng930409@ rsync -avup --remove-source-files  *.srt *.mp3  ubuntu@101.43.210.78:/home/ubuntu/docker-compose/douyin/output/musics/