
# 运行程序 python3 url_save_md.py
#!/usr/bin/python3

from copy import deepcopy
import os
from time import sleep
import requests
from pyquery import PyQuery as pq
from bs4 import BeautifulSoup

from yuyin import output
import subprocess

output_path =os.path.join(os.getenv("ZHIHU_PATH","/workspaces/notes/python/douyin/output/zhihu/booking/"))
root_path = os.getenv("ROOT_PATH","/workspaces/notes/python/douyin/output/douyin")
musics_path = os.path.join(root_path,os.getenv("MUSICS", "musics/"),'download_music.sh') 
agent = 'Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Mobile Safari/537.36'

headers = {
    'User-Agent': agent
}

def getText(url,video_title_pre,video_title,start_init):

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
    # 替换无用的数字
    t = content.replace('「', '').replace('」', '').replace('。', '，').replace(
        '？', '，').replace('……', '，').replace('”', '').replace('“', '').replace('『','').replace('』','').replace(
        ']','').replace('[','').replace(':','，').replace("：",'，').replace('?','，').replace("——",'，').replace(
        "，，","，").replace("！",'，').replace('.','').replace('，，','，').replace('最好','嘴好').replace(
        '砍','看').replace('砸','杂').replace('1\n','').replace('2\n','').replace('3\n','').replace('4\n','').replace(
        '5\n','').replace('6\n','').replace('7\n','').replace('8\n','').replace('9\n','').replace('0','').replace('】','').replace(
            "【",'').replace(' ','').replace('　','').strip()
        
    # for index in range(1,20):
    #    num = str(index) + "."
    #    t =t.replace(num,"\n" +num + "\n") 
    # t =t.replace('0\n',"\n") 
       
    print("输出文件内容：\n", t)  # 输出文件内容

    #处理敏感词
    t = t.replace('拐卖','柺卖').replace('死','4').replace('下身','下渗').replace('我靠','').replace(
        '警察','经查').replace("手枪",'首抢').replace("筹码",'抽码').replace("你他妈","你").replace("他妈",'').replace(
            '杂碎','砸碎').replace('第一','帝一').replace("极致",'机智').replace("安全",'案全').replace("最",'嘴').replace('㞞',
            "怂").replace("全面",'全棉').replace('唯一','惟一').replace("优秀","右袖").replace("全国","劝过").replace("顶级","定级").replace(
                "烧","少").replace("支付宝",'zhiFuBao')
        

    # 替换作者昵称
    t = t.replace("赵子君",'赵雅思').replace("沈修白",'沈元白')
    # 增加前缀
    t = ""  + t
    total_size = len(t)
    # 按照1600来分割
    split_size = 1000
    split_result = ''
    start_index =start_init
    end_index =1050
    # 修改前缀
    page_prefix = t[0:start_index]
    
    index_prefix = 1
    part_contents = []
    while True:
        current_content =""
        if end_index >= total_size:
            current_content =page_prefix + t[start_index:total_size]
            split_result += page_prefix +t[start_index:total_size]
            with open(output_path + textName2 + "_"+ str(index_prefix)  + '.md', "w") as f2:
                part_contents.append(current_content)
                f2.write(current_content)  
            break
        elif t[end_index] == '，':
            end_index = end_index+1
            current_content = page_prefix + t[start_index:end_index]
            #output(current_content,f"{video_title}({index_prefix})")
            split_result += page_prefix+ t[start_index:end_index] +"\n"
            end_index_bak = end_index
            end_index = end_index +split_size
            start_index = end_index_bak
            with open(output_path + textName2 + "_"+ str(index_prefix)  + '.md', "w") as f2:
                part_contents.append(current_content)
                f2.write(current_content)   
            index_prefix+=1

        else:
            end_index+=1
    for index,part in enumerate(part_contents):
        output(part,f"{video_title_pre}({index+1}),{video_title}({index+1})") 
        sleep(3)
    result = subprocess.call(f"bash {musics_path}",shell=True)
    # for index,part in enumerate(part_contents):
    #     run(f"{video_title}({index+1})",f"{video_title}({index+1})")
    t = url + "\n" + split_result
    with open(name2, "w") as f2:
        f2.write(t)
    return origin_content
    # move_folder_name2 = "md文件/" + name2

    # shutil.move(name2,move_folder_name2)

def run_zhuanlan():
    start_index = len("""六岁那年，我的绿茶闺蜜将错就错成了言氏集团的千金。
十年后，言家意外发现抱养错了人，把我接了回来。
言家人都以为我是小可怜，把我宠上天，只有我的绿茶妹妹，挑拨离间想让我在
言家待不下去。
生存法则第一条：只要我比她更茶，她就没办法恶心我。""")
    print("裁剪长度:",start_index)

    url = 'https://www.zhihu.com/market/paid_column/1500563077195636736/section/1555993658578702336'
    content = getText(url,"你们说前科闺蜜有多坏","前科闺蜜",start_index)
    # import image2
    # image2.show_image(content)

if __name__ == "__main__":
    run_zhuanlan()
    pass




