
# 运行程序 python3 url_save_md.py
#!/usr/bin/python3

import requests
from pyquery import PyQuery as pq
from bs4 import BeautifulSoup
import time
import datetime
import shutil
import re   #引用re模块
import html2text


def getText(url):

    agent = 'Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Mobile Safari/537.36'

    headers = {
    'User-Agent':agent
    }

    html = requests.get(url,headers=headers).text

    reqs = requests.get(url,headers=headers)


    doc = pq(html)

    print(doc)

    text = doc("p").text()


    text="\n".join(text.split())


    soup = BeautifulSoup(reqs.text, 'html.parser')
    
    
    print("Title of the website is : ")

    urlTiele = ''

    for title in soup.find_all('title'):

        urlTiele = title.get_text()

        print(title.get_text())

    text = soup.find(attrs={"itemprop": "text"})
    
    #print("文本数据:",text)
    content = text.get_text()
    print(content)


    textName2= urlTiele
 
    name2 = textName2 + '.md'

    print("输出文件内容：\n",content)      #输出文件内容
    content = url +"\n" + content
    t = content.replace('「','').replace('」', '').replace('！', '').replace('。', '，').replace('？', '，').replace('……', '，')

    with open(name2,"w") as f2:
        f2.write(t)
    return content
    # move_folder_name2 = "md文件/" + name2

    # shutil.move(name2,move_folder_name2)


url='https://www.zhihu.com/question/462705408/answer/2484315176'

content = getText(url)
import image2 
image2.show_image(content)








