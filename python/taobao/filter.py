
from copy import deepcopy
from numpy import single
import pandas

def filter_ci(excel_data,blacklist,blacklist_guanjianci,contains_cis):
    excel_data = excel_data.drop(excel_data[excel_data['蓝海值']=='蓝海值'].index )
    excel_data['蓝海值'].replace('∞','0',inplace=True)
    excel_data['蓝海值'] = excel_data['蓝海值'].astype('float')

    # 第一个过滤方式 计算
    filter_data = excel_data[(excel_data.在线商品数 > 50)
                             &(excel_data.支付转化率 > 3) 
                             &(excel_data.商城点击占比 < 60)
                             &(excel_data.蓝海值 > 5.0 )
                             &(excel_data.搜索人气 > 7000)]
    filter_data.to_excel("filter.xlsx",sheet_name="在开始过滤前的数据")
    with pandas.ExcelWriter("filter.xlsx", mode='a', engine='openpyxl') as writer:
        # 过滤一些黑名单关键词
        filter_data[filter_data['关键词'].isin(blacklist)].to_excel(writer,sheet_name="blacklist过滤")
        filter_data = filter_data[~filter_data['关键词'].isin(blacklist)]
    
    
        # 过滤长度 
        filter_data[filter_data['关键词'].str.len() > 20 ].to_excel(writer,sheet_name="关键词长度过滤")

        filter_data = filter_data[filter_data['关键词'].str.len() <= 20 ] 
        # 过滤某些词
        filter_balck_ci = ""
        for sinlge in blacklist_guanjianci:
            filter_balck_ci += sinlge + "|"
        filter_balck_ci = filter_balck_ci[0:len(filter_balck_ci)-1]
        filter_data[filter_data['关键词'].str.contains(filter_balck_ci)].to_excel(writer,sheet_name="关键词过滤某些词")
        filter_data = filter_data[~filter_data['关键词'].str.contains(filter_balck_ci) ] 
        
        # 查询出来的词包含某些字段
        contains_ci = ""
        for ci in  contains_cis:
            contains_ci +=  ci + "|"
        contains_ci = contains_ci[0:len(contains_ci)-1]
        filter_data_bak =  filter_data.copy(deep=True)
        filter_data[~filter_data['关键词'].str.contains(contains_ci)].to_excel(writer,sheet_name="包含的词过滤的某些内容")
        filter_data = filter_data[filter_data['关键词'].str.contains(contains_ci)]
        filter_data.drop_duplicates('关键词',inplace=True)
        filter_data.to_excel(writer,'结果值')
        print(filter_data)
        print("运行结束")

    
def run_hanfu():
    #csv_path ="/Users/ruiqi/data/code/notes/python/taobao/淘词参谋_20-10-06.xlsx
    #excel_data = pandas.read_csv(csv_path)
    excel_csv_path ="/Users/ruiqi/data/code/notes/python/taobao/淘词参谋_21-07-33.xlsx"
    excel_data = pandas.read_excel(excel_csv_path)
    exce_path = "/Users/ruiqi/data/code/notes/python/taobao/淘宝汉服存储.xlsx"
    blacklist = pandas.read_excel(exce_path,sheet_name=1)
    blacklist_guanjianci = pandas.read_excel(exce_path,sheet_name=2)
    contains_cis = pandas.read_excel(exce_path,sheet_name=3)

    filter_ci(excel_data,blacklist['过滤词'],blacklist_guanjianci['黑名单'],contains_cis['包含'])

def run_xie():
    excel_csv_path ="/Users/ruiqi/data/code/notes/python/taobao/淘词参谋_拖鞋.xlsx"
    excel_data = pandas.read_excel(excel_csv_path)
    exce_path = "/Users/ruiqi/data/code/notes/python/taobao/拖鞋词保存.xlsx"
    blacklist = pandas.read_excel(exce_path,sheet_name=1)
    blacklist_guanjianci = pandas.read_excel(exce_path,sheet_name=2)
    contains_cis = pandas.read_excel(exce_path,sheet_name=3)

    filter_ci(excel_data,blacklist['过滤词'],blacklist_guanjianci['黑名单'],contains_cis['包含'])
    

if __name__ == '__main__':
    run_hanfu()
   