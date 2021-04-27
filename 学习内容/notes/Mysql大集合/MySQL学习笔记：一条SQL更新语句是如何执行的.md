
# 前言

更新需要走的步骤流程基本上与查询流程类似。
![逻辑架构图](https://upload-images.jianshu.io/upload_images/4237685-d1ab2702f09a9545.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
但是需要涉及到两个重要的日志模块

## redo log

WAL 技术全程是 Write-Ahead Logging ,关键点是先写日志，然后在写磁盘。
一条记录需要更新的时候，InnoDB引擎会先把记录写到redo log 里面，并更新内容，这个时候更新计算完成，

InnoDB引擎会在适当的时候更新到磁盘里面。更新往往会在系统比较清闲的时候做，InnoDB中的redo log是固定大小，可以配置每个文件大小每组文件为4个.

![摘自极客时间](https://upload-images.jianshu.io/upload_images/4237685-5c00aad4d1ca388a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

**write pos** 是当前记录的位置，一遍写一遍后移。写到最后文件在返回来。**checkpoint** 是当前要擦除的位置，也是往后推移，并且循环的，擦除之前需要把记录更新到数据文件中。

write与checkpoint之间存在空白，那么可以进行更新操作，如果两者之间重合，更新暂停下来，将checkpoint推进以下。

利用 redo log 能保证数据库发生异常重启，之前的记录不会丢失这个能力称之为crash-safe 。

## binlog

Server层的日志文件，称为binlog 归档日志。

- binlog 是MySQL的Server实现的，所有引擎可以使用。redo log 是InnoDB引擎独有的

- binlog 日志是逻辑日志，记录的是这个语句的原始逻辑。redo log是 记录的某个数据页上进行了什么修改。

- binlog 是可以追加写入的，不会覆盖以前的日志空间。

执行器与InnoDB引擎执行update流程。

1. 先找到需要修改的行内容，如果在内存中直接返回，没在内存中需要从磁盘读取到内存中。
2. 把拿到的值进行修改，然后调用引擎写入这行数据。
3. 引擎将这行数据更新到内存中，并且把更新记录记录到redo log里面，redo log 处于 prepare状态。然后告知执行器执行完毕，随时提交到事务中。
4. 执行器生成这刚操作的binlog 并把binlog 写入到磁盘中。
5. 执行器调用引擎的提交事务接口，把刚刚写入的redo log 改成提交状态。更新完成。
![更新流程](https://upload-images.jianshu.io/upload_images/4237685-ce06706f927fb915.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 两段提交

两段提交时为了保证日志内容的一致性。不使用两段提交，很容易因为某个阶段的提交失败导致数据恢复出来的数据不一致的情况。
对数据库进行扩容，增加备库提高系统的读能力。常见的做法就是全量备份加上应用binlog实现的。
两段提交保证两个状态保持逻辑上的正确性。
