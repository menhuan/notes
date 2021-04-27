import math
import re
from time import sleep

import pandas
import requests

REFEREER = "https://www.lagou.com/jobs/list_python/p-city_2?&cl=false&fromSearch=true&labelWords=sug&suginput=Python"
HEADERS = {
    'Connection': 'keep-alive',
    'Accept': 'application/json, text/javascript, */*; q=0.01',
    'X-Anit-Forge-Code': '0',
    'X-Requested-With': 'XMLHttpRequest',
    'X-Anit-Forge-Token': 'None',
    'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36',
    'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8',
    'Origin': 'https://www.lagou.com',
    'Sec-Fetch-Site': 'same-origin',
    'Sec-Fetch-Mode': 'cors',
    'Referer': REFEREER,
    'Accept-Encoding': 'gzip, deflate, br',
    'Accept-Language': 'zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7',
}
ENCODING = "utf-8"


def acquire_data(url, pn, kd, sid=None):
    request_data = {
        "first": True if pn == 1 else False,
        "pn": pn,
        "kd": kd,
    }
    if sid is not None:
        request_data["sid"] = sid
    # 反爬虫机制解决，获取cookie
    session = requests.Session()
    session.headers.update(HEADERS)
    session.get(REFEREER)
    request_result = session.post(url, headers=HEADERS, data=request_data, cookies=session.cookies, timeout=3)
    request_result.raise_for_status()
    request_result.encoding = ENCODING
    result_json = request_result.json()
    return result_json


def recruiter_data_to_csv(results, path):
    data_frame = pandas.DataFrame()
    for result in results:
        data_frame = data_frame.append(result, ignore_index=True)
    data_frame.sort_index()
    data_frame.to_csv(path, header=True)


def acquire_pages(result_json):
    result_size = result_json["content"]["positionResult"]["resultSize"]
    total_count = result_json["content"]["positionResult"]["totalCount"]
    pages = int(math.floor(total_count / result_size))
    return pages


def deal_json(result_json, csv_result):
    result = result_json["content"]["positionResult"]["result"]
    csv_result.append(result)


def acquire_recruiter_data():
    page = 1
    csv_result = []
    next_page = 1
    show_id = None
    while page <= next_page:
        data = acquire_data(
            "https://www.lagou.com/jobs/positionAjax.json?city=%e5%8c%97%e4%ba%ac&needAddtionalResult=false", page,
            "Python", sid=show_id)
        deal_json(data, csv_result)
        if page == 1:
            show_id = data["content"]["showId"]
            next_page = acquire_pages(result_json=data)
        print(f"获取page{page} 数据结束")
        page += 1
        sleep(3)

    recruiter_data_to_csv(csv_result, "recruiter.csv")


def deal_data():
    recruiter = pandas.read_csv("recruiter.csv")

    # 工作经验
    num_match = "\d+"
    work_time = []
    for time in recruiter["workYear"]:
        if len(re.findall(num_match, time)) == 0:
            time = "0年"
            work_time.append("0年")
        else:
            work_time.append(time)
    recruiter["work_time"] = work_time
    # 工资进行取区间的均值
    salary_match = "\d+"
    salary_result = []
    for salary in recruiter["salary"]:
        salary_list = re.findall(salary_match, salary)
        if len(salary_list) == 0:
            salary_result.append(0)
        else:
            salary_nums = [int(value) for value in salary_list]
            salary = int((salary_nums[0] + salary_nums[1]) / 2)
            salary_result.append(salary)
    recruiter["month_salary"] = salary_result
    recruiter.to_csv("recruiter.bak.csv")


if __name__ == '__main__':
    # acquire_recruiter_data()
    deal_data()
