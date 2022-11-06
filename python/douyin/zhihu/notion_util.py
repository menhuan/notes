import requests
import json
import time
TOKEN = "secret_BNtB6tEMKocFnN2FqmzbCofCOer5HOAfl7PMc97e6Mm"
DATABASE_ID = "666f8249fd104dd48f195d7d326377f9"
PAGE_SIZE = 2


def get_notion_all(page=PAGE_SIZE):
    url = f"https://api.notion.com/v1/databases/{DATABASE_ID}/query"
    payload = {"page_size": page,
                "sorts": [
                    {
                        # 下面是根据最后的更新时间来做的.
                        #"property": "更新时间戳",  
                        "direction": "descending",
                        "timestamp": "last_edited_time"
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
    return response.json().get("results")
    #plain_text =  { object_page.get("properties").get("知乎文章昵称").get("title")[0].get("plain_text"):object_page.get("id").replace('-',"") for object_page  in response.json().get("results") if len(object_page.get("properties").get("知乎文章昵称").get("title"))>0  }


def update_page_not_content(create_time,update_time,zantong,pingjia,page_id):
    value = requests.patch(
    f"https://api.notion.com/v1/pages/{page_id}",
    json={
        "parent": {"type": "database_id", "database_id": DATABASE_ID},
        "properties": {
            "评价数": {"rich_text": [{"type": "text", "text": {"content": pingjia}}]},
            "创建时间戳": {"number": int(create_time) if create_time  == create_time else time.time()  },
            "更新时间戳": {"number": int(update_time) if update_time == update_time else time.time() },
            "赞同" :{"number": int(zantong) if zantong == zantong else 0 }
        }
    },
    headers={"Authorization": "Bearer " + TOKEN, "Notion-Version": "2022-06-28"}
    ) 
    print(json.dumps(value.json()))

def getAllBlocks(block_id="a8281df9cbd7489bacc2aa3f2022e545"):
    url = f"https://api.notion.com/v1/blocks/{block_id}/children?page_size=100"

    headers = {
        "accept": "application/json",
        "Notion-Version": "2022-06-28",
        "Authorization": "Bearer " + TOKEN
    }

    response = requests.get(url, headers=headers)
    contents = []
    for  res in response.json().get("results"):
       contents.append(res.get("paragraph").get("rich_text")[0].get("text").get("content"))
    print(contents)
    return contents

def insert_block(content="测试内容",block_id="a8281df9cbd7489bacc2aa3f2022e545"):
   #url = "https://api.notion.com/v1/blocks/a8281df9cbd7489bacc2aa3f2022e545"
    # block_id url后面的字段 或者是page_id 里面对应的id
    # headers = {
    # "accept": "application/json",
    # "Notion-Version": "2022-06-28",
    #  "Authorization": "Bearer " + TOKEN
    # }

    # response = requests.get(url, headers=headers)   
    # print(response.json())
    # a8281df9cbd7489bacc2aa3f2022e545
    url = f"https://api.notion.com/v1/blocks/{block_id}/children"

    text_block  = {
        "children":[
            {
            "type": "paragraph",
            "paragraph": {
                "rich_text": [{
                "type": "text",
                "text": {
                    "content": content,
                }
                }],
                "color": "default"
            }
            }
        ]
        } 

    headers = {
        "Accept": "application/json",
        "Notion-Version": "2022-06-28",
        "Content-Type": "application/json",
        "Authorization": "Bearer " + TOKEN
    }
    
    response = requests.patch(url, headers=headers,json=text_block)
    print("插入块数据:",json.dumps(response.json()))

if __name__ == "__main__":
    #update_page_not_content(create_time=time.time(),update_time=time.time(),zantong=10,pingjia="测试",page_id="97b1e4ac-eafd-43cb-bd3b-10a74ce5d5c8")
    #insert_block("测试内容","97b1e4ac-eafd-43cb-bd3b-10a74ce5d5c8")
    getAllBlocks()
    pass