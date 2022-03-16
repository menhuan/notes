from copy import deepcopy
import pandas
import re

# 从excel中读取数据,然后拆分出来单子词
def excel_pase(excel_path):
    excel = pandas.read_excel(excel_path,sheet_name=2)
    result = set()
    for ci in excel['关键词']:
        for i in ci:
            i = re.sub(u"([^\u4e00-\u9fa5\u0030-\u0039\u0041-\u005a\u0061-\u007a])","",i)
            if(i !=None or i != ''):
                result.add(i)
    return result
            
# end 代表的是这次的字符疮毒
def combination(collections,end,zifu):
    #组合遍历选择,然后从剩下的词里面选择1-(n-1个)词与自己组合.
    collectionList = list(collections)
    zifuLen = len(zifu)
    result = []
    if(zifuLen == end): 
        result.append(zifu)
        return result
    
    for num,index in enumerate(collections):
        if(zifu.find(index) > -1):
            continue
        else:
            zifu_bak = deepcopy(zifu)
            zifu += index
            result.extend(combination(collections=collections,end=end,zifu=zifu))
            zifu = deepcopy(zifu_bak)     
        print(end,num)
    
    return result

def combine(collections):
    for index in range(1,len(collections)+1):
        result = []
        result.extend(combination(collections,index,""))
        print(result)
    data = {"数据":result}
    da = pandas.DataFrame(data=data)
    da.to_csv('./data.csv')

def test_spilt():
    for i in "两件套小吊带+开衫mm":
        print(i)

        

def filter_ci():
    
        
if __name__ == '__main__':
    #result=excel_pase("/Users/ruiqi/Desktop/女装.xlsx")
    result = set(["两","件","套","m"])
    combine(result)