from notion_util import get_notion_all
import json
from url_save_md import getText


def run_zhihu_to_aedio():
     # 第一步 从 notion中获取到数据
    results = get_notion_all()
    for row in results:
        properties = row.get("properties")
        status = properties.get("状态")
        if status and status.get("multi_select") and  "待发布" in [status_value.get("name") for status_value in  status.get("multi_select")]  :
            url = properties.get("URL").get("url")
            keyword = properties.get("关键词").get("rich_text")[0].get("plain_text")
            desc = properties.get("描述").get("rich_text")[0].get("plain_text")
            title_pre = properties.get("前缀").get("rich_text")[0].get("plain_text")
            desc_len = len(desc)
            print(url,keyword,desc,title_pre,desc_len)
            getText(url,title_pre,keyword,desc_len)
        else:
            print(f"不符合数据要求{json.dumps(row)}")
    

if __name__ == "__main__":
    run_zhihu_to_aedio()