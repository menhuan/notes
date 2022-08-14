#!/bin/bash
##chmod 777 video_add_gblur.sh 给脚本加权限
##执行脚本，./video_add_gblur.sh
###ffmpeg实例，使用高斯模糊为视频生成一个模糊背景（gblur）
##参考代码https://blog.csdn.net/qq_20288327/article/details/114652060?utm_medium=distribute.pc_aggpage_search_result.none-task-blog-2~aggregatepage~first_rank_ecpm_v1~rank_v31_ecpm-2-114652060-null-null.pc_agg_new_rank&utm_term=moviepy%20%E9%AB%98%E6%96%AF%E6%A8%A1%E7%B3%8A&spm=1000.2123.3001.4430

dir="${ROOT_PATH}merge"
output="${ROOT_PATH}/merge_out/"
filelist=`ls ${dir}`
for file in $filelist
do 
    video_name=${dir}/${file}
    out_video_name=${output}${file//.mp4/_out.mp4}
    echo "输出要写入的路径:"${out_video_name}
    ffmpeg -i "${video_name}" -vf "split[a][b];[a]scale=1080:1920,boxblur=10:5[1];[b]scale=1080:ih*1080/iw[2];[1][2]overlay=0:(H-h)/2" -c:v libx264 -crf 18 -preset veryfast -aspect 9:16  -f mp4 "${out_video_name}"  -y
    echo "执行完毕"$video_name
    rm ${video_name}  
done
# ls $dir | while read line
# do
#    
# done

#ffmpeg -i "/workspaces/notes/python/douyin/video/relaxVideo/INTERSTELLAR - Beautiful Space Orchestral Music Mix | Epic Inspirational Sci-Fi Music.mp4" -vf "split[a][b];[a]scale=1080:1920,boxblur=20:5[1];[b]scale=1080:ih*610/iw[2];[1][2]overlay=4:(H-h)/2" -c:v libx264 -crf 18 -preset veryfast -aspect 9:16  -f mp4 test.mp4 -y

# for i in {1..140};

# do
# dir="/workspaces/notes/python/douyin/output/douyin/merge"
# merge_out="/workspaces/notes/python/douyin/output/douyin/merge_out"
# path="/Best Of Tiny Cakes | 1000+ Satisfying Miniature Cake Decorating Recipe Compilation | Tiny Cakes -  - 2022-07-21 23-33-55.mp4"
# origin_line=${path}
# update_line=${origin_line//|/_}
# update_line=${update_line// /}
# mv ${dir}${origin_line} ${merge_out}${update_line}
# video_name=解压$i.mp4
# out_video_name=out_解压$i.mp4

#ffmpeg -i "/workspaces/notes/python/douyin/video/merge/test_0.mp4" -vf "split[a][b];[a]scale=1080:1920,boxblur=10:5[1];[b]scale=1280:ih*1280/iw[2];[1][2]overlay=0:(H-h)/2" -c:v libx264 -crf 18 -preset veryfast -aspect 9:16  -f mp4 "/workspaces/notes/python/douyin/video/cutting_videos/test_0_test1.mp4"  -y

# done


