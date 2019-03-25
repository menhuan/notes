# 记录下

在我们使用docker中可能会存在刚开始启动docker 就启动失败的情况，一般我们使用docker 查看日志使用命令docker logs name ，但是如果docker 容器已经退出 就无法查看。

需要使用docker logs -f tomcat 这样的方式来查看打印的日志信息。

## 容器知识记录使用

我们在查看容器具体信息的时候都是 使用 docker inspect 容器名字查看具体配置内容。

但是配置内容有很多我们需要具体的进行格式化查看。

```Linux
jq  是用来格式化命令行内的json 工具内容
docker inspect tomcat -f '{{json .State}}' |grep jq
```

## 套路查询指令的方式

1. top查看，查看内存，cpu等内容找到一个问题再具体去下面详解。
2. 进程问题确认使用pidstat -t -p 进程号 1 显示对应进程的信息，并显示线程信息
3. docker进程获取方式

   ```Docker
   PID = $(docker inspect tomcat -f '{.State.Pid}') 来获取进程号
   ```

4. 再根据pidstat 查看具体的命令查看详情内容。
