# Mysql性能优化神神器explain。一文通透

- [Mysql性能优化神神器explain。一文通透](#mysql性能优化神神器explain一文通透)
  - [前言](#前言)
  - [数据准备](#数据准备)
    - [创建数据表](#创建数据表)
    - [插入数据](#插入数据)
  - [explain命令使用](#explain命令使用)
    - [select_type](#select_type)
    - [type](#type)
    - [rows](#rows)
    - [ref](#ref)
    - [extra](#extra)

## 前言

SQL语句在不同的人手中会写出不同的语句形式，比如经常遇到的SQL慢查询，这时候往往需要针对SQL进行优化。
而Mysql中为保证SQL语句能够高效的运行，提供了一个Explain的命令，用来对SQL语句进行语义分析，供开发者来针对SQL进行优化。

## 数据准备

为了方便整个流程的执行，首先创建好测试数据。

### 创建数据表

SQL中的执行涉及到单表与多表的联合执行，本次创建两张表用来模拟该情况，更多的多表联合执行与两张表执行计划是一样的。

```SQL
CREATE TABLE `users` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `name` varchar(20) NOT NULL DEFAULT '' COMMENT '用户名',
    `sex` tinyint(4) NOT NULL DEFAULT '1' COMMENT '性别',
    `phone` varchar(11) NOT NULL COMMENT '手机号',
    `desc` varchar(200) NOT NULL DEFAULT '' COMMENT '介绍',
    primary key (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '用户表';

CREATE TABLE `order`(
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `phone` varchar(11) NOT NULL COMMENT '手机号',
    `name` varchar(20) NOT NULL COMMENT '用户名',
    primary key (`id`)
) ENGINE =InnoDB default CHARSET=utf8 COMMENT '订单';
```

### 插入数据

为了方便本次没有使用SQL语句，而是使用存储过程创建数据，简单快速也方便。

```SQL
# 创建 存储过程
create procedure insert_user_data()
begin

declare i int ;
declare name varchar(20);
declare phone_num varchar(11);

set @SURNAME = '王李张刘陈杨黄赵吴周徐孙马朱胡郭何高林罗郑梁谢宋唐位许韩冯邓曹彭曾萧田董潘袁于蒋蔡余杜叶程苏魏吕丁任沈姚卢姜崔钟谭陆汪范金石廖贾夏韦傅方白邹孟熊秦邱江尹薛阎段雷侯龙史陶黎贺顾毛郝龚邵万钱严覃武戴莫孔向汤';
set @NAME = '丹举义之乐书乾云亦从代以伟佑俊修健傲儿元光兰冬冰冷凌凝凡凯初力勤千卉半华南博又友同向君听和哲嘉国坚城夏夜天奇奥如妙子存季孤宇安宛宸寒寻尔尧山岚峻巧平幼康建开弘强彤彦彬彭心忆志念怀怜恨惜慕成擎敏文新旋旭昊明易昕映春昱晋晓晗晟景晴智曼朋朗杰松枫柏柔柳格桃梦楷槐正水沛波泽洁洋济浦浩海涛润涵渊源溥濮瀚灵灿炎烟烨然煊煜熙熠玉珊珍理琪琴瑜瑞瑶瑾璞痴皓盼真睿碧磊祥祺秉程立竹笑紫绍经绿群翠翰致航良芙芷苍苑若茂荣莲菡菱萱蓉蓝蕊蕾薇蝶觅访诚语谷豪赋超越轩辉达远邃醉金鑫锦问雁雅雨雪霖霜露青靖静风飞香驰骞高鸿鹏鹤黎';

set i =1;

while  i < 100000 do
    SET phone_num = concat('1',
                    substring(cast(3 + (rand() * 10) % 7 AS char(50)), 1, 1),
                    right(left(trim(cast(rand() AS char(50))), 11), 9));

    set name = concat(substr(@surname,floor(rand()*length(@surname)/3+1),1), substr(@NAME,floor(rand()*length(@NAME)/3+1),1), substr(@NAME,floor(rand()*length(@NAME)/3+1),1));
    insert into users(create_time,name, sex,phone ,`desc`) values(now(),name,rand()*1,phone_num ,'test');
    insert into `order`(phone,name) values(phone_num,name);

    set i=i+1;
end while;

end

# 执行存储过程
call insert_user_data();
# 删除存储过程
drop  procedure if exists insert_user_data ;
```

创建存储过程后，有需要修改就直接使用删除存储过程，再重新创建即可。

## explain命令使用

explain的执行命令`explain select   * from users where id =1 \G;`展示如下：

```SQL
*************************** 1. row ***************************
           id: 1
  select_type: SIMPLE
        table: users
   partitions: NULL
         type: const
possible_keys: PRIMARY
          key: PRIMARY
      key_len: 4
          ref: const
         rows: 1
     filtered: 100.00
        Extra: NULL
1 row in set, 1 warning (0.00 sec)
```

一共12个字段，各个字段的含义如下：

- id: 每一个查询语句都会生成标识符，执行顺序是id从大到小执行
- select_type: 查询的类型，里面包含多种类型[跳转到select_type](#select_type)
- table: 查询的表名，包含关联的表信息
- partitions:匹配的分区
- type: 表示Mysql在表中找到的所需行的方式，这里是表示使用索引的方式。[type](#type)
- possible_keys: 查询语句可能用到的索引
- key: 查询语句真正使用的索引
- key_len: 表示索引中使用的字节数，注意显示的是索引字段中最大可能长度，而不是实际使用长度
- ref: 上述表的连接方式，哪些列或常量被用于查找索引列上的值
- rows: 估算查找到所需记录的需要读取的行数
- extra: 该列查询中包含的其他额外详细信息。

有些字段有更多的类型，以下是详细讲解。

### select_type

用来表示每个查询类型，常用类型如下：

- SIMPLE: 最简单的查询方式，单表查询，不包含UNION以及子查询,例如`select   * from users where id =1`
- PRIMAPY: 表示次查询是是最外层的查询。有子查询的时候展示。`explain select * from users where phone=(select phone from order where id = 10);` 
- UNION: 表示次查询是UNION的第二或随后查询方式,查询语句中存在union关键字`explain select * from users where id = 10 union select * from users where id = 20;`![上传图片](http://jikelearn.cn/img/20200524211726.png)
- DEPENDENT UNION:UNION中的第二个或后面的查询语句，取决于外面的查询
- UNION RESULT,UNION的结果。![看图示](http://jikelearn.cn/img/20200524211835.png)
- SUBQUERY: 子查询中的第一个SELECT.![子查询方式](http://jikelearn.cn/img/20200524211920.png)
- DEPENDENT SUBQUERY: 子查询中的第一个第一个SELECT,取决于外面的查询，当子查询依赖外部的查询结果时会有该内容展示`explain select * from users where phone=(select phone from order where id = users.id ) and id =10;`。![依赖外部](http://jikelearn.cn/img/20200524212156.png)
  
在这里面最常见的类型就属于SIMPLE类型，我们经常使用的多表查询也是SIMPLE类型。例如`explain select * from users left join order o on users.phone = o.phone where users.id =10`![多表查询是SIMPLE类型](http://jikelearn.cn/img/20200524212753.png)

### type

type字段帮助我们来定位查询是否高效，是全表扫描还是索引扫描。
不同的type,代表的性能不一样，顺序如下：
ALL < index < range ~ index_merge < ref < eq_ref < const < system

常用类型如下：

- ALL: 全表扫描，当数据库中的数据巨大时，一个查询还是使用全表扫描的方式，这个查询对数据库的压力影响是巨大的，解决方式是通过添加索引来避免。`explain select * from users;`，可以看到全表扫描扫了200多万行的数据。![全表扫描](http://jikelearn.cn/img/20200524214030.png)

- Index:**$\color{red}{全索引扫描}$**，只扫描所有的索引，而不扫描数据，相对全表扫描来说已经降低部分数据量。同时在Extra字段显示Index.`explain select id from users;`查询语句中id是主键索引，则只查询的是索引数据![主键索引](http://jikelearn.cn/img/20200524220155.png).
- range:建立在索引的基础上进行数据过滤查询，这些能使用索引的标识符有=，<,>,<=,>=, BETWEEN,IN操作符中。`explain select phone from users where id > 10 and id < 20 ;` SQL语句中使用>和<来限定where条件使用的还是range,**$\color{red}{当语句中的字段不是索引时，则不是使用的range}$**![范围查询](http://jikelearn.cn/img/20200524221311.png)
- ref: 查询中使用**非唯一索引**查询，同时在ref列显示使用**哪个列或者常数**。虽然使用了索引，但该索引列的值是可以存在多个的，如phone列出现相同的手机号。`explain select * from order where phone ='16485461071'`![ref](http://jikelearn.cn/img/20200525070101.png)
- ref_eq:用法类似，但比ref好点的是，该类型是知道结果集只有一条。直接知道结果集是一条记录的索引是主键索引与唯一索引，使用该类型是在多表查询时，**条件中包含主键或唯一索引的条件**。`explain select * from users, order where users.id=order.id`![ref_eq使用](http://jikelearn.cn/img/20200525071841.png)
- const: 主键值作为where的条件查询，Mysql优化器会将这次查询转为一个常量看待`explain select * from order where id =10;`![常量查询](http://jikelearn.cn/img/20200525072019.png)
- system: const类型的一个特例，当表中只有一行数据时，会使用system类型

### rows

查询中所需要扫描的行数，我们使用各种索引，优化都是为了减少扫描的行数。

### ref

表示在查询时，表的连接匹配条件，可以是常量，也可以是查询的列`explain select * from users, order where users.id=order.id;`
![ref表示的关联列或常量](http://jikelearn.cn/img/20200525072923.png)

### extra

extra 表示更多的sql查询信息，extra是Mysql查询计划中查询信息重要补充。extra的类型如下：

- Distinct: 在查找到第一行后，不再进行匹配查找更多的数据，对应到查询中的distinct去重查询。
- Using filesort: 代表MYSQL使用的是内存排序或者文件排序，并且该排序没有使用到索引。可以使用合适的索引来修改order by ,group by语句中的条件。
- Using temporary: 使用临时表保存中间结果，常用与Group by ,Order by语句查询中。同样的尽量避免使用临时表来保存中间结果。
- Not exists: 在某些 LEFT JOIN 连接中，MYSQL使用优化器进行优化,改变原有的QUERY的组成优化部分，减少数据访问的次数。
- Using index: 查询时不需要回表，直接通过索引就可以获得查询的数据。
- Using union: 使用or连接各个索引条件时，表明信息表示从处理结果中获取并集。
- Using intersect: 使用and连接各个索引条件时，表明信息表示从处理结果中获取并集。
- Using sort_union/Using sort_intersection:出现在and/or语句中，先查询主键信息，再将结果进行排序合并的数据读取中。
- Using where: 使用Where 字句来限制数据的返回，注意：使用Using Where表示是Mysql服务器将存储引擎返回服务层后再进行条件过滤。
- Using join buffer: 使用了连接缓存，一共两种：块嵌套循环连接Block Nested Loop，以及Index Nested-Loop Join使用索引查询。

## 总结

明白SQL的查询计划，当再写SQL时，多多使用explain语句来看下SQL的查询计划是怎样的，心中对SQL的执行有大概的了解，方能得心运手。

本文中用到的SQL语句上传到[github]()中需要的自取