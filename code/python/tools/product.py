import pandas


# 选择商品的标红数据
def choose_porduct_mark_red(data,to_path):
    # 注意这里的数据是已经按照按照店铺排过序了. 不用排序 在这里过滤就行.
    data["销量大于评论"]=data[["30天销量","评论数"]].apply(lambda x:x["30天销量"]>x["评论数"],axis=1)
    # 销量 介于50 到1000 之间   50可以稍微放开点 10 以上先看看. 
    
    # 销量大于评论 评论相对少
    xiaoliang = data["销量大于评论"].map(lambda x:x)
    tianxiaolaing = data["30天销量"].map(lambda x:x >10)
    operate = data["操作"].map(lambda x :  x.find("同款") >=0)
    # 数量少的话 淘宝的可不过滤.
    taobao = data["店铺类型"].map(lambda x: x=="淘宝") 
    
    # 优先选择淘宝店铺
    data[tianxiaolaing ].sort_values(by="30天销量",ascending = False).to_csv(to_path)
    pass

csv_path = "/Users/ruiqi/data/code/notes/code/python/tools/超火半拖鞋.csv"
to_path  = "./超火半拖鞋过滤.csv"
csv = pandas.read_csv(csv_path)
choose_porduct_mark_red(csv,to_path)
