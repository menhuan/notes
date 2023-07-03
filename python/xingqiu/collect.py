import random
import re
import traceback

import requests
import json
import os
import pdfkit
import shutil
import datetime
import urllib.request
from bs4 import BeautifulSoup
from urllib.parse import quote
from urllib.parse import unquote
import base64
import time
import browser_cookie3

ZSXQ_ACCESS_TOKEN = '219B7FFC-3A5E-C0EC-90CF-94EFBD4F7BA1_D43E2E6190D8F1A6'    # 登录后Cookie中的Token（必须修改）
USER_AGENT = 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/111.0.0.0 Safari/537.36'    # 登录时使用的User-Agent（必须修改）
GROUP_ID = '222454121411'   # 知识星球中的小组ID
PDF_FILE_NAME = '电子书.pdf'                       # 生成PDF文件的名字
DOWLOAD_PICS = True                               # 是否下载图片 True | False 下载会导致程序变慢
DOWLOAD_COMMENTS = True                           # 是否下载评论
ONLY_DIGESTS = False                              # True-只精华 | False-全部
FROM_DATE_TO_DATE = False                         # 按时间区间下载
EARLY_DATE = '2022-05-25T00:00:00.000+0800'       # 最早时间 当FROM_DATE_TO_DATE=True时生效 为空表示不限制 形如'2017-05-25T00:00:00.000+0800'
LATE_DATE = '2023-03-10T00:00:00.000+0800'        # 最晚时间 当FROM_DATE_TO_DATE=True时生效 为空表示不限制 形如'2017-05-25T00:00:00.000+0800'
DELETE_PICS_WHEN_DONE = True                      # 运行完毕后是否删除下载的图片
DELETE_HTML_WHEN_DONE = True                      # 运行完毕后是否删除生成的HTML
COUNTS_PER_TIME = 20                              # 每次请求加载几个主题 最大可设置为30
DEBUG = False                                     # DEBUG开关
DEBUG_NUM = 120                                   # DEBUG时 跑多少条数据后停止 需与COUNTS_PER_TIME结合考虑
SLEEP_FLAG = True                                 # 请求之间是否SLEEP避免请求过于频繁
SLEEP_SEC = 10     
SLEEP_SEC_LONG = 30# SLEEP秒数 SLEEP_FLAG=True时生效
SWITCH_NEXT = os.getenv("switch_next",True)

html_template = """
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
</head>
<body>
<h1>{title}</h1>
<br>{author} - {cretime}<br>
<p>{text}</p>
</body>
</html>
"""
htmls = []
num = 0
COOKS = None
def get_cooike():
    global COOKS
    if COOKS is None :
        # 参考https://www.sitstars.com/archives/106/
        COOKS = browser_cookie3.chrome(domain_name=".zsxq.com")  # firefox可以替换为browser_cookie3.firefox()
    return COOKS

