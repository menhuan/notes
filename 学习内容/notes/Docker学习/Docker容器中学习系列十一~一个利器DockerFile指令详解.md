在我们学习容器中我们往往都会用到DockerFile这个文件，在前两篇的文章中，我们也着重的使用了DockerFile这个文件用来构建我们使用的容器，这样可以用来构建我们自己需要的容器，方便使用和操作。今天我们就具体的来学习下DockerFile需要用到的命令参数。
![命令参数](https://upload-images.jianshu.io/upload_images/4237685-b74883eac9ac5913.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![命令参数](https://upload-images.jianshu.io/upload_images/4237685-d54904e577541fa7.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
从途中我们可以看到这些需要我们使用的命令。现在我们具体的来参考下怎么实现。
# FROM
该命令必须放置在我们命令的第一层，首先就会使用该命令。当然一个DokcerFile中存在多个镜像时，可以多次使用FROM命令
语法：
```
FROM <image> /FROM <image>:<tag>  //tag没有的话默认是latest
FROM ubuntu
```
# MAINTAINER
用来制定维护者信息，也可以是作者信息
```
语法： 
MAINTAINER  <name> 
MAINTAINER   dockerUser fqi@email.com
```
#RUN 
用来运行我们所需要的指令。该命令是构建容器时运行的命令以及提交命令行的结果
```
语法：
RUN command   //该命令是 直接跟shell命令个 在linux系统中默认 /bin/sh -C  windows中默认是 cmd /S /C  
RUN [“executable”,“param1”,“param2”] 函数调用 都一个参数类似可执行文件，后面是参数 使用exec 来执行的 
总的来说命令较长可以使用 \ 来换行。

RUN  [“/bin/bash”, “-c”, “echo hello”]。
RUN apt-get update && apt-get install -y x11vnc xvfb firefox
```
# CMD
用法是启动容器时默认执行的命令。启动容器需要执行的参数，并且一个DockerFile文件中最好只有一个该命令参数。多个话会把最后一个作为最终的目标
```
1. CMD ["executable","param1","param2"]
2. CMD ["param1","param2"]
3. CMD command param1 param2
参数执行与RUN 运行类似。
引号要使用是双引号，原因是参数传递后 docker用来解析为一个json array
```
# LABEL
构建生成的镜像的元数据标签，一个容器可以有多个LABEL文件。
```
例子参考：
LABEL multi.label1="value1" \
multi.label2="value2" \
other="value3"
```
# EXPOSE
声明镜像内服务所需要监听的端口。暴露给外部使用。但是在使用的时候还需要加上-P 参数映射或者使用-p映射为指定端口
```
EXPOSE 22 
EXPOSE 22 8080 9292 
```
# ENV
功能为设置环境变量，可以让其他命令来使用此参数。并且还有使用docker inspect 查看这个环境变量值。
```
语法 ： 
1. ENV <key> <value>
2. ENV <key>=<value> ...
ENV JAVA_HOME /path/java/src
在使用的时候就可以使用
```
#ADD
复制命令，将文件复制到镜像中去，
```
语法：
1. ADD <src>... <dest>  
2. ADD ["<src>",... "<dest>"]
其中 src 可以使本地文件也可以是压缩文件还可以是url  ，dest  是容器内部的绝对路径，也可以是相对于工作目录的相对路径。
ADD test relativeDir/ 
ADD test /relativeDir
ADD http://example.com/foobar /
如果<src>是一个文件夹了，复制整个目录的内容,包括文件系统元数据
```
# COPY
也是复制命令，不过该命令只能复制本地文件。
```
1. COPY <src>... <dest>
2. COPY ["<src>",... "<dest>"]
```
# ENTRYPOINT
用来指定镜像的默认入口，也是启动的默认命令。
```
语法：
1. ENTRYPOINT ["executable", "param1", "param2"]
2. ENTRYPOINT command param1 param2
```
从命令来看与我们的CMD命令十分的相似，但是他们两者有不同的区别。
- 首先ENTRYPOINT不会被运行的command命令覆盖，CMD指定的命令会被覆盖
- 如果两者同时存在，当CMD命令不是一个完整的执行命令时，那么CMD的命令参数会被作为ENTRYPOINT的参数，只一个完整的命令时，谁在最后谁执行。
# VOLUME
创建数据卷的挂载点，将容器内的文件或者其他容器中的文件挂载在该容器中。
```
语法：
VOLUME ["目录"]
1. VOLUME ["/home/log/"]
2. VOLUME /home/log
3. VOLUME /home/log /home/db
```
需要使用数据进行持久化需要该操作。容器使用的是AUFS文件系统。容器关闭的时候数据会丢失，那么数据持久化 就需要使用挂载点来操作。还有一点需要注意的是该命令创建的挂载点是无法指定主机上的目录，自动生成的。
# USER
用来指定运行容器时的用户名或者UID
```
1. USER daemo
2. USER UID
```
# WORKDIR
用来配置工作目录，对RUN,CMD,ENTRYPOINT,COPY,ADD生效，如果目录不存在会创建，也可以设置多次。切换目录的操作相当于是Linux系统中的cd
```
WORKDIR /home/ruiqi
WORKDIR data
RUN pwd
pwd执行的结果是//home/ruiqi/data
当然如果存在环境变量的值也可以 解析环境变量
ENV PATH /path
WORKDIR $PATH/ruiqi
RUN pwd 
pwd 的显示结果是/path/ruiqi
```
# ARG
用来指定一些参数信息。当然该参数可以被 build时使用 --build-arg user =asdasda 指定参数
```
语法：
ARG name = value
ARG user = fruiqi
ARG passwd =12313
```
# ONBUILD 
当该镜像作为其他镜像的基础镜像时，所设定的命令才会执行。
```
语法：
ONBUILD [INSTRUCTION]
比如 ONBUILD  RUN ls 
```
# STOPSIGNAL
容器退出的信号值，当容器退出的时候给系统发送什么样的指令
```
STOPSIGNAL signal
```
# HEALTHCHECK
进行容器的健康检查。
```
语法有两种：

1. HEALTHCHECK [OPTIONS] CMD command
2. HEALTHCHECK NONE
第一个的功能是在容器内部运行一个命令来检查容器的健康状况

第二个的功能是在基础镜像中取消健康检查命令
```
 

[OPTIONS]的选项支持以下三中选项：

-    --interval=DURATION 两次检查默认的时间间隔为30秒

-    --timeout=DURATION 健康检查命令运行超时时长，默认30秒

-    --retries=N 当连续失败指定次数后，则容器被认为是不健康的，状态为unhealthy，默认次数是3

    

注意：

HEALTHCHECK命令只能出现一次，如果出现了多次，只有最后一个生效。

CMD后边的命令的返回值决定了本次健康检查是否成功，具体的返回值如下：

0: success - 表示容器是健康的

1: unhealthy - 表示容器已经不能工作了

2: reserved - 保留值

 
```
例子：

HEALTHCHECK --interval=5m --timeout=3s \
CMD curl -f http://localhost/ || exit 1
  

健康检查命令是：curl -f http://localhost/ || exit 1

两次检查的间隔时间是5秒

命令超时时间为3秒
```
