from pymongo import * 

# 链接MongoDB的客户端
client = MongoClient(host="188.131.139.100",port=27017)

# 设置库
test =client.test
# 设置集合
weather = test.weather

def mongo_insert(collestion,data):
    results = collestion.insert_many(data)
    for result in results:
        id = result ["_id"]
        result["_id"] = result.remove("_id")
