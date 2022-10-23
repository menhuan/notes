import requests
import json
TOKEN = "secret_BNtB6tEMKocFnN2FqmzbCofCOer5HOAfl7PMc97e6Mm"
DATABASE_ID = "666f8249fd104dd48f195d7d326377f9"
PAGE_SIZE = 2

def get_notion_all():
    url = f"https://api.notion.com/v1/databases/{DATABASE_ID}/query"
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
    print(json.dumps(response.json()))
    return response.json().get("results")
    #plain_text =  { object_page.get("properties").get("知乎文章昵称").get("title")[0].get("plain_text"):object_page.get("id").replace('-',"") for object_page  in response.json().get("results") if len(object_page.get("properties").get("知乎文章昵称").get("title"))>0  }

if __name__ == "__main__":
    get_notion_all()