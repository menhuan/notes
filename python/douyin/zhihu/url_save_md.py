
# 运行程序 python3 url_save_md.py
#!/usr/bin/python3

from copy import deepcopy
import os
import requests
from pyquery import PyQuery as pq
from bs4 import BeautifulSoup


output_path =os.path.join(os.getenv("ROOT_PATH","/workspaces/notes/python/douyin/output"), os.getenv(
    'OUTPUT_PATH', "zhihu/booking/"))

booking_size = int(os.getenv("BOOKING_SIZE",1800))
agent = 'Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Mobile Safari/537.36'

headers = {
    'User-Agent': agent
}

def getText(url):

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
    # 获取问答
    #text = soup.find(attrs={"itemprop": "text"})
    # 获取专栏
    text = soup.find(id="manuscript")

    texts = []
    p_text = text.find_all('p')
    for p in p_text:
        print(p.get_text())
        texts.append(p.get_text())
    content = text.get_text()
    print("获取到数据",content)

    textName2 = urlTiele

    name2 = output_path + textName2 + '.md'
    origin_content = deepcopy(content)
#    content = content.replace('\n','')
    t = content.replace('「', '').replace('」', '').replace('。', '，').replace(
        '？', '，').replace('……', '，').replace('”', '').replace('“', '').replace('『','').replace('』','').replace(
        ']','').replace('[','').replace(':','，').replace("：",'，').replace('?','，').replace("——",'，').replace(
        "，，","，").replace("！",'，').replace('.','').replace('，，','，').replace('最好','嘴好').replace(
        '砍','看').replace('砸','杂').replace('1\n','').replace('2\n','').replace('3\n','').replace('4\n','').replace(
        '5\n','').strip()
        
    # for index in range(1,20):
    #    num = str(index) + "."
    #    t =t.replace(num,"\n" +num + "\n") 
    # t =t.replace('0\n',"\n") 
       
    print("输出文件内容：\n", t)  # 输出文件内容

    #处理敏感词
    t = t.replace('拐卖','柺卖').replace('死','私').replace('下身','下渗').replace('我靠','').replace(
        '警察','经查').replace("手枪",'首抢').replace("筹码",'抽码').replace("你他妈","你").replace("他妈",'').replace(
            '杂碎','砸碎').replace('第一','帝一').replace("极致",'机智').replace("安全",'案全').replace("最",'嘴').replace('㞞',"怂")

    total_size = len(t)
    # 按照1600来分割
    split_size = 1600
    split_result = ''
    start_index = 0
    end_index =1550
    while True:
        if end_index >= total_size:
            split_result +=t[start_index:total_size]
            print("输出数据",start_index,end_index,total_size)
            break
        elif t[end_index] == '，':
            end_index = end_index+1
            print("输出数据",start_index,end_index,total_size)
            split_result +=t[start_index:end_index] +"\n"
            end_index_bak = end_index
            end_index = start_index +split_size*2
            start_index = end_index_bak
        else:
            end_index+=1

    t = url + "\n" + split_result
    with open(name2, "w") as f2:
        f2.write(t)
    return origin_content
    # move_folder_name2 = "md文件/" + name2

    # shutil.move(name2,move_folder_name2)

def run_zhuanlan():
    url = 'https://www.zhihu.com/market/paid_column/1540341389779202048/section/1543974789659947008'
    content = getText(url)
    # import image2
    # image2.show_image(content)

if __name__ == "__main__":
    run_zhuanlan()
    pass




