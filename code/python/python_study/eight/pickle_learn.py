import pickle

## 字典序列化
content = {
    "str": "this is str",
    "map": {
        "key": "value",
        "num": 1,
    },
    "list": [1, 2, 3]
}
# dumps_dict = pickle.dumps(content)
# print(f"序列化字典数据:{dumps_dict}")
# loads_dict = pickle.loads(dumps_dict)
# print(f"反序列化字典结果:{loads_dict}")

with  open("dump_dict.pickle", "wb") as p:
    pickle.dump(content, p)

with open("dump_dict.pickle", "rb") as p:
    load_dict = pickle.load(p)
    print(f"文件反序列化字典结果:{load_dict}")
## 序列化列表
content_list = [
    content,
    1,
    2,
    3,
    4,
    "list"
]

dumps_list = pickle.dumps(content_list)
print(f"序列化列表数据:{dumps_list}")
loads_list = pickle.loads(dumps_list)
print(f"反序列化列表结果:{loads_list}")

with  open("dump_list.pickle", "wb") as p:
    pickle.dump(content_list, p)

with open("dump_list.pickle", "rb") as p:
    load_dict = pickle.load(p)
    print(f"文件反序列化列表结果:{load_dict}")

# class Student:
#     def __init__(self, name, num, age):
#         self.name = name
#         self.num = num
#         self.age = age
#
#     def update(self):
#         self.num = self.num * 2
#         self.age = self.age * 2
#     def insert(self):
#         pass



# stu = Student("stu", 15, 14)
# dumps_stu = pickle.dumps(stu)
# print(f"序列化列表数据:{dumps_stu}")
# loads_stu = pickle.loads(dumps_stu)
# print(f"反序列化列表结果:{loads_stu}")
# with  open("dump_stu.pickle", "wb") as p:
#     pickle.dump(stu, p)

with open("dump_stu.pickle", "rb") as p:
    load_stu = pickle.load(p)
    print(f"文件反序列化类结果:{load_stu}")
