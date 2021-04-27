这一篇算是我们Docker容器命令的最后一篇文章了，基础学习好了我们再去学习Docker中更高级的特性，用好容器，让我们更加方便使用在开发上提高软件的性能。
# ps
这个命令是我们经常使用来展示容器信息。
>docker ps [OPTIONS]

OPTIONS 可选参数：
- -a :显示所有的容器，包括未运行的。
- -f :根据条件过滤显示的内容。
-  --format :指定返回值的模板文件。
- -l :显示最近创建的容器。
- -n :列出最近创建的n个容器。
- --no-trunc :不截断输出。
- -q :静默模式，只显示容器编号。
- -s :显示总的文件大小。
我经常用的是 -a 或者不指定参数，也可以使用最近创建的几个容器 -n。
列子如下：
```
docker@ubuntu:~$ docker ps 
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS               NAMES
fc8e73a8b48b        mongo               "docker-entrypoint.s…"   5 seconds ago       Up 3 seconds        27017/tcp           test-mongo
04d57e7380bf        redis               "docker-entrypoint.s…"   22 hours ago        Up 2 hours          6379/tcp            test-redis

docker@ubuntu:~$ docker ps -a   包含创建好 没有启用的
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS               NAMES
fc8e73a8b48b        mongo               "docker-entrypoint.s…"   21 seconds ago      Up 19 seconds       27017/tcp           test-mongo
f93712639869        redis               "docker-entrypoint.s…"   22 hours ago        Created                                 test-redis1
04d57e7380bf        redis               "docker-entrypoint.s…"   22 hours ago        Up 2 hours          6379/tcp            test-redis

docker@ubuntu:~$ docker ps -s
CONTAINER ID        IMAGE               COMMAND                  CREATED              STATUS              PORTS               NAMES               SIZE
fc8e73a8b48b        mongo               "docker-entrypoint.s…"   About a minute ago   Up About a minute   27017/tcp           test-mongo          0B (virtual 380MB)
04d57e7380bf        redis               "docker-entrypoint.s…"   22 hours ago         Up 2 hours          6379/tcp            test-redis          0B (virtual 83.4MB)

```
# inspect 
用来获取容器或者镜像的元数据
> docker inspect [OPTIONS] NAME 

OPTIONS 说明：
- -f : 指定返回值的模板文件
- -s： 显示总的文件大小
- --type: 为指定类型返回JSON数据
列子: 内容太多，粘贴一部分，大家自己尝试的时候可以多看看。获取容器信息 有很多的。也可以利用-f指定返回内容 
```
docker@ubuntu:~$ docker inspect test-redis 
[
    {
        "Id": "04d57e7380bf130ecaeca455340a9f18e3c40679c299a5f9b12b8842709e4305",
        "Created": "2018-08-31T15:44:24.634464326Z",
        "Path": "docker-entrypoint.sh",
        "Args": [
            "redis-server"
        ],
        "State": {
            "Status": "running",
            "Running": true,
            "Paused": false,
            "Restarting": false,
            "OOMKilled": false,
            "Dead": false,
            "Pid": 2828,
            "ExitCode": 0,
            "Error": "",
            "StartedAt": "2018-09-01T11:15:06.016924571Z",
            "FinishedAt": "2018-09-01T04:11:15.077538199-07:00"
        },
        "Image": "sha256:4e8db158f18dc71307f95260e532df39a9b604b51d4e697468e82845c50cfe28",
        "ResolvConfPath": "/var/lib/docker/containers/04d57e7380bf130ecaeca455340a9f18e3c40679c299a5f9b12b8842709e4305/resolv.conf",


docker@ubuntu:~$ docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' test-redis 
172.17.0.2
```
# top
查看容器中运行的进程信息，跟linux中的top类似， 并且也支持ps命令参数。
> docker top [OPTIONS] 容器 [ps OPTIONS]

例子：
```
docker@ubuntu:~$ docker top test-redis 
UID                 PID                 PPID                C                   STIME               TTY                 TIME                CMD
999                 2828                2806                0                   04:15               pts/0               00:00:14            redis-server *:6379

```
# attach 
用来链接到正在运行中的容器
> docker attach [OPTIONS] name(容器名字)

