"""
1. 确定爬取的技术，也就是使用解析html还是解析json，分析知乎热门接口
https://www.zhihu.com/api/v3/feed/topstory/hot-lists/total?limit=50&desktop=true
2. 确定需要的数据内容
{
    "title":"",
    "url":"",
    "detail_text":热度，
    "answer_count":"回答条数",
    "comment_count":1,
    "follower_count":1,
}
3. 数据返回
"""
import requests

HEADERS = {
    'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36',
}
ENCODING = "utf-8"

def acquire_hot_list(url):
    hot_result = requests.get(url,headers=HEADERS)
    hot_result.encoding = ENCODING
    hot_json = hot_result.json()
    return hot_json

def parse_zhihu_hot_list(hot_json):
    return [{
        "title":hot["target"]["title"],
        "datail_text":hot["detail_text"],
        "url":"http://zhihu.com/question/"+str(hot["target"]["id"]),
        "comment_count":hot["target"]["comment_count"],
        "follower_count":hot["target"]["follower_count"],
        "answer_count":hot["target"]["answer_count"],
        "imgUrl":hot["children"][0]["thumbnail"]
    }for hot in hot_json["data"]]
