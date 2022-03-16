
from copy import deepcopy
from numpy import single
import pandas

def filter_ci(excel_data,blacklist,blacklist_guanjianci,contains_cis):
    excel_data['蓝海值'].replace('∞','0',inplace=True)
    excel_data['蓝海值'] = excel_data['蓝海值'].astype('int')

    # 第一个过滤方式 计算
    filter_data = excel_data[(excel_data.在线商品数 > 50)
                             &(excel_data.支付转化率 > 3) 
                             &(excel_data.商城点击占比 < 60)
                             &(excel_data.蓝海值 > 1.0 )]
    # 过滤一些黑名单关键词
    filter_data = filter_data[~filter_data['关键词'].isin(blacklist)]
    
    # 过滤长度 
    filter_data = filter_data[filter_data['关键词'].str.len() < 20 ] 
    # 过滤某些词
    filter_balck_ci = ""
    for sinlge in blacklist_guanjianci:
        filter_balck_ci += sinlge + "|"
    filter_balck_ci = filter_balck_ci[0:len(filter_balck_ci)-1]
    filter_data = filter_data[~filter_data['关键词'].str.contains(filter_balck_ci) ] 
    
    # 查询出来的词包含某些字段
    contains_ci = ""
    for ci in  contains_cis:
        contains_ci +=  ci + "|"
    contains_ci = contains_ci[0:len(contains_ci)-1]
    filter_data_bak =  filter_data.copy(deep=True)
    filter_data = filter_data[filter_data['关键词'].str.contains(contains_ci)]
    filter_data_bak_2 = filter_data.copy(deep=True)
    filter_data_bak_2 = pandas.concat([filter_data_bak_2,filter_data_bak])
    set_diff = filter_data_bak_2.drop_duplicates(subset=['关键词'],keep=False)
    # 从这里筛选出来不同的词语,看是否有药摘出来的词语
    set_diff.to_csv('./set_diff.csv')
    filter_data.to_csv('result.csv')
    print("运行结束")

    
def run():
    # csv_path ="/Users/ruiqi/data/code/notes/python/taobao/淘词参谋_22-16-20.xlsx"
    # excel_data = pandas.read_csv(csv_path)
    excel_csv_path ="/Users/ruiqi/data/code/notes/python/taobao/淘词参谋_13-57-13.xlsx"
    excel_data = pandas.read_excel(excel_csv_path)
    exce_path = "/Users/ruiqi/data/code/notes/python/taobao/淘宝数据存储.xlsx"
    blacklist = pandas.read_excel(exce_path,sheet_name=1)
    blacklist_guanjianci = pandas.read_excel(exce_path,sheet_name=2)
    contains_cis = pandas.read_excel(exce_path,sheet_name=3)

    filter_ci(excel_data,blacklist['过滤词'],blacklist_guanjianci['黑名单'],contains_cis['包含'])
if __name__ == '__main__':
    run()
   