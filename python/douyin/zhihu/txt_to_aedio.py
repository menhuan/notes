import os
import subprocess
from time import sleep
from notion_util import get_notion_all, getAllBlocks
import json

from yuyin import output
#from url_save_md import getText
root_path = os.getenv("ROOT_PATH","/workspaces/notes/python/douyin/output/douyin")
musics_path = os.path.join(root_path,os.getenv("MUSICS", "musics/"),'download_music.sh') 


def run_zhihu_to_aedio():
     # 第一步 从 notion中获取到数据
    results = get_notion_all(2)
    for row in results:
        print(json.dumps(row))
        properties = row.get("properties")
        block_id = row.get("id")
        status = properties.get("状态")
        if status and status.get("multi_select") and  "待发布" in [status_value.get("name") for status_value in  status.get("multi_select")]  :
            url = properties.get("URL").get("url")
            keyword = properties.get("关键词").get("rich_text")[0].get("plain_text")
            #desc = properties.get("描述").get("rich_text")[0].get("plain_text")
            title_pre = properties.get("视频标题").get("rich_text")[0].get("plain_text")
            content_pre = properties.get("文案前缀").get("rich_text")[0].get("plain_text")
            #desc_len = len(desc)
            #print(url,keyword,desc,title_pre,
            # desc_len)
            run_zhihu_to_aedio_by(block_id,title_pre,keyword,content_pre)
            #getText(url,title_pre,keyword,0,content_pre)
            print(f"符合要求数据{json.dumps(row)}")
            break
        else:
            print(f"不符合数据要求{properties.get('详情标题')} status:=== {status}")



def run_zhihu_to_aedio_by(block_id:str="",video_title:str ="",keyword:str= "",content_pre:str=""):
    contents = getAllBlocks()
    for index,part in enumerate(contents):
        output(content_pre+part,f"{video_title}({index+1}),{keyword}({index+1})") 
        sleep(3)
    result = subprocess.call(f"bash {musics_path}",shell=True)
    sleep(3)



if __name__ == "__main__":
    run_zhihu_to_aedio()