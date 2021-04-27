# 链接查询

在elasticsearch中也存在关联查询，该关联查询只支持在同一个index下面数据进行查询，不能支持跨index关联关系查询。

从下面几个问题开始我们的解答。

1. 关联是解决什么问题
2. elasticsearch支持的关联查询有几种
3. 几种实现方式
4. 为什么不建议用关联查询

## 链接查询解决的问题

在elasticsearch中提供两种方式进行关联查询，一种是nested，另外一种采用父子关系关联查询。

两种方式都是用来解决不同的文档，链接的查询，不用在一个文档里面查询出来，再经过一个连接访问另外的文档进行查询。

## 两种关联查询

### nested

nested类型数据简单来说就是嵌套性的json数据。

#### 实现该方式

创建一个属性字段是nested类型的，方便后期执行查询语句使用。

```执行语句
put http://ip:端口/test_index

{
    "mapping":{
        "properties":{
            "test_user":{
                "type":"nested"
            }
        }
    }
}
```

插入相关语句

```执行语句
插入类型是nested类型的数据
put http://ip:端口/test_index/type_test/1
{
  "group" : "fans",
  "test_user" : [ 
    {
      "first" : "John",
      "last" :  "Smith"
    },
    {
      "first" : "Alice",
      "last" :  "White"
    }
  ]
}
```

查询nested类型的数据

```执行语句
get http://ip:端口/test_index/_search

{
  "query": {
    "nested": {
      "path": "test_user",
      "query": {
        "bool": {
          "must": [
            { "match": { "user.first": "Alice" }},
            { "match": { "user.last":  "Smith" }}
          ]
        }
      }
    }
  }
}

```

将相关的内容一块展示出来，而不是分别展示匹配。

#### 适合的场景

该关联方式是嵌入到文档中，则数据属于更新多，读少，该方式容易造成对很多数据刷新，造成系统压力过大。

数据采用了冗余字段内容的方式，提高了快速查询。

### 父子查询

父子查询是把在同一个索引下两个不相关的文档，也不再一起的文档，进行关联起来。

#### 实现

先指定相关的字段内容，定义好父子字段关联

```执行语句
PUT http://ip:9200/test_index
{
    "mapping":{
        "properties":{
            "test_join":{
                "type":"json"
                "relation":{
                    "父字段名字":"子字段名字"
                }
            }
        }
    }
}

```
