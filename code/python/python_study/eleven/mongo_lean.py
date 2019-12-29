from pymongo import * 

# 链接MongoDB的客户端
client = MongoClient(host="188.131.139.100",port=27017)

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
print(teacher_result.inserted_id)
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
print(teachers_result)
print(teachers_result.inserted_ids)




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
for result in query_results:
    print(result)

# ## 数据更新
condition = {
    "name":"wangMeng"
}

result= teachers.update_one(condition, {'$inc': {'age': 1}})
print(result)
print(result.matched_count,result.modified_count)

condition = {
    "age":{
        "gt":15
    }
}
results = teachers.update_many(condition, {'$inc': {'age': 1}})
print(results)
print(result.matched_count,result.modified_count)

# ########数据删除

condition = {
    "age":27
}


one_result=teachers.delete_one(condition)
print(one_result)
print(one_result.deleted_count)
many_result = teachers.delete_many(condition)
print(many_result)
print(many_result.deleted_count)
