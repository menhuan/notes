# 开源的数据库控制器

在开发中，我们经常会遇到上线数据库表的情况，代码上我们有git，svn这样优秀的版本控制软件，但是数据库的迭代我们不能使用手工的方式迭代吧？或者说每次上线前手工去数据库执行。这样带来的便捷性就会有很大问题，执行者需要跟开发多次核对数据库执行脚本语句确保正确性后才执行。

为了更好的管理好各个环境的数据库版本控制，我们可以在项目中增加Flyway来管理数据库的版本。

## Flyway是什么呢？

Flyway是一款开源的数据库版本管理工具，利用简单的配置，独立跟踪进行数据版本的迭代。

>Version control for your database.Robust schema evolution across all your environments.
With ease, pleasure and plain SQL.

简单的来说，使用Flyway来帮助我们执行数据库的脚本，打破原先的脚本执行的规范。

### 原先的流程规范

原先部署程序的流程是这样的。

1. 开发人员将代码应用程序，脚本汇总到代码仓库上，比如svn或者git上。
2. DBA或者运维人员根据脚本检查，备份，执行；先完成数据库的升级。
3. 然后再将程序包进行部署安装升级。

### 现在的数据执行流程

1. 开发人员将应用程序，代码上传至代码仓库上。
2. 部署人员直接打包程序、或者使用docker打包程序，替换完成升级。我们执行的Flyway

这样的步骤中没有引入docker的概念，引入docker的概念流程还会存在变动，甚至自动化ci等等都是可以进行流程方面的优化，也带来更高的工作效率。

## Flyway 如何工作

flyway在使用的时候会在一个**空的数据库**里面创建一张表，表名一般是**flyway_schema_history**，老版本的名字是schema_version.

```sql
表结构如下： 记录了表变更的历史
CREATE TABLE `flyway_schema_history` (
  `installed_rank` int(11) NOT NULL,
  `version` varchar(50) DEFAULT NULL,
  `description` varchar(200) NOT NULL,
  `type` varchar(20) NOT NULL,
  `script` varchar(1000) NOT NULL,
  `checksum` int(11) DEFAULT NULL,
  `installed_by` varchar(100) NOT NULL,
  `installed_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `execution_time` int(11) NOT NULL,
  `success` tinyint(1) NOT NULL,
  PRIMARY KEY (`installed_rank`),
  KEY `flyway_schema_history_s_idx` (`success`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

```

![sql](http://jikelearn.cn/2019-04-23-23-46-43.png)

执行流程：

- 在应用中创建的数据库脚本，flyway中称之为migration.
- 程序启动，flyway根据表中 的记录决定是否执行应用中的migration。
- 执行结果会就散出来checksum，进行对比。版本一样里面内容出现变化，migration就会出现问题。所以在线上环境，一定不能修改原先已经完成的migration内容。
- 插入成功后，会在版本库中新增一条记录。

## Java中的使用

### 项目背景

1. 该使用我们是建立在Spring boot 1.5.10版本上。在Spring boot 2.0版本后存在不同的使用。
2. 构建项目使用的gradle4.9+。

### 源码实现

- 在项目中引入flyway

```Java
 compile("org.flywaydb:flyway-core:5.1.3")
```

- 创建文件目录db.migration(默认是这样的文件目录结构，可以更改)

![2019-04-23-23-58-33](http://jikelearn.cn/2019-04-23-23-58-33.png)

- 注意sql脚本的文件命名
    其中的文件名由以下部分组成，除了使用默认配置外，某些部分还可自定义规则。

    prefix: 可配置，前缀标识，默认值V表示Versioned，R表示Repeatable
    version: 标识版本号，由一个或多个数字构成，数字之间的分隔符可用点.或下划线_
    separator: 可配置，用于分隔版本标识与描述信息，默认为两个下划线__
    description: 描述信息，文字之间可以用下划线或空格分隔
    suffix: 可配置，后续标识，默认为.sql
![2019-04-23-23-59-24](http://jikelearn.cn/2019-04-23-23-59-24.png)
  
### 启动springboot项目

启动项目之后就会在数据库中执行脚本中的sql内容。
![2019-04-24-00-02-30](http://jikelearn.cn/2019-04-24-00-02-30.png)

### 更多参数配置列表

在介绍的使用过程中，有很多参数可以进行配置，定制属于自己个性化的flyway内容。**注意不同的Spring boot 项目有不同的配置内容**。

```Java
flyway.baseline-description= # 执行基线时标记已有Schema的描述
flyway.baseline-version=1 # 基线版本默认开始序号 默认为 1. 
flyway.baseline-on-migrate=false # 针对非空数据库是否默认调用基线版本 ， 这也是我们上面版本号从 2 开始的原因
flyway.check-location=false # 是否开启脚本检查 检查脚本是否存在 默认false
flyway.clean-on-validation-error=false # 验证错误时 是否自动清除数据库 高危操作！！！
flyway.enabled=true # 是否启用 flyway.
flyway.encoding=UTF-8 # 脚本编码.
flyway.ignore-failed-future-migration=true # 在读元数据表时，是否忽略失败的后续迁移.
flyway.init-sqls= # S获取连接后立即执行初始化的SQL语句
flyway.locations=classpath:db/migration # 脚本位置， 默认为classpath: db/migration.
flyway.out-of-order=false # 是否允许乱序（out of order）迁移
flyway.placeholder-prefix= # 设置每个占位符的前缀。 默认值： ${ 。 
flyway.placeholder-replacement=true # 是否要替换占位符。 默认值： true 。 
flyway.placeholder-suffix=} # 设置占位符的后缀。 默认值： } 。 
flyway.placeholders.*= # 设置占位符的值。
flyway.schemas= # Flyway管理的Schema列表，区分大小写。默认连接对应的默认Schema。
flyway.sql-migration-prefix=V # 迁移脚本的文件名前缀。 默认值： V 。 
flyway.sql-migration-separator=__ # 迁移脚本的分割符 默认双下划线
flyway.sql-migration-suffix=.sql # 迁移脚本的后缀 默认 .sql
flyway.table=schema_version # Flyway使用的Schema元数据表名称 默认schema_version
flyway.url= # 待迁移的数据库的JDBC URL。如果没有设置，就使用配置的主数据源。
flyway.user= # 待迁移数据库的登录用户。
flyway.password= # 待迁移数据库的登录用户密码。
flyway.validate-on-migrate=true # 在运行迁移时是否要自动验证。 默认值： true 。
```

## 总结

使用flyway帮助我们解决了每次汇总数据库sql的问题，并且也不需要人工在干预数据库的执行。大大提高效率与工作的流程。

参考资料如下：
>https://blog.waterstrong.me/flyway-in-practice/  快速掌握和使用Flyway
https://blog.csdn.net/chenleiking/article/details/80691750 Flyway简介