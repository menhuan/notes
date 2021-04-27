"""
json的学习
"""
import json

children = {
    "name": "mengRui",
    "age": 5,
    "height": 120.4,
    "student": False,

}

childrens = [
    {
        "name": "mengRui",
        "age": 5,
        "height": 120.4,
        "student": False,

    },
    {
        "name": "mengRui",
        "age": 5,
        "height": 120.4,
        "student": False,
    }

]

children_json = json.dumps(children)
print("输出返回的数据类型：",type(children_json)," 内容为:",children_json)
childrens_json =json.dumps(childrens)
print("输出返回的数据类型：",type(childrens_json)," 内容为:",childrens_json)
with open("../dict.json",'w') as f:
    json.dump(children,f)

with open("../list.json",'w') as f:
    json.dump(childrens,f)

dict_json = json.loads(children_json)    
print("dict_json类型:",type(dict_json))
list_json = json.loads(childrens_json)
print("list_json数据类型",type(list_json))

with open("../dict.json","r") as f:
    value = json.load(f)
    print("dict_value数据类型",type(value)," 数据内容为",value)
with open("../list.json","r") as f:
    value = json.load(f)
    print("list_json数据类型为",type(value)," 数据内容为",value)