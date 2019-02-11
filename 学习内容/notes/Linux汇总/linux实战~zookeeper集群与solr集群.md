昨天写的solr单机版，今天就先把solr集群版也写一下吧，因为solr需要用到zookeeper，那么我们首先需要安装zookeeper集群，如果zookeeper只安装一个的话也会造成这个服务挂掉的话，其他服务也会挂。无法使用的情况。
#zookeeper介绍
ZooKeeper是一个分布式的，开放源码的分布式应用程序协调服务，它包含一个简单的原语集，分布式应用程序可以基于它实现同步服务，配置维护和命名服务等。
1. 首先Zookeeper给我们能提供什么呢？
    1） Zookeeper文件系统，其实就是采用了层次性文件的目录结构并且命名也符合常规文件的规范。名字就是叫做Znode，这个Znode有四种类型。
```
1包含持久化目录节点PERSISTENT-客户端与zookeeper断开连接后，该节点依然会存在。
2持久化顺序编号目录节点PERSISTENT_SEQUENTIAL, 该节点也会依旧存在，只是Zookeeper会给该节点进行顺序编号
3、EPHEMERAL-临时目录节点 ，客户端与zookeeper断开连接后，该节点被删除 
4、EPHEMERAL_SEQUENTIAL-临时顺序编号目录节点 ，客户端与zookeeper断开连接后，该节点被删除，只是Zookeeper给该节点名称进行顺序编号 
```             
2. Zookeeper的特点
 ```
  1.最终的一致性：给客户端展示同一个视图。
  2.可靠性：一个消息被其中一个接收到，其他的服务器也会接收到。
  3. 实时性：zookeeper通过在读取数据之前调用sync接口保证数据的一直性。
 4.等待无关：慢的或者失效的client不干预快速的client请求
 5.原子性：更新只有成功与失败两种状态
 6.顺序性：所有的server,同一消息发布顺讯一致。
```
#zookeeper机制
1. 每个server都会存储一份数据
2. 集群启动的时候会从启动的实例中选举一个leader(利用的是Paxos协议)
3. Leader负责处理数据更新等操作（利用Zab协议）
4. 数据更新成功后，会在大多数的Server内容中修改成功数据
具体的zookeeper请看官网吧这些就简单的介绍下。
#zookeeper集群的搭建
 上传的步骤我们就不说了
  1. 首先我们需要创建三个zookeeper，因为该集群也是才去投票的方式选择主从。然后在zookeeper01目录下创建一个data文件夹，在data目录下创建一个myid的文件，然后写入内容 1(01对应1，zookeeper02 对应2 ，zookeeper03对应3)
 2. 然后我们进入conf文件，复制zoo_sample.cfg文件改名为zoo.cfg
 3. 修改zoo.cfg文件中的dataDir=属性，指定为刚创建的文件夹data路径并且把clientPort指定为不冲突的端口号（01:2181、02:2182、03:2183）
 4. 在zoo.cfg中添加如下内容：
server.1=localhost:2881:3881
server.2=localhost:2882:3882
server.3=localhost:2883:3883

![状态如图所示](http://upload-images.jianshu.io/upload_images/4237685-d547d646d452214b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
 5. 启动我们的zookeeper ，在文件bin目录下使用如下命令启动：
./zkServer.sh start
关闭：./zkServer.sh stop
查看服务状态：./zkServer.sh status

![成功图片](http://upload-images.jianshu.io/upload_images/4237685-da82bbe68a29254b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
#solr集群的搭建
跟单机版的solr类似，但是也有不同的。我们需要把solr注册到zookeeper中。
1. 首先我们需要准备多个tomcat。我这里准备了4个 端口改为80 81 82 83 四个。
2. 复制我们单机版 的在各个tomcat中部署solr.复制到tomcat的webapps下即可，配置solrhome，修改web.xml 还有在集群中我们需要修改solr.xml中的端口和ip访问等。可以看我写的单机版solr配置
3. 将配置文件上传到zookeeper中，这个是从solr 解压包里面拿出来的/root/solr-4.10.3/example/scripts/cloud-scripts/zkcli.sh命令上传配置文件。
把/usr/local/solr-cloud/solrhome01/collection1/conf目录上传到zookeeper。
./zkcli.sh -zkhost 192.168.25.154:2181,192.168.25.154:2182,192.168.25.154:2183 -cmd upconfig -confdir /usr/local/solr-cloud/solrhome01/collection1/conf -confname myconf  在这里如果 自己是在远程服务器里面搭建的可以改成自己的远程ip.
5. 使用zookeeper中的zkCli.sh命令查看是否启动成功
6. 告诉solr实例zookeeper的位置。需要修改tomcat的catalina.sh添加
JAVA_OPTS="-DzkHost=192.168.25.154:2181,192.168.25.154:2182,192.168.25.154:2183"
7最后启动查看下是否成功即可
