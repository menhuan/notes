# flush privileges 与 grant

grant 语句是赋权语句，flush privileges 是刷新磁盘与内存的数据，保证数据的一致性。

## grant

### 用户

#### 创建

```Mysql

create user 'ua'@'%' identified by 'pa';

```

该语句会创建一个用户 us 和密码使用pa，如果用户存在会修改密码为pa。
其中 ua 与@后面的ip地址构成一个用户， **%**代表所有ip。

### 执行效果

1. 往磁盘上的mysql.user 表插入一行。不指定权限。
2. 往数组acl_users里面插入一个acl_user对象，字段access为0；

### 权限

权限介绍从大到小介绍。

#### 全局权限

作用于整个MySQL的实例。这些信息保存在mysql的user表上。

1. 修改用户权限的语句

   ```Mysql
    grant all privileges on *.* to 'ua'@'%' with grant option;
   ```

   包含两个动作：
   
   - 修改用户表里这个用户的权限。
   - 找到数组里面acl_users中用户对应的对象，将access全部修改为 1 。

2. 影响的范围
   - 作用于全局，同时会刷新磁盘与内存。新创建的链接会直接作用。
   - 已经存在的链接，全局范围还是原先权限，没有收到影响。
3. 回收权限操作

   ```Mysql
   //与上面的修改权限动作一样
    revoke all privileges on *.* from 'ua'@'%';
   ```

#### db权限

```Mysql
grant all privileges on db1.* to 'ua'@'%' with grant option;
作用于数据库中的某个库上。
```

1. 在mysql.db表上插入记录并修改权限。
2. 内存上增加对象到数组acl_dbs中，权限记为1.

grant 在修改db权限的时候是同时对**内存和磁盘起作用的**。

但是对于已经存在的链接影响是不一样的。对db权限修改后，权限判断就会被影响。这是因为acl_db是一个全局数组。能被影响到其他链接

#### 表权限与列权限

- 表权限的内容是放在 **mysql.table_priv**中，

- 列权限的内容是放在 **column_priv_hash**。

```Mysql
create table db1.t1(id int, a int);

表权限
grant all privileges on db1.t1 to 'ua'@'%' with grant option;

列权限
GRANT SELECT(id), INSERT (id,a) ON mydb.mytbl TO 'ua'@'%' with grant option;

```

这两类操作会同时修改 数据表，并且同步内存里面的内容，会影响到已经存在的链接。

## flush privileges

### 作用

flush privileges 命令时用来清空acl_users 数组的， 然后从 mysql.user表中重新加载用户权限。 

同样的对于 db权限与表权限，列权限同样的处理。

在正常情况下，我们使用grant 是没有必要跟着 flush privileges.

### 使用场景

使用不规范导致的。

1. 直接使用dml操作权限表，该操作不会刷新内存中的权限内容。刷新后删除的权限才会被加载到内存上。