def get_data(download_url):

    OVER_DATE_BREAK = False
    global htmls, num
    headers = {
        #'Cookie': 'zsxq_access_token=' + ZSXQ_ACCESS_TOKEN,
        'User-Agent': USER_AGENT
    }
    rsp = requests.get(download_url, headers=headers,cookies=get_cooike())
    with open('temp.json', 'w', encoding='utf-8') as f: # 将返回数据写入temp.json方便查看
        f.write(json.dumps(rsp.json(), indent=2, ensure_ascii=False))
    result = rsp.json().get("succeeded")
    if result:
        with open('temp.json', encoding='utf-8') as f:
            for topic in rsp.json().get('resp_data').get('topics'):
                if FROM_DATE_TO_DATE and EARLY_DATE.strip():
                    if topic.get('create_time') < EARLY_DATE.strip():
                        OVER_DATE_BREAK = True
                        break

                content = topic.get('question', topic.get('talk', topic.get('task', topic.get('solution'))))

                anonymous = content.get('anonymous')
                if anonymous:
                    author = '匿名用户'
                else:
                    author = content.get('owner').get('name')

                cretime = (topic.get('create_time')[:23]).replace('T', ' ')

                text = content.get('text', '')
                text = handle_link(text)
                title = str(num) + '_' + cretime[:16]
                num += 1
                if topic.get('digested') == True:
                    title += '_精华'

                if DOWLOAD_PICS and content.get('images'):
                    soup = BeautifulSoup(html_template, 'html.parser')
                    images_index = 0
                    for img in content.get('images'):
                        url = img.get('large').get('url')
                        local_url = './images/' + str(num - 1) + '_' + str(images_index) + '.jpg'
                        images_index += 1
                        download_image(url, local_url)
                        #img_tag = soup.new_tag('img', src=local_url)
                        #直接写入路径可能无法正常将图片写入PDF，此处写入转码后的图片数据
                        img_tag = soup.new_tag('img', src=encode_image(local_url))
                        soup.body.append(img_tag)
                    html_img = str(soup)
                    html = html_img.format(title=title, text=text, author=author, cretime=cretime)
                else:
                    html = html_template.format(title=title, text=text, author=author, cretime=cretime)

                if topic.get('question'):
                    answer_author = topic.get('answer').get('owner').get('name', '')
                    answer = topic.get('answer').get('text', "")
                    answer = handle_link(answer)

                    soup = BeautifulSoup(html, 'html.parser')
                    answer_tag = soup.new_tag('p')

                    answer = '【' + answer_author + '】 回答：<br>' + answer
                    soup_temp = BeautifulSoup(answer, 'html.parser')
                    answer_tag.append(soup_temp)

                    soup.body.append(answer_tag)
                    html = str(soup) 
                
                files = content.get('files')
                if files:
                    files_content = '<i>文件列表(需访问网站下载) :<br>'
                    for f in files:
                        files_content += f.get('name') + '<br>'
                    files_content += '</i>'
                    soup = BeautifulSoup(html, 'html.parser')
                    files_tag = soup.new_tag('p')
                    soup_temp = BeautifulSoup(files_content, 'html.parser')
                    files_tag.append(soup_temp)
                    soup.body.append(files_tag)
                    html = str(soup)

                comments = topic.get('show_comments')
                if DOWLOAD_COMMENTS and comments:
                    soup = BeautifulSoup(html, 'html.parser')
                    hr_tag = soup.new_tag('hr')
                    soup.body.append(hr_tag)
                    for comment in comments:
                        comment_str = ''
                        if comment.get('repliee'):
                            comment_str = '[' + comment.get('owner').get('name') + ' 回复 ' + comment.get('repliee').get('name') + '] : ' + handle_link(comment.get('text'))
                        else:
                            comment_str = '[' + comment.get('owner').get('name') + '] : ' + handle_link(comment.get('text'))

                        comment_tag = soup.new_tag('p')
                        soup_temp = BeautifulSoup(comment_str, 'html.parser')
                        comment_tag.append(soup_temp)
                        soup.body.append(comment_tag)
                    html = str(soup)

                htmls.append(html)

        # DEBUG 仅导出部分数据时使用
        if DEBUG and num >= DEBUG_NUM:
            return htmls,rsp

        if OVER_DATE_BREAK:
            return htmls,rsp
    else:
        print(f"输出返回的结果内容rsp:{rsp.json()},cookies:{COOKS}")
    return htmls,rsp 
def get_next_data(rsp,download_url,end_time):
    """获取下一页链接内容

    Args:
        rsp (_type_): 上一个请求的返回结果
        download_url (_type_): 下载的链接

    Returns:
        _type_: html内容,用来构成html
    """
    next_page = rsp.json().get('resp_data').get('topics')
    if next_page and SWITCH_NEXT:
        create_time = next_page[-1].get('create_time')
        if  0 >= ( datetime.datetime.strptime(create_time, '%Y-%m-%dT%H:%M:%S.%f%z').timestamp()  - 
            datetime.datetime.strptime(end_time, '%Y-%m-%dT%H:%M:%S').timestamp() ) :
            print(f"时间达到截止时间,crete_time:{create_time},end_time:{end_time}")
            return [],None
        if create_time[20:23] == "000":
            end_time = create_time[:20]+"999"+create_time[23:]
            str_date_time = end_time[:19]
            delta = datetime.timedelta(seconds=1)
            date_time = datetime.datetime.strptime(str_date_time, '%Y-%m-%dT%H:%M:%S')
            date_time = date_time - delta
            str_date_time = date_time.strftime('%Y-%m-%dT%H:%M:%S')
            end_time = str_date_time + end_time[19:]
        else :
            res = int(create_time[20:23])-1
            end_time = create_time[:20]+str(res).zfill(3)+create_time[23:] # zfill 函数补足结果前面的零，始终为3位数
        end_time = quote(end_time)
        if len(end_time) == 33:
            end_time = end_time[:24] + '0' + end_time[24:]
        next_url = download_url + '&end_time=' + end_time
        sleep_time = random.randint(SLEEP_SEC,SLEEP_SEC_LONG)
        print(next_url,"睡眠一会儿:",sleep_time)
        if SLEEP_FLAG:
            time.sleep(sleep_time)
        return get_data(next_url) 

    return [],None

