今天趁着端午节的最后一天假期，把想看的视频看了下。也走了一遍Hadoop的安装步骤。总的来说流程也明白了很多。这次文章简单的介绍知识点。具体安装步骤大家可以先看网上的。后面有时间的时候在补一篇。
我们的文章是建立在Hadoop已经安装好的情况下。请大家注意再练习的时候首先把环境安装好。
# HDFS 简介
   在HDFS的学习中，我们首先应该明白他具体是什么，为什么会有这个系统。优点和缺点是什么。
* HDFS是什么呢？**HDFS即Hadoop分布式文件系统（Hadoop Distributed Filesystem），以流式数据访问模式来存储超大文件**，运行于商用硬件集群上，是管理网络中跨多台计算机存储的文件系统。
* HDFS 缺点： 要求低时间延迟的数据访问应用，存储量小的数据文件，多用户写入，任意修改的文件都不适合该方式。
# HDFS的 架构
   在整个HDFS架构中，也是采用主从结构Master-Slaves 架构.我们的**NameNode**是主节点，Datanode是数据节点也是我们的Slaves。
   * NameNode 在现在的架构中是有两个节点存活，其中一个节点是备用节点。处于不活跃状态，只有活跃的节点出现问题，才会切换到备用节点上，保证系统的高可用。
     1. 负责客户端的请求响应。
     2. 负责元数据的管理，包含文件的名称，副本系数，Block的存放等。
     3. 用来管理文件系统的命名空间，负责记录文件是如何分割成数据块，以及这些数据块分别被存储到那些数据节点上，它的主要功能是对内存及IO进行集中管理。
  * DataNode ： 在我们该架构中属于数据节点，目的存放数据。
     1. 存储用户的文件及对应的数据块Block。
     2. 定期的向NameNode 发送心跳信息。汇报本身及其所有的block信息，和节点的健康状态。
     3. 虽然是主备结构，但是还具有高可用的性质。在安装集群的时候，建议NameNode与DataNode安装在不同的机架上，这样就算其中一台机架挂掉，还能继续的稳定执行。
    4. 副本存放策略：我们在安装HDFS的时候默认副本系数为三，就是一个文件能用有三个存放目录。同样也是建议放置在不同的机架上。
![机架](https://upload-images.jianshu.io/upload_images/4237685-8b113825fc90a54d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
# Hadoop shell命令
![命令参数](https://upload-images.jianshu.io/upload_images/4237685-cc697c77bd9f7894.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
我们简单的说几个参数命令，很多使用的方式是跟我们的linux命令参数是一样的。
mkdir 命令 创建文件  
ls命令查看目录下文件内容 。
![mdkir与ls](https://upload-images.jianshu.io/upload_images/4237685-3021374d1a8a54f7.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
put命令参数 上传文件到Hadoop上  hdfs dfs -put 文件 目的目录
![put命令](https://upload-images.jianshu.io/upload_images/4237685-48b7606ad3d57f2e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
get命令参数 从hadoop文件上获取文件下载到本地目录下
![get命令](https://upload-images.jianshu.io/upload_images/4237685-15ead345691da6ee.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
还有很多 其他的命令参数 ，包含复制  copy ，rm 删除 ，move移动 等等.。我们在这里就不多说了大家可以自己去学习使用下。
# HDFS中的数据块
我们知道在配置Hadoop时，我们程序默认的数据块大小是128M，该数据块究竟意义是什么呢？
* 首先我们把一个大文件分成很多块，就能充分的利用磁盘IO的性能，不同的块存储在不同的磁盘上。
* 简化了存储系统的设计，将存储系统控制在块的范围内，简化管理。
* 提高了数据备份，和高可用性，我们在数据写入或者读取时，如果有的块数据损坏了，我们采用别的块数据继续进行读取。方便我们获得数据。![块信息展示](https://upload-images.jianshu.io/upload_images/4237685-28fb95c8d6e09bf2.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)



