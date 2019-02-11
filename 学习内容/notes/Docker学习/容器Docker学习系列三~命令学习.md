
今天继续学习我们的Docker命令，在简单的了解命令后我们会开始实战操作执行Docker容器，应用起来我们的命令。今天继续学习六大部门Docker命令中的**本地镜像管理命令**
原先我们学习images的命令可以参考前一篇文章[容器Docker学习系列二](https://www.jianshu.com/p/ed3b3e740d28)了解
今天要学习的命令包含剩下的五个 **rmi**,**tag**,**build**,**history**,**save**,**import**
## rmi 
> 执行操作 docker rmi [OPTIONS]  镜像名称
删除本地一个或者多个镜像。


OPTIONS 是一个可选操作：
   *  -f :强制删除
   * --no-prune:不移除该镜像的过程镜像，默认操作是移除。
 ```
docker@ubuntu:~$ docker images 
REPOSITORY          TAG                 IMAGE ID            CREATED             SIZE
mongo               latest              8bf72137439e        4 days ago          380MB
redis               latest              4e8db158f18d        11 days ago         83.4MB
docker@ubuntu:~$ docker rmi redis 
Untagged: redis:latest
Untagged: redis@sha256:858b1677143e9f8455821881115e276f6177221de1c663d0abef9b2fda02d065
Deleted: sha256:4e8db158f18dc71307f95260e532df39a9b604b51d4e697468e82845c50cfe28
Deleted: sha256:f0a7bdb1c3ed0d654f4c089184d736248a36fe904656c4a6907d2c1af3e28886
Deleted: sha256:96aa0bbe90a1e1cc0400b9ae97ceae726b4c8a4b4e86cbaa38577437b1747317
Deleted: sha256:098bb5a74892a87af81f5eb190c2768aaa2a625300b111270c53951488995658
Deleted: sha256:e6b3eda8746c5cc312ebb40e1ca5c064638af429b9b3848280aab8ed882bd10b
Deleted: sha256:aee8b479b9a768a64f4c32d69108566fbdbb71c8e541496dd1fa9f7ad19d8632
Deleted: sha256:cdb3f9544e4c61d45da1ea44f7d92386639a052c620d1550376f22f5b46981af
docker@ubuntu:~$ docker images 
REPOSITORY          TAG                 IMAGE ID            CREATED             SIZE
mongo               latest              8bf72137439e        4 days ago          380MB
``` 

## tag
>执行操作 docker tag [OPTION]  sourceImage[:TAG]  targetImage[:TAG]
用来标记本地的镜像，将其归入其中的仓库中 .将源目标的tag创建一个新的TAG或者是某一类的标签标示
OPTION 可选：
 * -f ：强制覆盖
例子如下展示了修改标签。 标签一般代表的是版本
```
docker@ubuntu:~$ docker images 
REPOSITORY          TAG                 IMAGE ID            CREATED             SIZE
mongo               latest              8bf72137439e        4 days ago          380MB
docker@ubuntu:~$ docker tag mongo:latest mongo:13.2
docker@ubuntu:~$ docker images 
REPOSITORY          TAG                 IMAGE ID            CREATED             SIZE
mongo               13.2                8bf72137439e        4 days ago          380MB
```
## build
> docker build [OPTIONS] PATH | URL | -
docker build 用于使用DockerFile来创建使用自己的镜像。
 
   因为OPTIONS 是可选的 但是我们在构建的时候常用的可选参数应该也明白下。

   - --build-arg :用来设置构建时的变量
   - --no-cache : 默认false,如果设置该选项将不会使用Build Cache 构建镜像
    - --compress，默认false。设置该选项，将使用gzip压缩构建的上下文
 - --disable-content-trust，默认true。设置该选项，将对镜像进行验证
 - --file, -f，Dockerfile的完整路径，默认值为‘PATH/Dockerfile’
 - --isolation，默认--isolation="default"，即Linux命名空间；其他还有process或hyperv
 - --label，为生成的镜像设置metadata
 - --squash，默认false。设置该选项，将新构建出的多个层压缩为一个新层，但是将无法在多个镜像之间共享新层；设置该选项，实际上是创建了新image，同时保留原有image。
 - --tag, -t，镜像的名字及tag，通常name:tag或者name格式；可以在一次构建中为一个镜像设置多个tag
 - --network，默认default。设置该选项，Set the networking mode for the RUN instructions during build
 - --quiet, -q ，默认false。设置该选项，Suppress the build output and print image ID on success
 - --force-rm，默认false。设置该选项，总是删除掉中间环节的容器
 - --rm，默认--rm=true，即整个构建过程成功后删除中间环节的容器
那么我们应该怎么使用呢？
1. 如果我们当前目录有Dockerfile文件那么使用或者不是当前目录
```
docker build -t mongo:12.1
docker build -f  dockerfile 文件路径
```
2. 当然我们除了本地的文件也可以是远程仓库的路径，如远程URL，Git的仓库等等。
例子
```
docker build github.com/creack/docker-firefox
执行过程：
Sending build context to Docker daemon  72.19kB
Step 1/7 : from	ubuntu:12.04
12.04: Pulling from library/ubuntu
d8868e50ac4c: Pull complete 
83251ac64627: Pull complete 
589bba2f1b36: Pull complete 
d62ecaceda39: Waiting 
6d93b41cfc6b: Download complete 
```
今天我们就先了解这三个命令，尝试下使用。多多练习，我们才能熟悉这些命令掌握使用。我们下来系列文章还是会继续接着写下去，大家一起共同学习
