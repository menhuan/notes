from time import sleep
import requests
import json
import pandas
import time
import traceback 

from zhihu.notion_util import insert_block
TOKEN = "secret_BNtB6tEMKocFnN2FqmzbCofCOer5HOAfl7PMc97e6Mm"
database_id = "666f8249fd104dd48f195d7d326377f9"
PAGE_SIZE = 100
CSV_PATH= "/workspaces/notes/python/douyin/output/zhihu/csv/采集专栏的数据-后羿采集器.csv"

def read_csv():
    return  pandas.read_csv(CSV_PATH)

def get_all_content():
    url = f"https://api.notion.com/v1/databases/{database_id}/query"
    payload = {"page_size": PAGE_SIZE,
                "sorts": [
                    {
                        "property": "创建时间戳",
                        "direction": "descending"
                    }
                ]
            }
    headers = {
        "Accept": "application/json",
        "Notion-Version": "2022-06-28",
        "Content-Type": "application/json",
        "Authorization": "Bearer " + TOKEN
    }
    response = requests.post(url, json=payload, headers=headers)
    plain_text =  { object_page.get("properties").get("知乎文章昵称").get("title")[0].get("plain_text"):object_page.get("id").replace('-',"") for object_page  in response.json().get("results") if len(object_page.get("properties").get("知乎文章昵称").get("title"))>0  }
    return plain_text

def filter_insert(plain_texts:dict,csv):
    # 过滤要插入的
    return csv[~csv["标题"].isin(plain_texts.keys())]

def filter_update(plain_texts:dict,csv):

    for csv_line in csv:
        if(csv_line["标题"].isin(plain_texts.keys())):
            csv_line["page_id"] = plain_texts[csv_line['标题']]
   
    # 过滤要更新的
    return csv[csv["标题"].isin(plain_texts.keys())]

def insert_notion_page(title,pingjia,xiangqing_title,create_time,update_time,zantong,content =""):
    value = requests.request("POST",
        "https://api.notion.com/v1/pages",
        json={
            "parent": {"type": "database_id", "database_id": database_id},
            "properties": {
                "知乎文章昵称": {
                                "title": [
                                {
                                    "type": "text",
                                    "text": {
                                    "content": title
                                    }
                                }]                            },
                "评价数": {"rich_text": [{"type": "text", "text": {"content": pingjia}}]},
                "详情标题": {"rich_text": [{"type": "text", "text": {"content": xiangqing_title}}]},
                "创建时间戳": {"number": int(create_time) if create_time  == create_time else time.time()  },
                "更新时间戳": {"number": int(update_time) if update_time == update_time else time.time() },
                "赞同" :{"number": int(zantong) if zantong == zantong else 0 }
            }
        #    ,
        #      "children":[
        #     {
        #     "type": "paragraph",
        #     "paragraph": {
        #         "rich_text": contents,
        #         "color": "default"
        #     }
        #     }
        # ]
    
        },
        headers={"Authorization": "Bearer " + TOKEN, "Notion-Version": "2022-06-28"},
        )
    print(value.text)
    # for text in   content.split("\n"):
    #     insert_block(text,block_id=value.json().get("id"))




def update_notion_page(title,pingjia,xiangqing_title,create_time,update_time,page_id):
    value = requests.request("PATCH",
        f"https://api.notion.com/v1/pages/{page_id}",
        json={
            "parent": {"type": "database_id", "database_id": database_id},
            "properties": {
                "知乎文章昵称": {
                                "title": [
                                {
                                    "type": "text",
                                    "text": {
                                    "content": title
                                    }
                                }]                            },
                "评价数": {"rich_text": [{"type": "text", "text": {"content": pingjia}}]},
                "详情标题": {"rich_text": [{"type": "text", "text": {"content": xiangqing_title}}]},
                "创建时间戳": {"number": int(create_time)},
                "更新时间戳": {"number": int(update_time)},
            }
        },
        headers={"Authorization": "Bearer " + TOKEN, "Notion-Version": "2022-06-28"},
        )
    print(value.text)

if __name__ == "__main__":
   plain_text = get_all_content()
   content = read_csv()
   filter_contents = filter_insert(plain_text,content)
   for filter_content in filter_contents.iterrows():
       try:
            print(filter_content[1])
            insert_notion_page(filter_content[1]["标题"],filter_content[1]["评价数"],filter_content[1]["详情标题"]
            ,filter_content[1]["创建时间戳"],filter_content[1]["更新时间戳"],filter_content[1]["赞同"],filter_content[1]["内容"] )
            sleep(3)
       except Exception as e:
            traceback.print_exc()

           
#    update_notion_page
#    print(filter_content)