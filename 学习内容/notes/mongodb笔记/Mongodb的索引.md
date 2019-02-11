前面的两篇文章简单的讲解mongodb基本的操作，安装与使用的方式。
[一文了解Mongodb的使用](https://www.jianshu.com/p/345c66c1aecd)
[初识Mongodb了解其安装与使用](https://www.jianshu.com/p/0b84d76460ca])
想把Mongodb真正的使用好，不是那么简单，不能只会增删改查，还需要练习内功。
内功在武侠小说里面是一个人发展强大起来的重要基础，在我们Mongodb中练习内功也有这样的作用。
开始今天的**内功学习**。

#为什么需要索引
索引：提高查询效率最有效的手段。是解决查询速度缓慢而退出的一种特殊的数据结构，以易于遍历的形式存储部分数据内容；索引数据存储在内存当中，同样加快了索引查找数据的效率。

从索引的简介中了解两个个知识点：
- 目的提高查询速度。
- 索引存储在内存当中。

索引针对的是**查询速度缓慢**，**数据量大**特别是数据量在百万级别，千万级别以及以上的数据量。
索引能大大减少查询时间的损耗。

**eg:**自己写过一段Monodb中的关联查询，数据表数据在百万级别，没有使用索引的时刻查询时间在**7s**,使用索引后查询时间是**0.3s**。效率大大提高。
### Mongodb的索引机制
在往Mongodb中插入文档，每个文档都会经过底层的存储引擎持久化操作之后，会展示一个位置信息。
通过这个位置 信息，就能从存储引擎中读取到数据。不同的存储引擎处处位置的信息不同。选择合适的引擎也能帮助我们快速的查找数据。
**eg:** wiredtiger引擎生成一个KEY值，通过KEY去访问对应的文档。mmapv1引擎里面位置信息是通过文件id与文件内的偏移量决定的。
# 索引的类型
在Mongodb中有很多种索引支持，包含以下索引类型：**单字段索引，联合索引，多key索引，文本索引， 地理位置索引，哈希索引**.不同的索引类型支持不同类型的数据格式和查询需求。
###单字段索引
单字段索引是针对单个字段进行设置索引的操作。
```
//创建索引的语法
db.getCollection('test').createIndex({name:1})
{
    "createdCollectionAutomatically" : false,
    "numIndexesBefore" : 1,
    "numIndexesAfter" : 2,
    "ok" : 1.0
}
数字1 是索引里面的数据按照升序进行排序，需要按照降序排序的索引可以写-1
db.getCollection('test').createIndex({name:-1})
```
代码中针对name字段进行了创建索引，特别是Mongodb的主键_Id索引也是单字段索引。
###联合索引
联合索引在单字段索引上进行了多个字段操作，将多个字段合并为一个索引的联合索引。
```
//创建索引的语法还是一样的。
db.getCollection('test').createIndex({name:1,phone:1})
{
    "createdCollectionAutomatically" : false,
    "numIndexesBefore" : 2,
    "numIndexesAfter" : 3,
    "ok" : 1.0
}

```
在查询字段中引入联合索引，在查询语句操作时需要按照联合索引的顺序进行查询，否则不能走索引的操作。
**eg:**我们创建索引时name在前 phone在后。
```
//find操作
db.getCollection('test').find({name:"qiiq"})
db.getCollection('test').find({name:"qiiq",phone:12512135})
这两种操作是能走联合索引。
//下面两种操作时不能走联合索引
db.getCollection('test').find({phone:12512135,name:"qiiq"})
db.getCollection('test').find({phone:12512135})
```
###多key索引
多key索引：当内容是数组或者list集合创建的一种索引。该索引会为数组中的每个字段创建索引。
### 子文档索引
该索引用来嵌入子文档中的字段进行创建索引。操作也可以有复合索引，单字段索引。
```
db.getCollection('test').createIndex({"user.name":1})
```
# 索引的属性
在Mongodb中不仅支持多个类型的索引，还能对索引增加一些额外的属性。
- 唯一索引：在Mongodb中_id就是利用单字段索引加唯一索引的属性，构成的。
- 部分索引(3.2版本之后新增)：仅索引符合指定过滤器表达式集合中的文档。部分索引有较低的存储要求，降低索引的创建与维护。
- 稀疏索引： 确保索引仅包含具有索引字段的文档的条目。会跳过没有索引字段的文档。
- TTL索引：在一定时间后自动从集合中删除文档的一种索引。
# 索引的操作
索引的操作包含 创建，查看 ，删除，重建操作。
### 索引的创建
我们在前面的操作操作中已经使用索引的创建
```
db.getCollection('test').createIndex({"user.name":1})
db.collection.createIndex（keys,选项）
```
1. keys，要建立索引的参数列表。如：{KEY:1}，其中key表示字段名，1表示升序排序，也可使用使用数字-1降序。
2. options，可选参数，表示建立索引的设置。可选值如下：
   - background，Boolean，在后台建立索引，以便建立索引时不阻止其他数据库活动。默认值 false。
   - unique，Boolean，创建唯一索引。默认值 false。
   - name，String，指定索引的名称。如果未指定，MongoDB会生成一个索引字段的名称和排序顺序串联。
   - dropDups，Boolean，创建唯一索引时，如果出现重复删除后续出现的相同索引，只保留第一个。
   -  sparse，Boolean，对文档中不存在的字段数据不启用索引。默认值是 false。
   -  v，index version，索引的版本号。
   - weights，document，索引权重值，数值在 1 到 99,999 之间，表示该索引相对于其他索引字段的得分权重。
### 查看索引
getIndexes()查看集合的所有索引。
```
db.getCollection('test').getIndexes()
[
    {
        "v" : 2,
        "key" : {
            "_id" : 1
        },
        "name" : "_id_",
        "ns" : "test.test"
    },
    {
        "v" : 2,
        "key" : {
            "name" : 1.0
        },
        "name" : "name_1",
        "ns" : "test.test"
    },
    {
        "v" : 2,
        "key" : {
            "name" : 1.0,
            "phone" : 1.0
        },
        "name" : "name_1_phone_1",
        "ns" : "test.test"
    }
]
```
totalIndexSize()查看集合索引的总大小。
```
db.getCollection('test').totalIndexSize()
69632 //单位字节
```
#索引的优化
###慢查询查看
在mysql数据库中，有慢查询语句的展示，在Mongodb中也有这样的实现名字是Profiling。
更改Mongodb的阈值，有三个级别的性质。
- 0 代表的是不开启慢分析性质。
- 1 根据处理时间将超过阈值的请求记录都记录到system.profile集合中。
- 2 所有记录都将记录到集合system.profile中。
在随着业务的发展，刚开始创建的索引可能不符合现在的业务需求。索引的数量并不是越多越好。
索引能帮助我们提高查询的性能，但是会影响到插入和更新的性能。写入与更新操作每次都需要把索引更新。
在此就可以根据慢请求的日志，进行索引创建的调整。
### 索引分析
Mongodb中有一个命令explain();帮助我们进行查询的慢分析。
```
db.getCollection("test").find().explain()
{
    "queryPlanner" : {
        "plannerVersion" : 1,
        "namespace" : "test.test",
        "indexFilterSet" : false,
        "parsedQuery" : {},
        "winningPlan" : {
            "stage" : "COLLSCAN",  //代表的是进行的全盘扫描，没有利用到索引。当然也是查询条件中没有指定条件语句所致
            "direction" : "forward"
        },
        "rejectedPlans" : []
    },
    "serverInfo" : {
        "host" : "237ae74dd4d9",
        "port" : 27017,
        "version" : "4.0.3",
        "gitVersion" : "7ea530946fa7880364d88c8d8b6026bbc9ffa48c"
    },
    "ok" : 1.0
}
```
在name字段增加索引，执行查询计划。
```
db.getCollection("test").find({"name":"frq"}).explain()
{
    "queryPlanner" : {
        "plannerVersion" : 1,
        "namespace" : "test.test",
        "indexFilterSet" : false,
        "parsedQuery" : {
            "name" : {
                "$eq" : "frq"
            }
        },
        "winningPlan" : {
            "stage" : "FETCH",
            "inputStage" : {
                "stage" : "IXSCAN",
                "keyPattern" : {
                    "name" : 1.0,
                    "phone" : 1.0
                },
                "indexName" : "name_1_phone_1",
                "isMultiKey" : false,
                "multiKeyPaths" : {
                    "name" : [],
                    "phone" : []
                },
                "isUnique" : false,
                "isSparse" : false,
                "isPartial" : false,
                "indexVersion" : 2,
                "direction" : "forward",
                "indexBounds" : {
                    "name" : [ 
                        "[\"frq\", \"frq\"]"
                    ],
                    "phone" : [ 
                        "[MinKey, MaxKey]"
                    ]
                }
            }
        },
        "rejectedPlans" : [ 
            {
                "stage" : "FETCH",  执行完索引后，进行FETCH，读取出最终的
                "inputStage" : {
                    "stage" : "IXSCAN",  // 重点是这里 用到了索引字段，先在索引中查找。
                    "keyPattern" : {
                        "name" : 1.0
                    },
                    "indexName" : "name_1",
                    "isMultiKey" : false,
                    "multiKeyPaths" : {
                        "name" : []
                    },
                    "isUnique" : false,
                    "isSparse" : false,
                    "isPartial" : false,
                    "indexVersion" : 2,
                    "direction" : "forward",
                    "indexBounds" : {
                        "name" : [ 
                            "[\"frq\", \"frq\"]"
                        ]
                    }
                }
            }
        ]
    },
    "serverInfo" : {
        "host" : "237ae74dd4d9",
        "port" : 27017,
        "version" : "4.0.3",
        "gitVersion" : "7ea530946fa7880364d88c8d8b6026bbc9ffa48c"
    },
    "ok" : 1.0
}
```
