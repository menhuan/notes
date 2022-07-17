from time import sleep
from you_get import common

save_dir = "/Users/ruiqi/data/code/notes/python/douyin/测试下/"
common.any_download(url='https://www.douyin.com/video/7109311452413988134',stream_id='mp4',info_only=False,output_dir=save_dir,merge=True)
sleep(10)