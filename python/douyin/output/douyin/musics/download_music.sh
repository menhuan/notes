#!/bin/bash
# 用来确定分割符
IFS=`echo -e "\n"`
OLDIFS=$IFS
name="婚姻是恋爱的坟墓 但这才是真正幸福的开始"
musics=('https://wpye.10cha.cn/yuyin/hecheng/outmusic/1663467798000wpy1548.wav' 'https://wpyn.10cha.cn/yuyin/hecheng/outmusic/1663467865000wpy7083.wav' 'https://wpyo.10cha.cn/yuyin/hecheng/outmusic/1663467929000wpy4084.wav')
srts=('https://wpye.10cha.cn/yuyin/hecheng/zimu/1663467798000wpy1548.srt' 'https://wpyn.10cha.cn/yuyin/hecheng/zimu/1663467865000wpy7083.srt' 'https://wpyo.10cha.cn/yuyin/hecheng/zimu/1663467929000wpy4084.srt')
for index in ${!musics[@]}
    do 
    wget "${musics[$index]}" -O  ${name}'('$(($index+1))')'',浓情女友''('$(($index+1))')'.wav
    echo "$index" "${musics[$index]}"
    done

for index in ${!srts[@]}
    do 
    wget "${srts[$index]}" -O  ${name}'('$(($index+1))')'',浓情女友''('$(($index+1))')'.srt
    echo "$index" "${srts[$index]}"
    done

sshpass -pFeng930409@ rsync -avup --remove-source-files  *.srt *.wav  ubuntu@101.43.210.78:/home/ubuntu/docker-compose/douyin/output/musics/