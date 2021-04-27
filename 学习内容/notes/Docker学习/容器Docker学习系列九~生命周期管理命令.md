我们前面已经学习了本地容器操作以及版本信息管理，复制提交等容器命令。但是对于怎么启动执行或者操作容器还不是很清楚，今天就来学习下整个容器周期的操作从启动，到停止一整圈的命令掌握。当然还是小步快走，一个一个的尝试学习。
# run
run命令是用来创建一个容器并且启动他。
>语法糖 docker run [OPTIONS] IMAGE[tag]

OPTIONS[可选的]：在这里列举几个常用的参数，其他参数可以去官网查询
- -i: 以交互模式运行容器，通常与 -t 同时使用；
- -t: 为容器重新分配一个伪输入终端，通常与 -i 同时使用；
- --name="test-redis": 为容器指定一个名称；
- -d: 后台运行容器，并返回容器ID；
- -env-file=[]: 从指定文件读入环境变量；
- -P : 系统自动的随意指定端口。
- -p： 指定端口映射
```
docker run   -it  --name test-redis -d redis 

docker@ubuntu:~$ docker run -it --name test-redis -d redis 
573f7d98f746465d7498b384f19b5d5a0506f55125e433c30ea3a02060d907c2
docker@ubuntu:~$ docker ps 
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS               NAMES
573f7d98f746        redis               "docker-entrypoint.s…"   6 seconds ago       Up 5 seconds        6379/tcp            test-redis
docker@ubuntu:~$ 
```
# start/stop/restart
这三个命令式掌握启动，停止，重启的命令。
> 语法糖就是docker start/stop/restart  + docker容器的名字。
列子：
```
docker start  test-redis
docker stop test-redis
docker restart test-redis

docker@ubuntu:~$ docker stop test-redis 
test-redis
docker@ubuntu:~$ docker ps 
CONTAINER ID        IMAGE               COMMAND             CREATED             STATUS              PORTS               NAMES
docker@ubuntu:~$ docker start rest-redis 
Error response from daemon: No such container: rest-redis
Error: failed to start containers: rest-redis
docker@ubuntu:~$ docker start test-redis 
test-redis
docker@ubuntu:~$ docker ps  
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS               NAMES
573f7d98f746        redis               "docker-entrypoint.s…"   2 minutes ago       Up 11 seconds       6379/tcp            test-redis
docker@ubuntu:~$ docker stop test-redis 
test-redis
docker@ubuntu:~$ docker ps  
CONTAINER ID        IMAGE               COMMAND             CREATED             STATUS              PORTS               NAMES
docker@ubuntu:~$ docker restart test-redis 
test-redis
docker@ubuntu:~$ docker ps 
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS               NAMES
573f7d98f746        redis               "docker-entrypoint.s…"   2 minutes ago       Up 2 seconds        6379/tcp            test-redis


```

# kill
我们在Linux系统中都知道，会有kill这个命令，同样的在我们容器命令操作里面也有kill相关的命令。
>语法糖 docker kill [OPTONS] CONTAINER [容器]

OPTIONS:可选参数项。
- --s ：向容器发送一个信号，杀死容器的信号
列子：
```
docker kill -s KILL test-redis
docker@ubuntu:~$ docker kill --help

Usage:	docker kill [OPTIONS] CONTAINER [CONTAINER...]

Kill one or more running containers

Options:
  -s, --signal string   Signal to send to the container (default "KILL")
docker@ubuntu:~$ docker kill -s KILL  test-redis 
test-redis
docker@ubuntu:~$ docker ps 
CONTAINER ID        IMAGE               COMMAND             CREATED             STATUS              PORTS               NAMES

```
# rm
删除一个容器，当我们容器修改命令造成容器不可恢复性错误的时候，一般会把现在的容器删除，当然启动容器的时候要把数据外挂。这样再次启动的时候还会获取到数据。删除的是已经创建的容器，不是我们下载下来的容器镜像
>语法糖 docker rm [OPTIONS] CONTAINER [容器名字]

OPTIONS说明：
- -f :通过SIGKILL信号强制删除一个运行中的容器
- -l :移除容器间的网络连接，而非容器本身
- -v :-v 删除与容器关联的卷

```
docker rm -f test-redis

docker@ubuntu:~$ docker run -it --name test-redis -d redis 
docker: Error response from daemon: Conflict. The container name "/test-redis" is already in use by container "573f7d98f746465d7498b384f19b5d5a0506f55125e433c30ea3a02060d907c2". You have to remove (or rename) that container to be able to reuse that name.
See 'docker run --help'.
docker@ubuntu:~$ docker rm -f test-redis 
test-redis
docker@ubuntu:~$ docker run -it --name test-redis -d redis 
04d57e7380bf130ecaeca455340a9f18e3c40679c299a5f9b12b8842709e4305
docker@ubuntu:~$ docke ps 

Command 'docke' not found, did you mean:

  command 'docky' from deb docky
  command 'docker' from deb docker.io

Try: sudo apt install <deb name>

docker@ubuntu:~$ docker run -it --name test-redis -d redis 
docker: Error response from daemon: Conflict. The container name "/test-redis" is already in use by container "04d57e7380bf130ecaeca455340a9f18e3c40679c299a5f9b12b8842709e4305". You have to remove (or rename) that container to be able to reuse that name.
See 'docker run --help'.

```
# pause/unpause命令
>语法糖 docker pause [OPTIONS] 容器名字 

```
docker pause test-redis
docker unpause test-redis
```

# create
创建一个新的容器但是不启动容器。
>语法糖 docker create [options] image [tag]

具体操作语法与run操作一样，可以参考
列子：
```
docker create --name test-redis1 redis（存在的镜像文件） 

docker@ubuntu:~$ docker create --name test-redis1 redis
f93712639869006ad3fe1440edb1a5c9570b70e36092d39f2ba94e275bab9db8
docker@ubuntu:~$ docker ps 
CONTAINER ID        IMAGE               COMMAND                  CREATED              STATUS              PORTS               NAMES
04d57e7380bf        redis               "docker-entrypoint.s…"   About a minute ago   Up About a minute   6379/tcp            test-redis

```
