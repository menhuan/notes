# -*- coding: utf-8 -*-
from re import A
import jieba
import pandas as pd 
txt ="""
三亚海边凉鞋
三亚旅游凉鞋
三亚凉鞋
三亚凉鞋女
三亚度假凉鞋
凉鞋三亚
"""
words = jieba.lcut(txt,cut_all=True)
count = {}
for word in words:
    if word not in count:
        count[word] = 1
    else:
        count[word] = count[word] + 1

a = sorted(count.items(), key=lambda x: x[1], reverse=True)

df = pd.DataFrame(a,columns=['a', 'b'])
print(df)
df.to_csv("三亚凉鞋.csv")