# 数据库的链接

前几天，小伙伴们在群里面讨论进行优化join语句，大家都很积极的发言讨论，结论是**围绕索引与大小表关系来进行操作**，重要的是业务进行绑定。

部分内容来源于极客时间的Mysql实战45讲。

在Mysql的数据库中，我们知道join链接主要使用的有大致三种情况。

- inner join：内连接
- left joinL：左链接
- right join:右链接
  
那这些join我们需要怎么使用呢？并且可以使用的很好，需要我们在数据库里面尝试下。

## 数据准备

该数据表来源于网络。

```Sql
-- 创建测试数据库
CREATE DATABASE join_test CHARSET UTF8;

-- 人员信息表
CREATE TABLE `Persons` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `LastName` char(16) NOT NULL DEFAULT '',
  `FirstName` char(16) NOT NULL DEFAULT '',
  `Address` varchar(128) NOT NULL DEFAULT '',
  `City` varchar(128) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 订单表
CREATE TABLE `Orders` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `OrderNo` int(11) NOT NULL DEFAULT '0',
  `Pid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `Persons` (`LastName`, `FirstName`, `Address`, `City`)
VALUES
('Adams', 'John', 'Oxford Street', 'London'),
('Bush', 'George', 'Fifth Avenue', 'New York'),
('Carter', 'Thomas', 'Changan Street', 'Beijing');

INSERT INTO `Orders` (`OrderNo`, `Pid`)
VALUES (77895, 3), (44678, 3), (22456, 1), (24562, 1), (34764, 65);
```

创建了两个字段的关联关系，并且关联关系这里没有使用索引字段。

## join中使用的 算法

使用的算法有几种，一个是Index Nested-Loop join，另外就是Block Nested-Loop Join.

关联表Peoples,与Order表

### Block Nested-Loop Join

```SQL
explain select  p.* from Persons p
INNER JOIN Orders  o on p.id = o.Pid

```

执行结果图：
![2019-03-25-23-36-54](http://jikelearn.cn/2019-03-25-23-36-54.png)

从图上可以看出驱动表是Peoples,被驱动表是Order,由于我们的关联关系中，被驱动表没有索引，所以在执行关联的时候第二张表要全盘扫描。

那执行流程是怎样的呢？

1. 将驱动表里面需要读取的数据放入到内存中（useing join buffer）。
2. 从被驱动表中取出来一行内容，与内存中的数据进行匹配，符合结果的结果集取出来。
3. 循环执行被驱动表中的所有数据，
4. 被驱动表执行完毕后，在执行驱动表的下一条数据。
5. 2-4，直到驱动表的数据执行完毕，结束。

结果执行的数量是笛卡尔积，进行乘法。

如果join buffer 里面的数据放不下怎么办？

就先取出来一部分驱动表里面的数据，进行与第二个表对比，循环执行，对比结束后清空buffer中的内容，再处理。

从上面可以看出，当被驱动表上没有使用索引的时候会涉及全盘扫描，并且是两个表都全盘扫描，虽然第一个表内容读取到内存中可以加快数据的读取，但是全盘扫描对于性能属于一个损耗。

所以我们需要尽可能的建立索引

### Index Nested-Loop

那么如果我们建立索引了呢？

```SQL

增加索引  索引的名字 与索引的列
CREATE INDEX Pid ON Orders (Pid)

EXPLAIN select  p.* from Persons p
STRAIGHT_JOIN Orders o on  p.id = o.Pid;
```

因为我们在被驱动的表上增加了索引，所以当我们需要的是Persons表中的数据时候，可以利用到索引，执行结果如下。

![2019-03-26-00-21-33](http://jikelearn.cn/2019-03-26-00-21-33.png)

当两个表关联的时候，我们的People表，还有Order表。选择People选择为驱动表，Order为被动表，使用On关联的时候Order 字段上有索引，那么就会使用该执行算法语句。

算法内容如下：

1. 取出驱动表的里面的一条数据
2. 拿着这条数据去表二中查找到合适的结果集，返回。
3. 重复以上循环。

可以看到是使用的循环驱动表中的数据然后去被驱动表中查找，利用索引，减少第二个循环的次数。这样就能加快速度。

### 两个算法总结

从上面可以看出，在选择使用join的时候，一定要避免sql语句将关联的第二个上使用join语句，我们可以每次将自己执行的语句加上explain简单的看下sql执行计划，在优化我们的sql语句。

还有作为驱动表的数据尽可能少，循环的数据就很少了。

这就有我们前面所说的小表作为驱动表，大表加索引。这个概念。

## Join

上面我们说了使用的两个算法，那么我们在执行过程中会遇到哪些呢？

### Inner join

**重点强调，我们的语句都每次使用explain来查看输出**

inner join 与join语句执行结果是一致的，所以在看执行结果，我们不必要关注某个点。

在使用inner join 的时候，以哪个左表还是右表作为依赖表都是存在可能的，所以我们可以使用straight_join来强制使用某个表作为依赖表，并且在使用inner join语句的时候该straight_join 也是一个优化的方式。

强制采用某个表作为依赖表。

```SQL

// 注意当我们使用join语句，需要查询第二个表数据的时候，如果我们的where 条件中没有增加 筛选条件可能会导致使用Block Nested-Loop Join
EXPLAIN select  p.id,o.OrderNo from Persons p
STRAIGHT_JOIN Orders o on  p.id = o.Pid;
```

那么这种情况下怎么优化的呢？
![2019-03-26-00-37-04](http://jikelearn.cn/2019-03-26-00-37-04.png)

从图上可以看出来，我们的表二没有走索引，导致我们数据进行全盘的扫描。

在每一个数据库的表上，我们都了解会有主键索引，那么我们是否可以根据主键索引来排除呢？

sql在执行之前需要根据过滤条件来过滤之后再进行join的数据计算。

我们通过sql来看。

```SQL

EXPLAIN select  p.id,o.OrderNo from Persons p
STRAIGHT_JOIN Orders o on  p.id = o.Pid

where o.OrderNo > 40000

```

