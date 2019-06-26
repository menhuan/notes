# 相关学习内容

elasticsearch 中没有关系数据库中的库表概念，有索引（index）与类型(type)的对应内容.

## 基础概念

### index

index 相当于数据库中的库，可以根据索引来进行数据的操作。索引分区 有主分区与副本分区，默认是5个主分区，与1个副本分区。副本分区相当于是主分区的一个冗余，并且也能提高数据的并行执行效率。

### type

类型相当于数据库中的表。属于逻辑上的分区类型。

### 文档

文档属于存储的数据内容，JSON格式的字符串。文档中的字段都会有对应的映射，映射关系可以优先定义，也可以在第一次插入数据的时候自动生成相关映射。（这里会有动态映射。）

### 主键ID

ID是文档唯一标识，可以自己提供唯一ID，也可以让系统生成唯一ID.主键id进行hash求值/主分区数，路由数据的保存。
路由也可以自己指定。在创建数据数据的时候可以指定，使用路由查询方便快速。

## 基础数据操作

### 查询操作

查询操作分为单个数据查询，多个数据查询，还有匹配查询几种情况

查询操作可以指定返回的参数内容包含什么，可以减少传输的数据量。
?_source=false 代表source里面内容别禁止传输。
?_source_includes=*.id&——source_excludes=sda  代表source字段内容包含什么与排除什么。
?_source=*.id,sda 简单的写也可。

#### 单个数据根据id查询

get /{index}/{type}/id查询
get /{index}/_doc/1

#### 多个数据根据id查询

get /{index}/{type}/_mget

还可以直接使用 get /_mget

参数体

```Json
{
    "docs":[
        {
            "_index":"test",
            "_type":"_doc",
            "_id" :"1"
        },
         {
            "_index":"test",
            "_type":"_doc",
            "_id" :"2"
        }
    ]
}

```

#### 多个条件查询

get /_search

##### match_all

全匹配
```Json
{
    "query":{
        "match_all":{}
    }
}
与match_all 相反的则是match_none
```

##### 全文本匹配

全文本匹配里面包含了 match + 查询语句，match_phrase + 查询语句（匹配精确的或者类似的），还有multi_match query 匹配，匹配查询的多字段版本内容。

##### match

查询的时候构建一个boolean类型的查询，对要查询的文本进行分析。

```json
{
    "query":{
        "match":{
            "filed" :"内容"
            或者
            "filed": {
                "query":"查询内容"
                "operator":"and/or"
            }
        }
    }
}
```

##### multi_match

```json
{
    "query":{
        "multi_match":{
            "query": "=内容"
            "fields": ["内容1"，"内容2"] // 支持通配符的使用
            "type":"best_fields"， //最佳匹配
        }
    }
}

相当于单个执行并且封装到dis_max 这个查询语句中。

{
    "query": {
    "dis_max": {
      "queries": [
        { "match": { "subject": "brown fox" }},
        { "match": { "message": "brown fox" }}
      ],
    }
  }
}
```

type 有best_fields 还有most_fields 等等类型匹配方式，



### 数据删除

数据的删除是根据id进行删除，也可以根据路由来控制
DELETE /{index}/{type}/id？routing=test，路由里面数据没有的话不会删除数据

### 数据更新

POST /{index}/{type}/id/_update 更新数据

```json
json体内容
```

