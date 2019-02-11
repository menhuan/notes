昨天的文章我们简单的说了下Hadoop中HDFS的内容，今天我们来了解下HDFS的 都写入流程。当然该流程也是一个经常面试遇到的一个问题。
# HDFS的 写入流程
  在HDFS客户端想要往HDFS写入文件，流程如下
![写入数据流程图](https://upload-images.jianshu.io/upload_images/4237685-755268f533795e57.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
1. 首先客户端发送请求，携带者配置文件信息，还有文件的路径
2. 访问到NameNode后，NameNode根据文件路径，去查看该路径是否存在，不存在继续将文件进行Block切割还有DataNode信息。存在追缴内容到对应的文件上
3.客户端接收到NameNode信息后，根据返回的Block信息访问到最近的DataNode上，打开Socket链接，进行访问，然后一个DataNode会接着链接第二个DataNode，第二个会继续链接第三个将pipeline链接构成，再返回给客户端。
4. client开始传输数据到最近的DataNode上传输第一个Block，按照一个包packet为64K的大小进行写入，在写入的时候会进行数据校验，防止数据写入出现错误的问题。然后形成链式操作到第三个datanode上结束。
5. 一个Block传输完毕后，再回接着传输第二个Block。到对应的DataNode上。
整个流程就会循环的 执行，传输完毕整个文件。
6. 最后关闭链接的访问，告知namenode完成任务。

# HDFS的读取过程

![读取过程](https://upload-images.jianshu.io/upload_images/4237685-12da2462e6faa7e0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

1. 客户端访问NameNode，根据发送的路径进行检查。
2. 存在 ,返回给客户端Block还有存储的DataNode信息。不存在，返回给客户端异常信息
3. 客户端根据发送的Block还有DataNode信息，与其建立Socket链接，DataNode开始发送数据，还是按照packet来作为单位进行校验。
4. 本地客户端进行接收。先缓存下来、然后根据块进行数据汇总。完成读取操作。
5. 最后关闭链接的访问。
