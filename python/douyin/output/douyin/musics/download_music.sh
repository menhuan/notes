#!/bin/bash
# 用来确定分割符
IFS=`echo -e "\n"`
OLDIFS=$IFS
name="#前夫复合"
musics=('https://wpyk.10cha.cn/yuyin/hecheng/outmusic/1664065748000wpy7486.mp3' 'https://wpye.10cha.cn/yuyin/hecheng/outmusic/1664065837000wpy5489.mp3' 'https://wpy7.10cha.cn/yuyin/hecheng/outmusic/1664066012000wpy8855.mp3' 'https://wpy11.10cha.cn/yuyin/hecheng/outmusic/1664066080000wpy6977.mp3' 'https://wpyz.10cha.cn/yuyin/hecheng/outmusic/1664066154000wpy7638.mp3' 'https://wpya.10cha.cn/yuyin/hecheng/outmusic/1664066236000wpy619.mp3')
srts=('https://wpyk.10cha.cn/yuyin/hecheng/zimu/1664065748000wpy7486.srt' 'https://wpye.10cha.cn/yuyin/hecheng/zimu/1664065837000wpy5489.srt' 'https://wpy7.10cha.cn/yuyin/hecheng/zimu/1664066012000wpy8855.srt' 'https://wpy11.10cha.cn/yuyin/hecheng/zimu/1664066080000wpy6977.srt' 'https://wpyz.10cha.cn/yuyin/hecheng/zimu/1664066154000wpy7638.srt' 'https://wpya.10cha.cn/yuyin/hecheng/zimu/1664066236000wpy619.srt')
for index in ${!musics[@]}
    do 
    wget "${musics[$index]}" -O  ${name}'('$(($index+1))')'',前夫复合''('$(($index+1))')'.mp3
    echo "$index" "${musics[$index]}"
    done

for index in ${!srts[@]}
    do 
    wget "${srts[$index]}" -O  ${name}'('$(($index+1))')'',前夫复合''('$(($index+1))')'.srt
    echo "$index" "${srts[$index]}"
    done

sshpass -pFeng930409@ rsync -avup --remove-source-files  *.srt *.mp3  ubuntu@101.43.210.78:/home/ubuntu/docker-compose/douyin/output/musics/