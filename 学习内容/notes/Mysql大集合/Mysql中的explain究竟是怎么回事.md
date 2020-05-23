# Mysql性能优化神神器explain。一文通透

- [Mysql性能优化神神器explain。一文通透](#mysql性能优化神神器explain一文通透)
  - [前言](#前言)
  - [数据准备](#数据准备)
    - [创建数据表](#创建数据表)
    - [插入数据](#插入数据)
  - [explain命令使用](#explain命令使用)
    - [id](#id)
    - [select_type](#select_type)
    - [table](#table)
    - [type](#type)
    - [possible_keys与keys](#possible_keys与keys)
    - [rows rel fk](#rows-rel-fk)
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

- id: 每一个查询语句都会生成标识符，执行顺序是id从到小执行[跳转到id](#id)
- select_type: 查询的类型，里面包含多种类型[跳转到select_type](#select_type)
- table: 查询的表名
- partitions:匹配的分区
- type:

### id

### select_type

### table

### type

### possible_keys与keys

### rows rel fk

### extra