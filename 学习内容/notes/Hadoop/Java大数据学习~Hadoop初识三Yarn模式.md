在上篇文章中我们简单的学习了HDFS简单架构，还有最重要的读写流程。我们都知道在如今的Hadoop中主要有三个重要的执行管理器。一个HDFS,一个MapReduce,还有就是我们今天要看的 YARN。
# 2.0以前的Hadoop
在2.0以前的hadoop中是没有Yarn这个模式管理的。大部分都是独自作战。Hbase做自己的，Spark也是做自己的,等等。这样的话就会造成资源的浪费，不能充分的把资源给利用上。特别是在1.x的版本上容易出现单点故障，不容易扩展的情况。
![1.x](https://upload-images.jianshu.io/upload_images/4237685-a9d1f12fbbc86d37.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
1. 在这里Client的请求都会通过1个JobTracker来分发任务，如果我们的这个JobTracker出现异常。整个集群就没法参与正常工作。
2. 在JobTracker 过多的TaskScheduler 集中过来，容易造成内存,cpu不够用的情况。增加了任务执行失败的风险。
![慕课课程](https://upload-images.jianshu.io/upload_images/4237685-550d351eabf8a6d9.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
因为这些情况，随着发展，Hadoop需要更新的一代管理引擎来帮助我们管理集群-YARN引擎
# 在2.0的YARN 
在新的业务驱动下，发展起来的YARN替代原先的模式。将原先浪费的资源进行合并，共同管理建立在一个模式管理下
![慕课网](https://upload-images.jianshu.io/upload_images/4237685-711b3034c78359a5.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
新的YARN模式如下
![yarn结构](https://upload-images.jianshu.io/upload_images/4237685-2b1d8bde03d49e4b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![新的架构图](https://upload-images.jianshu.io/upload_images/4237685-95e1cd61633c3bf3.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
1. 从图中我们可以看到 原先的JobTracker 被拆分成 资源管理和任务调度监控。
2. 我们来看下如今的架构
 * ResourceManager : 在集群中提供资源的统一管理和调度。并且接收来自客户端的请求。同时不停的接收来自 DataNode上的心跳信息。并且对集群进行管理。
 *  NodeManager ： 
     1. 在整个集群中会有多个该节点。主要用来维护自己节点上资源的管理和使用。
     2. 定时向ResourceManager 汇报自己资源的使用情况。并且 接收来自ResourceManager 各种命令
     3. 启动我们在图中看到的ApplicationMaster.
  * ApplicationMaster :
     1. 该ApplicationMaster  对应我们提交的程序，该程序可以来自Spark,Hbase , MapReduce.该master向管理器YARN申请资源。然后供应用程序使用。
     2. 分配任务给接下来的Container 。包含启动，停止任务。
  * Container 
      1. 封装了CPU ，Memory 等资源的容器。
  * Client 
      1. 通过client来提交任务，进行任务的开始与结束。并且查询任务的执行进度等情况。

>了解了这几个功能名称的作用，我们来看下整个任务执行流程是怎么样的。
![流程图](https://upload-images.jianshu.io/upload_images/4237685-9fc4c8e750fccdd5.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
1. 从Client端发送一个 请求到我们的ResourceManager  上。其中内容应该包含ApplicationMaster,ApplicationMaster的启动命令。本身应用程序的内容。
2. ResourceManager 分配任务到NodeManager上
3. NodeManager根据配置信息进行处理启动ApplicationMaster 。
4.注册到ResourceManager，并且申请到资源返回到我们的ApplicationMaster 上。
5.根据申请到的资源注册到NodeManger上。
6. NodeManager 启动对应的Container上。
在这之间会通过心跳进行任务汇报。然后任务汇报后。进行任务管理。

# 总结
整个yarn的流程和新的结构大概就是如此。新模式解决了原先的单点问题。并且挺高了高可用性和扩展性。一套集群环境就能供多个应用程序使用。YARN模式帮助我们解决掉了资源管理的问题，程序员关注业务开发即可。


