# 快速的复制一个表

## mysqldump的方法

使用工具mysqldump
```Mysql
mysqldump -h$host -P$port -u$user --add-locks --no-create-info --single-transaction  --set-gtid-purged=OFF db1 t --where="a>900" --result-file=/client_tmp/t.sql

```
具体使用的时候可以参考这查询命令使用。

- result-file 指定输出的文件路径，表示在客户端机器上的。
- -skip-extended-inset ,代表的是输出都是一条条的insert语句。
- 其他参数使用的时候可参考文档使用。

###  导出的数据执行表

客户端的命令，根据分好一条条的读取数据，发送到服务端执行。
```MySQL

mysql -h127.0.0.1 -P13000  -uroot db2 -e "source /client_tmp/t.sql"

```

## 导出csv文件内容

### 导出
```MySQL语句
select * from db1.t where a>900 into outfile '/server_tmp/t.csv';
使用 into outfile 临时文件目录。
```
- 语句是保存在服务端
- into outfile 指定文件的生成位置，但是受secure_file_priv 的限制。
    1. 设置为empty，代表不限制生成的位置。
    2. 表示路径的字符串，指定目录或者子目录
    3. 设置为NULL，代表禁止直行该语句。
- 不会覆盖原先的文件，必须保证该文件原先不存在。
- 字段中有换行符存在，文本中也会存在换行符。

该方法不会产生表结构文件，所以需要加一个-tab 参数，导出表结构定义文件和csv数据文件。
```
mysqldump -h$host -P$port -u$user ---single-transaction  --set-gtid-purged=OFF db1 t --where="a>900" --tab=$secure_file_priv

```

### 导入  
```MySQL
load data infile filepath into table db2.t;
```
1. 按照行读取数据
2. 启动事务。
3. 判断字段是否相同，相同执行，不相同事务回滚。
4. 重复步骤三读完后执行事务。

### 备库上怎么执行

主库上导出的数据文本，怎么放到备库执行呢？
1. 主库执行完毕后，会将csv文件内容写入到binlog文件中。
2. 在binlog日志文件中写入， load file local infile filepath into table db2.t ;
3. 把binlog日志传输到备库中。
4. 备库的apply线程执行事务。先将csv文件内容写到临时文件上，在执行load data 语句，插入相同的数据到备库上。

### load data 命令
- 不加local, 读取客服务端的文件，这个文件必须在secure_file_priv指定的目录或者子目录下。
- 加local,读取客户端的文件，客户端需要有文件的权限，先把文件传送给服务端，在执行。


## 物理拷贝方法
不可直接拷贝 表的.frm文件与.idb文件执行。

原因是除了物理文件之外，还需要有在内存的数据字典增加表。

5.6以后增加可传输的方法，通过导出+导入表空间的实现物理拷贝。

执行步骤：
```MySQL
1. create table r like t;
2. alter table r discard tablespace, r.idb文件被删除。
3. flush table for export db1目录下生成t.cfg文件
4. 物理拷贝，cp t.cfg,r.cfg,cp t.idb t.idb文件命令。
5. unlock tables t.cfg 删除。 释放度锁
6. alter table import tablespace，新建立表空间。表的数据在idb中。数据量很大的情况下import语句是很费时间的。
```


