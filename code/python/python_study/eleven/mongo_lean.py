from pymongo import * 

# 链接MongoDB的客户端
client = MongoClient(host="localhost",port=277017)

# 用来链接MongoDB中的test数据库
db = client.test 
# 或者采用如下方式
db = client["test"]

# 获取集合,两种方式都可以
teachers = db.teacher
teachers = db["teachers"]


# 有了集合就可以操作数据
# 插入单条数据
teacher = {
    "name":"wangMeng",
    "age":27,
    "num":"2672",
    "sex":"woman"
}

teacher_result = teachers.insert_one(teacher)
# 插入的同时会把插入结果返回
print(teacher_result)
# 输出如下


teacher1 ={
    "name":"wangQi",
    "age":27,
    "num":"2673",
    "sex":"woman"

}
teacher2 ={
    "name":"Jack",
    "age":29,
    "num":"2679",
    "sex":"man"

}
teacher3 ={
    "name":"wangYi",
    "age":34,
    "num":"267",
    "sex":"woman"

}
# 插入多条数据
teachers_list = [
    teacher3,
    teacher2,
    teacher1,
]
teachers_result = teachers.insert_many(teachers_list)
# 输出插入的结果





# 数据查询
query = {
    "name":"wangMeng"
}
query_result = teachers.find_one(query)
print(query_result)

query1 ={
    "age":27
}
query_results = teachers.find(query1)

print(query_results)

## 数据更新
condition = {
    "name":"wangMeng"
}
teacher = teachers.find_one(condition)

result= teachers.update_one(condition,{'$set':teacher})
print(result)


########数据删除

condition = {
    "age":27
}

teacher = teachers.find_one(condition)

if teacher is not None:
    one_result=teachers.delete_one(condition)
    print(one_result)
    many_result = teachers.delete_many(condition)
    print(many_result)

