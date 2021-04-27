本来今天想写下实战的教程的，但是下载spark的内容太慢了。就打算写下Docker容器中需要的Docerk-compose.
# Docker-compose
### 简介
docker-compose 是一个用户用来定义和运行多个容器的Docker程序。我们在程序中一般是使用yaml来操作。其中定义的每个服务都必须通过 image 指令指定镜像或 build 指令（需要 Dockerfile）来自动构建。
使用docker-compose 基本上分为三个步骤：
1. Dockerfile 定义应用的运行环境。
2. docker-compose.yml定义组成应用的各种服务。
3. docker-compose op -d 启动整个应用
### 安装 Docker-compose
1. 首先检测下电脑上是否安装了crul。
```
docker@ubuntu:~/Desktop/spark/curl-7.55.1$ curl --version
curl 7.35.0 (x86_64-pc-linux-gnu) libcurl/7.35.0 OpenSSL/1.0.2n zlib/1.2.11 libidn/1.33 librtmp/2.3
Protocols: dict file ftp ftps gopher http https imap imaps ldap ldaps pop3 pop3s rtmp rtsp smtp smtps telnet tftp 
Features: AsynchDNS GSS-Negotiate IDN IPv6 Largefile NTLM NTLM_WB SSL libz TLS-SRP 

//存在是上面这种情况。如果不存在那么需要进行下载

docker@ubuntu:~/Desktop/spark/curl-7.55.1$ sudo apt  update 
docker@ubuntu:~/Desktop/spark/curl-7.55.1$ sudo apt  install curl
docker@ubuntu:~/Desktop/spark/curl-7.55.1$ sudo curl -L https://github.com/docker/compose/releases/download/1.21.2/docker-compose-$(uname -s)-$(uname -m) -o /usr/local/bin/docker-compose
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
100   617    0   617    0     0    401      0 --:--:--  0:00:01 --:--:--   401
100 10.3M  100 10.3M    0     0   701k      0  0:00:15  0:00:15 --:--:-- 1479k
//改变权限
docker@ubuntu:~/Desktop/spark/curl-7.55.1$ chmod +x /usr/local/bin/docker-compose
chmod: changing permissions of '/usr/local/bin/docker-compose': Operation not permitted
docker@ubuntu:~/Desktop/spark/curl-7.55.1$ sudo chmod +x /usr/local/bin/docker-compose
```
这就已经安装完毕。
### Docker-compose文件详解
```

version: "3"
services:
 
  redis:
#连接地址
    image: redis:alpine
#用来指定端口
    ports:   
      - "6379"
    networks:
      - frontend
    deploy:
      replicas: 2
      update_config:
        parallelism: 2
        delay: 10s
      restart_policy:
        condition: on-failure
 
  db:
    image: postgres:9.4
    volumes:
      - db-data:/var/lib/postgresql/data
    networks:
      - backend
    deploy:
      placement:
        constraints: [node.role == manager]
 
  vote:
    image: dockersamples/examplevotingapp_vote:before
    ports:
      - 5000:80
    networks:
      - frontend
    depends_on:
      - redis
    deploy:
      replicas: 2
      update_config:
        parallelism: 2
      restart_policy:
        condition: on-failure
 
  result:
    image: dockersamples/examplevotingapp_result:before
    ports:
      - 5001:80
    networks:
      - backend
    depends_on:
      - db
    deploy:
      replicas: 1
      update_config:
        parallelism: 2
        delay: 10s
      restart_policy:
        condition: on-failure
 
  worker:
    image: dockersamples/examplevotingapp_worker
    networks:
      - frontend
      - backend
    deploy:
      mode: replicated
      replicas: 1
      labels: [APP=VOTING]
      restart_policy:
        condition: on-failure
        delay: 10s
        max_attempts: 3
        window: 120s
      placement:
        constraints: [node.role == manager]
 
  visualizer:
    image: dockersamples/visualizer:stable
    ports:
      - "8080:8080"
    stop_grace_period: 1m30s
    volumes:
      - "/var/run/docker.sock:/var/run/docker.sock"
    deploy:
      placement:
        constraints: [node.role == manager]
 
networks:
  frontend:
  backend:
 
volumes:
  db-data:
```
配置文件中应该有version、services、networks三大部分。下面简单的介绍下相关的标签内容参数。
## image
用来指定服务的镜像名称或者镜像id，本地不存在该镜像的话，会主动的拉去此镜像。
```
image : redis
image : mongo
```
## container_name
Compose的容器名称是项目名称 服务名称 序号
但是我们也可以自己自定义名字
```
container_name : app_driver
```
## build
用来指定Dockerfile文件的路径，当然可以是绝对路径也可以是相对路径。或者我们使用context参数来帮我们构建 后面可以跟绝对路径，相对路径也可以是链接到git的url。
```
build: /home/ruiqi/content
build: ./content
build:
   context : ../content
   dockerfile : /ruiqi/Dockerfile
```
## args 
用来指定环境变量。与我们前面在Dockerfile里面所说的内容类似。不过在这里是放到build下面的进行内容构建使用。
```
build:
  args :
   -  username : ruiqi
   -   password :1111
```
## depends_on 
使用的依赖关系，我们在启动程序的时候是需要启动顺序的所以，该参数就可以帮助我们很好的进行上下文程序的启动、
```
 app:
    depends_on :   先启动数据库和 redis
      - db 
      - redis
redis :
    image :redis
db :
    image : mysql
```
## pid 
讲PID模式设置成主机PID模式 。进行交互与主机系统并且共享进程命名空间。
```
pid: "content"
```
## networks
用来设置网络通信进程，并且可以指定网络设置好别名。

## target
根据对应的Dockerfile进行构建指定的Stage
```
build :
   target: prod
```

## deploy
指定与部署和运行服务相关的配置
```
version: '3'
services:
  redis:
    image: redis:alpine
    deploy:
      replicas: 6   //指定容器的数量
      update_config:  
        parallelism: 2
        delay: 10s
      restart_policy:  //配置容器的重新启动
        condition: on-failure  状态
       delay: 5s   尝试重启启动次数
        max_attempts: 3  重新尝试的启动次数
        window: 120s   持续时间
       resources:  //配置资源
        limits:
          cpus: '0.50'
          memory: 50M
        reservations:
          cpus: '0.25'
          memory: 20M

      update_config:
        parallelism: 2
        delay: 10s
        order: stop-first

     配置更新服务，用于无缝更新应用（)
     parallelism：一次性更新的容器数量
    delay：更新一组容器之间的等待时间。
    failure_action：如果更新失败，可以执行的的是 continue、rollback 或 pause （默认）
    monitor：每次任务更新后监视失败的时间(ns|us|ms|s|m|h)（默认为0）
    max_failure_ratio：在更新期间能接受的失败率
     order：更新次序设置，top-first（旧的任务在开始新任务之前停止）、start-first（新的任务首先启动，并且正在运行的任务短暂重叠）（默认 stop-first）
```

还有很多的命令需要学习，我们可以去官网进行学习参考。[官方文档](https://docs.docker.com/compose/compose-file/)
