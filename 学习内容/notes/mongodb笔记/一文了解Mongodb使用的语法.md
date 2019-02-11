在使用数据库之前，我们需要先了解下其基本的数据结构类型。防止我们出现类型不匹配的问题。
支持的数据类型补充的是本人在开发中经常使用的。还有更多的数据类型可以参考官方文档。
#支持的数据类型如下：
|Type|描述|
|--|--|
|String |字符串类型，Mongodb中使用UTF-8是合法的|
|Double|双精度浮点值|
|Boolean|布尔值|
|Object|用于内嵌文档|
|Integer|整数类型，范围根据服务器分为32和64位|
|Arrays|数组或者列表存储多个值存为一个键|
|Date|日期类型|
|Timestamp|时间戳|
|Object ID |对象ID 用于创建文档的ID|

学习完数据类型之后，我们常用的数据库命令。
上篇文章中，我们学习了怎么创建数据集合，怎么查看数据集合。今天来学习下怎么增删改查来操作数据库。
# 操作符
#### 比较运算符
|操作符|	效果
|--|--|
|$gt|	大于|
|$lt|	小于|
|$gte|	大于等于|
|$lte	|小于等于|
|$exists|	存在与否|
|$in	|包含|
|$ne|	不等于|
|$nin|	不包含|
#### 逻辑运算符
|操作符	|效果|
|--|--|
|$exists|	存在与否|
|$or|	或者|
|$and	|并且|
|$not|	不存在|
|$mod	|求模|
|$where|	位置|
**特别的 $exists: true 表示字段存在**
我们这些操作符在下面使用的查询，删除，更新中都会用到。操作符很方便我们去操作这些数据。当然操作符在我们的高级数据聚合操作中也起着十分重要的作用。了解这些操作符能帮助我们更方便的去学习数据
#插入数据
```
//单条插入数据
db.test.insert({"name":"fengfeng","phone":"15612854235"})
db.test.insert({"name":"fengfeng1","phone":"15612854234"})
db.test.insert({"name":"fengfeng2","phone":"15612854233"})
db.test.insert({"name":"fengfeng3","phone":"15612854232"})
db.test.insert({"name":"fengfeng4","phone":"15612854231"})

//插入多条集合数据
db.test.insertMany([
{"name":"fengfen","phone":"15612854225"},
{"name":"fengfen1","phone":"15612854226"},
{"name":"fengfen2","phone":"15612854227"},
{"name":"fengfen3","phone":"15612854228"},
{"name":"fengfen4","phone":"15612854229"}
])
//单条记录
db.test.insertOne({"name":"fengfe","phone":"15612854235"})
```
#查询数据
查询数据中在mongodb 不止find一种方式，我们后面会讲解一种高级操作。本篇文章讲解find操作。后面用的知识也会涉及到这些指令。
```
db.test.find()
//在{} 里面写入匹配条件。更多的匹配条件可以写入到{}里面。相当于mysql中的where and的关系
db.test.find({"name":"fengfen"})
//在find语句中我们还可以使用运算符操作
db.test.find({age:{'$gt':8}}) 
db.test.find().sort({age:1}) 升序排序
db.test.find().sort({age:0})降序排序 
db.test.find().limit(10) 前10个文档
db.test.find().count() 返回集合中有多少文档
```
# 删除文档
```
db.test.remove(条件)  //删除数据库中指定条件的集合文档。与find使用条件一样。
```
# 更新文档
在Mongodb中有两种方式来修改数据
#### 根据update 修改
个人在使用的时候大部分使用的是该方案。
```
db.test.update(条件，新文档，是否新增，是否修改多条)。修改满足条件的集合，如果不存在新增。需要设置好条件
db.test.update({name:"fengfe"},{name:“wowow”,phone:"15612854632"},true,true)

```
#### 使用修改器
```
$inc : 加上一个数字
$set：修改某一个字段。
db.test.update({name:"fengfe"},{$set:{name:'fenwowo'}})
```
# 总结
我们在简单的增删改查中基本上都会用到操作符，所以我们对操作符应该了解的比较清晰。
然后就是Mongodb中的Query条件的编写。使用一个好的Query能帮助我们很好地找到想要的结果。