def encode_image(image_url):
    with open(image_url, "rb") as image_file:
        encoded_string = base64.b64encode(image_file.read())
    return 'data:image/png;base64,' + encoded_string.decode('utf-8')

def download_image(url, local_url):
    try:
        urllib.request.urlretrieve(url, local_url)
    except urllib.error.ContentTooShortError:
        print('Network not good. Reloading ' + url)
        download_image(url, local_url)

def handle_link(text):
    soup = BeautifulSoup(text, "html.parser")

    mention = soup.find_all('e', attrs={'type' : 'mention'})
    if len(mention):
        for m in mention:
            mention_name = m.attrs['title']
            new_tag = soup.new_tag('span')
            new_tag.string = mention_name
            m.replace_with(new_tag)

    hashtag = soup.find_all('e', attrs={'type' : 'hashtag'})
    if len(hashtag):
        for tag in hashtag:
            tag_name = unquote(tag.attrs['title'])
            new_tag = soup.new_tag('span')
            new_tag.string = tag_name
            tag.replace_with(new_tag)

    links = soup.find_all('e', attrs={'type' : 'web'})
    if len(links):
        for link in links:
            title = unquote(link.attrs['title'])
            href = unquote(link.attrs['href'])
            new_a_tag = soup.new_tag('a', href=href)
            new_a_tag.string = title
            link.replace_with(new_a_tag)

    text = str(soup)
    text = re.sub(r'<e[^>]*>', '', text).strip()
    text = text.replace('\n', '<br>')
    return text

def make_pdf(htmls,save_pdf_path):
    if len(htmls) == 0 :
        print("html文件是空的,暂不输出PDF文件")
        return 
    html_files = []

    for index, html in enumerate(htmls):
        html_file = str(index) + ".html"
        html_files.append(html_file)
        with open(html_file, "w", encoding="utf-8") as f:
            f.write(html)

    options = {
        "user-style-sheet": "temp.css",
        "page-size": "Letter",
        "margin-top": "0.75in",
        "margin-right": "0.75in",
        "margin-bottom": "0.75in",
        "margin-left": "0.75in",
        "encoding": "UTF-8",
        "custom-header": [("Accept-Encoding", "gzip")],
        "cookie": [
            ("cookie-name1", "cookie-value1"), ("cookie-name2", "cookie-value2")
        ],
        "outline-depth": 10,
    }

    pdf_error_flag = False
    try:
        save_path = os.path.join(save_pdf_path,PDF_FILE_NAME) if save_pdf_path is not None else PDF_FILE_NAME
        pdfkit.from_file(html_files, "电子书.pdf", options=options)
    except Exception as e:
        pdf_error_flag = True
        traceback.format_exc(e)
        print("电子书生成失败！")

    if DELETE_HTML_WHEN_DONE:
        for file in html_files:
            os.remove(file)

    if not pdf_error_flag:
        print("电子书生成成功！")

def download(group_id,file_path_value,end_time,digests=False):
    images_path = r'./images'
    if DOWLOAD_PICS:
        if os.path.exists(images_path):
            shutil.rmtree(images_path)
        os.mkdir(images_path)

    if digests:
        url = 'https://api.zsxq.com/v2/groups/' + group_id + '/topics?scope=digests&count=' + str(COUNTS_PER_TIME)
    else:
        url = 'https://api.zsxq.com/v2/groups/' + group_id + '/topics?scope=all&count=' + str(COUNTS_PER_TIME)

    print("输出的url为:",url)
    htmls,rsp = get_data(url)
    if rsp is  None:
        print("获取结果是空")
        return 
    make_pdf(htmls,file_path_value)
    is_continue = True
    
    while(is_continue and rsp is not None):
        if  rsp.status_code != 200:
            is_continue = False
            print("被终止了")
        else:
            htmls , rsp = get_next_data(rsp,url,end_time)
            make_pdf(htmls,file_path_value)


    if DOWLOAD_PICS and DELETE_PICS_WHEN_DONE:
        shutil.rmtree(images_path) 

if __name__ == '__main__':
    # get_cooike()now.strftime("%Y-%m-%d %H:%M:%Sprint(date_string)
    ten_day = datetime.datetime.now()- datetime.timedelta(days=10)
    download(GROUP_ID,"./data/",ten_day.strftime("%Y-%m-%dT%H:%M:%S"))

    
   
