#!/bin/bash
IFS=`echo -e "\n"`
OLDIFS=$IFS

dir="/home/ubuntu/docker-compose/douyin/output/pending"
filelist=`ls ${dir}`
for file in $filelist
do
   if [[ $file =~ "webm" ]]
   then
        out_video_name=${file//.webm/.mp4}
        ffmpeg -i ${file} ${out_video_name}
   elif [[ $file =~ "mp4" ]]
   then 
      echo "mp4文件不用处理"
   elif [[ $file =~ "sh" ]]
   then 
      echo "shell文件不用处理"
   else
      echo "$file 文件不包含webm"
   fi
done