OPTIONS:
- --sig-proxy=false 
例子：
```
docker@ubuntu:~$ docker attach test-redis 
^C1:signal-handler (1535809783) Received SIGINT scheduling shutdown...
1:M 01 Sep 13:49:44.031 # User requested shutdown...
1:M 01 Sep 13:49:44.033 * Saving the final RDB snapshot before exiting.
1:M 01 Sep 13:49:44.041 * DB saved on disk
1:M 01 Sep 13:49:44.042 # Redis is now ready to exit, bye bye...
docker@ubuntu:~$ docker ps 
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS               NAMES
fc8e73a8b48b        mongo               "docker-entrypoint.s…"   11 minutes ago      Up 11 minutes       27017/tcp           test-mongo
docker@ubuntu:~$ docker attach --sig-proxy=false test-redis 
You cannot attach to a stopped container, start it first
docker@ubuntu:~$ docker start test-redis 
test-redis
docker@ubuntu:~$ docker attach --sig-proxy=false test-redis 
```
# events 
从容器中获取实时事件
> docker events [OPTIONS]
OPTIONS:
- -f:根据给定的条件进行过滤。
- --since:根据指定的时间戳显示事件
例子：
```
docker@ubuntu:~$ docker events --since="1525810182"
2018-09-01T04:15:04.930155641-07:00 network connect 702b1e16448dbb8fc6e0baf8d6a66d3c6c4c214ce2cf0e4281ab5d8826872099 (container=04d57e7380bf130ecaeca455340a9f18e3c40679c299a5f9b12b8842709e4305, name=bridge, type=bridge)
2018-09-01T04:15:05.020352568-07:00 volume mount 05911ac2f739e8d85206f0170cf26f066372a4e49b7a964202ff56dbe7325c6d (container=04d57e7380bf130ecaeca455340a9f18e3c40679c299a5f9b12b8842709e4305, destination=/data, driver=local, propagation=, read/write=true)
2018-09-01T04:15:06.088232843-07:00 container start 04d57e7380bf130ecaeca455340a9f18e3c40679c299a5f9b12b8842709e4305 (image=redis, name=test-redis)
2018-09-01T06:38:19.267367732-07:00 container create fc8e73a8b48be47f6fc59607506d4e78f0f8fe6a09a6f8c2b030c6cf5fc23264 (image=mongo, name=test-mongo)
2018-09-01T06:38:19.345489171-07:00 network connect 702b1e16448dbb8fc6e0baf8d6a66d3c6c4c214ce2cf0e4281ab5d8826872099 (container=fc8e73a8b48be47f6fc59607506d4e78f0f8fe6a09a6f8c2b030c6cf5fc23264, name=bridge, type=bridge)
2018-09-01T06:38:19.424505893-07:00 volume mount 3cbb0b38feb2d121bf065076e7354d59804ce7df091158addf3edf837d406a44 (container=fc8e73a8b48be47f6fc59607506d4e78f0f8fe6a09a6f8c2b030c6cf5fc23264, destination=/data/configdb, driver=local, propagation=, read/write=true)
2018-09-01T06:38:19.424512355-07:00 volume mount 4509ec37637d27647f8a98a1678318200bf772ae294bbc341b765fa606b20633 (container=fc8e73a8b48be47f6fc59607506d4e78f0f8fe6a09a6f8c2b030c6cf5fc23264, destination=/data/db, driver=local, propagation=, read/write=true)
```
# logs
查看容器的日志
>docker logs [OPTIONS] name(容器名字)

OPTIONS:
- -f:跟踪日志输出
- --since:显示某个开始时间的日志
- -t ：显示时间戳
- --tail: 列出需要的最新N条容器日志

例子：使用的时候最好带上参数这样，能显示出需要的日志，筛选掉不必要的日志内容
```
docker@ubuntu:~$ docker logs test-redis 
1:C 31 Aug 15:44:25.125 # oO0OoO0OoO0Oo Redis is starting oO0OoO0OoO0Oo
1:C 31 Aug 15:44:25.125 # Redis version=4.0.11, bits=64, commit=00000000, modified=0, pid=1, just started
1:C 31 Aug 15:44:25.125 # Warning: no config file specified, using the default config. In order to specify a config file use redis-server /path/to/redis.conf
                _._                                                  
           _.-``__ ''-._                                             
      _.-``    `.  `_.  ''-._           Redis 4.0.11 (00000000/0) 64 bit
  .-`` .-```.  ```\/    _.,_ ''-._                                   
 (    '      ,       .-`  | `,    )     Running in standalone mode
 |`-._`-...-` __...-.``-._|'` _.-'|     Port: 6379
 |    `-._   `._    /     _.-'    |     PID: 1
  `-._    `-._  `-./  _.-'    _.-'                                   
 |`-._`-._    `-.__.-'    _.-'_.-'|                                  
 |    `-._`-._        _.-'_.-'    |           http://redis.io        
  `-._    `-._`-.__.-'_.-'    _.-'                                   
 |`-._`-._    `-.__.-'    _.-'_.-'|                                  
 |    `-._`-._        _.-'_.-'    |                             
```
#export
将文件系统作为一个tar归档文件导出到STDOUT.我们使用导出的时候，相应的也会用到导入
> docker export [OPTIONS] 容器id

OPTIONS:
- -o 将输入内容写到文件

例子：
```
docker@ubuntu:~$ docker export -o test-redis.tar test-redis 
docker@ubuntu:~$ ls 
Desktop    Downloads         mongo.tar  Pictures  Templates       Videos
Documents  examples.desktop  Music      Public    test-redis.tar
```
# port 
列出指定的容器的端口映射信息，或者查找面向公众的端口
> docke port [OPTIONS] 容器名字或者id也可

例子：
```
docker@ubuntu:~$ docker port test-redis
6379/tcp -> 0.0.0.0:6379
```

#wait
阻塞运行直到容器停止，然后打印出退出的代码。
>docker wait 名字 或者容器id

例子：
```
docker wait test-redis
```
