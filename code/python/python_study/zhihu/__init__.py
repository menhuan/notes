import requests

header = {
    "referer":"https://www.zhihu.com/question/364585397/answers/updated",
    "user-agent":"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36",

}

first_url ="https://www.zhihu.com/api/v4/questions/364585397/answers"
params = {
    "include": "data[*].is_normal,admin_closed_comment,reward_info,is_collapsed,annotation_action,annotation_detail,collapse_reason,is_sticky,collapsed_by,suggest_edit,comment_count,can_comment,content,editable_content,voteup_count,reshipment_settings,comment_permission,created_time,updated_time,review_info,relevant_info,question,excerpt,relationship.is_authorized,is_author,voting,is_thanked,is_nothelp,is_labeled,is_recognized,paid_info,paid_info_content;data[*].mark_infos[*].url;data[*].author.follower_count,badge[*].topics",
    "offset":0,
    "limit":20,
    "sort_by":"updated"

}


result = requests.get("https://www.zhihu.com/api/v4/questions/364585397/answers",params=params,headers = header)
json_result = result.json()
print(json_result )

def parse_zhihu_data(url):
    response = requests.get(url,params,header)
    data = response.json()
