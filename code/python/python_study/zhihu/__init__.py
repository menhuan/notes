import ast
import re
from time import sleep

import jieba
import pandas
import requests
from pyecharts import options
from pyecharts.charts import Bar, Line
from pyecharts.commons.utils import JsCode

headers = {
    "referer": "https://www.zhihu.com/question/364585397/answers/updated",
    "user-agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36",

}

# result = requests.get("https://www.zhihu.com/api/v4/questions/371625212/answers", params=params, headers=headers)
# json_result = result.json()
# print(json_result)
# params = {
#     "include": "data[*].is_normal,admin_closed_comment,reward_info,is_collapsed,annotation_action,annotation_detail,collapse_reason,is_sticky,collapsed_by,suggest_edit,comment_count,can_comment,content,editable_content,voteup_count,reshipment_settings,comment_permission,created_time,updated_time,review_info,relevant_info,question,excerpt,relationship.is_authorized,is_author,voting,is_thanked,is_nothelp,is_labeled,is_recognized,paid_info,paid_info_content;data[*].mark_infos[*].url;data[*].author.follower_count,badge[*].topics",
#     "offset": 0,
#     "limit": 20,
#     "platform": "desktop",
#     "sort_by": "default"
# }

first_url = "https://www.zhihu.com/api/v4/questions/371625212/answers?include=data[*].is_normal,admin_closed_comment,reward_info,is_collapsed,annotation_action,annotation_detail,collapse_reason,is_sticky,collapsed_by,suggest_edit,comment_count,can_comment,content,editable_content,voteup_count,reshipment_settings,comment_permission,created_time,updated_time,review_info,relevant_info,question,excerpt,relationship.is_authorized,is_author,voting,is_thanked,is_nothelp,is_labeled,is_recognized,paid_info,paid_info_content;data[*].mark_infos[*].url;data[*].author.follower_count,badge[*].topics&limit=5&offset=0&platform=desktop&sort_by=default"


def parse_zhihu_data(url, first=False):
    response = requests.get(url, headers=headers)
    data_json = response.json()
    pagin = data_json["paging"]
    is_end = pagin["is_end"]
    result_csv = pandas.DataFrame(data_json["data"])
    result_csv.to_csv("zhihu.csv", mode="a", header=first)
    if is_end:
        return
    print(pagin["next"])
    sleep(30)
    parse_zhihu_data(pagin["next"])


def statistic_data(data):
    count = data.count()
    voteup_count = data.sort_values(by="voteup_count", ascending=False)
    comment = data.sort_values(by="comment_count", ascending=False)
    authors = voteup_count.head(10)["author"]
    names = [ast.literal_eval(author)['name'] for author in authors]
    bar = (
        Bar()
            .add_xaxis(names)
            .add_yaxis("赞同数", voteup_count.head(10)["voteup_count"].tolist(), category_gap="60%")
            .set_series_opts(itemstyle_opts={
            "normal": {
                "color": JsCode("""new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                        offset: 0,
                        color: 'rgba(0, 244, 255, 1)'
                    }, {
                        offset: 1,
                        color: 'rgba(0, 77, 167, 1)'
                    }], false)"""),
                "barBorderRadius": [30, 30, 30, 30],
                "shadowColor": 'rgb(0, 160, 221)',
            }})
            .set_global_opts(title_opts=options.TitleOpts(title="点赞排名前10以及评论数量"), xaxis_opts=options.AxisOpts(
                axislabel_opts=options.LabelOpts(rotate=45)
            ))
    )
    line = (
        Line()
            .add_xaxis([ast.literal_eval(author)['name'] for author in comment.head(10)["author"]])
            .add_yaxis("评论数", comment.head(10)["comment_count"])
            .set_global_opts(title_opts=options.TitleOpts(title="评论数量"), xaxis_opts=options.AxisOpts(
            axislabel_opts=options.LabelOpts(rotate=45)
        ))
    )
    bar.render("vote_comment.html")
    line.render("comment.html")







if __name__ == '__main__':
    # parse_zhihu_data(first_url, first=True)
    data = pandas.read_csv("zhihu.csv")
    statistic_data(data)
