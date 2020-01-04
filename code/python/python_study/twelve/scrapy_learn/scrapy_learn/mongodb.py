from pymongo import * 
from scrapy import settings

HOST =settings["HOST"]
PORT =settings["PORT"]
DB =settings["DB"]
COLLECTION =settings["COLLECTION"]

class MongoConnection():
    def __init__(self):
        client =   MongoClient(host = HOST,port=PORT)
        db = client[DB]
        self.collection = client[COLLECTION]

    def add_data_to_mongo(self,item,spider):
        print(item)
        self.collection.insert_many(item)
        return item