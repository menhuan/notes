import re
from collections import Counter

import numpy
import pandas as pd
import jieba
from PIL import Image

from wordcloud import WordCloud


def acquire_txt_from_csv(csv_path: str):
    """
    acquire txt content
    :param csv_path:
    :return: txt content
    """
    csv = pd.read_csv(csv_path)
    comment =  " ".join([str(row["comment"]) for _, row in csv.iterrows()])
    # 使用正则匹配标点符号,使用空字符串替换
    comment_result=re.sub("[\s+\.\!\/_,$%^*(+\"\']+|[+——！，。？、~@#￥%……&*（）]+", "",comment)
    return comment_result

def parse_words(comment):
    result = jieba.cut(value)
    return Counter(result)

def draw_imgae(counter):
    font_path = "C:/Users/96363/Desktop/markdown/font/HYBeiKeHeiW.ttf"
    # background_image = numpy.array(Image.open("C:\\Users\\96363\\Downloads\\british-library-FQph76jy1mo-unsplash.jpg"))
    wd = WordCloud(
        font_path=font_path,
        height=400,
        width=800,
        background_color="black",
        #  mask=background_image
    ).generate_from_frequencies(counter)
    wd.to_file("world.png")
    # 词图显示
    image = Image.open("world.png")
    image.show()


if __name__ == '__main__':
    value = acquire_txt_from_csv("C:\\Users\\96363\\Desktop\\markdown\\学习内容\\code\\python\\python_study\\test.csv")

