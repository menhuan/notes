from url_save_md import getTextContenByColumn, spilt_content,replace_content
from notion_util import get_notion_all, insert_block
import json


if __name__ == "__main__":
    # 用来插入知乎内容到notion中
    results = get_notion_all(2)
    for row in results:
        properties = row.get("properties")
        url = properties.get("URL")
        text_type = properties.get("类型")
        block_id = row.get("id")
        status = properties.get("状态")
        #print(json.dumps(row))
        #print(json.dumps(row))
        if url and status and status.get("multi_select") and  "待发布" in [status_value.get("name") for status_value in  status.get("multi_select")] and text_type.get("select").get("name") == "专栏" :
            text,_ =getTextContenByColumn(url.get("url"))
            text = replace_content(text)
            texts = spilt_content(text)
            for content in texts:
                #https://www.notion.so/jikelearn/42-a8281df9cbd7489bacc2aa3f2022e545
                insert_block(content,block_id=block_id)