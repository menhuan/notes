
# 运行程序 python3 url_save_md.py
#!/usr/bin/python3

from copy import deepcopy
import os
import requests
from pyquery import PyQuery as pq
from bs4 import BeautifulSoup

output_path = os.getenv(
    'OUTPUT_PATH', "/workspaces/notes/python/douyin/booking/")


def getText(url):

    agent = 'Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Mobile Safari/537.36'

    headers = {
        'User-Agent': agent
    }

    html = requests.get(url, headers=headers).text

    reqs = requests.get(url, headers=headers)

    doc = pq(html)

    text = doc("p").text()

    text = "\n".join(text.split())

    soup = BeautifulSoup(reqs.text, 'html.parser')

    print("Title of the website is : ")

    urlTiele = ''

    for title in soup.find_all('title'):

        urlTiele = title.get_text()

        print(title.get_text())

    text = soup.find(attrs={"itemprop": "text"})
    texts = []
    p_text = text.find_all('p')
    for p in p_text:
        print(p.get_text())
        texts.append(p.get_text())
    content = text.get_text()
    print("获取到数据")

    textName2 = urlTiele

    name2 = output_path + textName2 + '.md'
    origin_content = deepcopy(content)
    content = content.replace('\n','')
    t = content.replace('「', '').replace('」', '').replace('！', '').replace('。', '，').replace(
        '？', '，').replace('……', '，').replace('”', '').replace('“', '')
    for index in range(1,20):
       num = str(index) + "."
       t =t.replace(num,"\n" +num + "\n") 
    t =t.replace('0\n',"\n") 
    t = url + "\n" + t
       
    print("输出文件内容：\n", t)  # 输出文件内容
    with open(name2, "w") as f2:
        f2.write(t)
    return origin_content
    # move_folder_name2 = "md文件/" + name2

    # shutil.move(name2,move_folder_name2)


url = 'https://www.zhihu.com/question/462705408/answer/2605285400'

content = getText(url)
# import image2
# image2.show_image(content)