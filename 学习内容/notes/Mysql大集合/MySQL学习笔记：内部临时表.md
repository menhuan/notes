经常要使用临时表，那究竟什么时候使用呢？

# union 执行流程
1. union需要去重。保证唯一，创建了主键索引。
2. 执行从sql的前后执行。将结果放入到临时表中。
3. 再从临时表中取出来数据

采用union all的话就不需要这么执行了，因为不需要保证去重结果，可以直接把结果一起发送给客户端。

上面需要是因为需要保证前面的中间结果与后面产生的结果做对比，才需要产生临时表。

# group by 操作
在group by 中需要使用中间结果的时候那么就会产生临时表的使用。
1.  创建内存临时表，将结果内存存入到临时表上。
2.  插入相应的数据到内存临时表中。
3.  便利完成后，在进行排序。这里使用到排序是因为内存临时表导致的。数据放入到sort_buffer里完成的。
4.  如果对需求的结果不需要进行排序，可以增加order by null 语句，就不会 进行排序了。

那如果数据量特别大怎么办？内存是不是放的下？

内存临时表是有大小限制的默认是16M,超过这个大小内存临时表会转化为磁盘临时表，默认使用的引擎是InnoDB.

## 优化索引
group by 这个语句都需要构造一个唯一索引的表，那怎么优化呢？

group by 数据之后是无序的，如果有的需求是中有排序的要求，就会增加查询结果的压力。

再次我们可以将无序的结果在使用之前就排好序。

可以使用innodb的索引。在mysql5.7版本中可以使用generated column 机制。 在数据表上新增一个字段并创建 索引。

## 直接排序 优化
不能创建索引的，那就只能做拍需要了，数据大就需要用磁盘临时表。内存转化为磁盘临时表，有消耗。

数据量大，超过内存临时表我的限制，那么就直接采用磁盘临时表，SQL_BIG_RESULT 在sql中加入该字段即可。

## 总结

- 边读边得到结果是不需要临时表。
- join_buffer 是无序的，sort_buffer 是有序的，临时表示二维表结构。
- 执行逻辑需要用到二维表特性，就需要考虑临时表。