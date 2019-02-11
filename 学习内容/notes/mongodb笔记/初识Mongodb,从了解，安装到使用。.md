关于Docker的基础知识相关内容基本上写完了，剩下的就是需要我们去多多熟练学习了。
一篇知识的完毕，不是结束，而是另外的一个开始。我们接下来的系列文章就是Mongodb的知识。
我们也会跟Docker一样逐步的从基础知识开始深入了解。
那么就开始我们Mongodb的学习之旅吧
# 什么是Mongodb
Mongodb是一款开源的文档数据库，提供高性能，高可用性和自动扩展性。
是最近接近于关系型数据库的**Nosql数据库**。
采用了一种叫做BSON结构的数据类型。
## 特点
1. 高性能，具有数据持久性。
   - 对嵌入式文档模型的支持减少系统I/O操作
   - 索引操作，更加的快速。
2. 查询语言丰富，不止有简单的查询find操作，还有**聚合操作**，**文本搜索**等内容
3. 高可用性，利用水平扩展的方式搭建集群。
4. 支持多个存储引擎。
6. 面向集合存储，能十分方便的保存对象类型的数据
7. 能使用二进制进行存储数据
## mongodb的使用场景
1. 用对对象存储或者JSON数据存储的场景。
2. 需要大量的地理位置查询，文本查询。
3. 高伸缩的场景：能快速的利用服务器进行数据库的水平扩展。并且在Mongodb中已经对MapReduce进行了支持。聚合查询等。
4. 缓存层：利用Mongodb进行搭建缓存，避免下层的数据过载。
这是个人已知的场景，并且已经在使用的情况，其他场景情况，请参考google。不过可能有很多文档已经过时。比如Mongodb已经支持Join在聚合操作中，因为可以利用到分片的功能，所以性能还是很高的。

# Mongodb的安装
我们前面学习了Docker的使用，那么本篇文章中我们就可以来使用Docker来安装Mongodb。

首先我们要在自己的环境上安装docker,这个可以参考文章[Docker入门](https://www.jianshu.com/p/4619963629a6)
1. 使用docker命令检查下是否已安装Docker，没有安装的需要安装一下
```
ruiqi@FRQ-PC:~$ docker --version
Docker version 17.03.2-ce, build f5ec1e2
```
2. 开始下载Mongodb
```
ruiqi@FRQ-PC:~$ docker pull mongo
Using default tag: latest
latest: Pulling from library/mongo
3b37166ec614: Pull complete
504facff238f: Pull complete
ebbcacd28e10: Pull complete
c7fb3351ecad: Pull complete
2e3debadcbf7: Pull complete
004c7a04feb1: Pull complete
897284d7f640: Pull complete
af4d2dae1422: Pull complete
5e988d91970a: Pull complete
aebe46e3fb07: Pull complete
6e52ad506433: Pull complete
47d2bdbad490: Pull complete
0b15ac2388a7: Pull complete
7b8821d8bba9: Pull complete
Digest: sha256:4ad50a4f3834a4abc47180eb0c5393f09971a935ac3949920545668dd4253396
Status: Downloaded newer image for mongo:latest
```
3. 启动Mongodb
```
ruiqi@FRQ-PC:~$ docker run  --name mongodb  -p 27017:27017  -d mongo
237ae74dd4d935c64935d0af746d373f479f097d474dcb99380d3c13b392b540
ruiqi@FRQ-PC:~$ docker images
REPOSITORY          TAG                 IMAGE ID            CREATED             SIZE
mongo               latest              052ca8f03af8        10 days ago         381 MB
```
4. 链接Mongodb
我们在这里使用的图形化操作Mongodb的软件Robo 3T。
因为是学习操作，所以里面不加用户权限限制，在正常操作时候都需要加上用户权限。
![链接](https://upload-images.jianshu.io/upload_images/4237685-0d4d66031fd4bb4e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![代表链接成功](https://upload-images.jianshu.io/upload_images/4237685-3179cea72de618e0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
5. 使用Mongodb
  - 打开shell操作，右击localhost。
  - 创建数据库,如果只是用user  数据库，库不存在的时候会直接创建并且换，没有任何操作会删除这可库。我们会看到以下现象。   
 ```
   // 创建库或者切换库  Mongodb中创建库是隐士操作。不需要我们直接创建，可以直接使用
   use newTestDB
   // show dbs   但是不显示我们新增的库。 正如我们上面所说的没有操作会被删除。
   // 接下来这么操作
   use newTestDB
   db.test.insert({"name":"frq","email":"96363167@qq.com"})
   show dbs  //这样我们就会看到新库
 ```
![没有newTestDB库](https://upload-images.jianshu.io/upload_images/4237685-562221f6ecbcce65.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![有newTestDB库](https://upload-images.jianshu.io/upload_images/4237685-7a5b86393d144dae.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

# 总结
我们今天先了解什么是Mongodb，Mongdb的安装，以及简单的创库操作。希望这些能对你有些帮助学习了解到Mongdb
接下来就走入我们的Mongodb的航海中吧



