#### docker 安装 centos 
1. sudo yum update 
2. sudo yum install docker
####  docker 安装 Ubuntu
1. sudo apt-get update
2. sudo apt-get install docker.io
#### 检验其版本号
docker -v

#### 启动docker
service docker start 
![启动的图片](http://upload-images.jianshu.io/upload_images/4237685-6c9ca3a986dbe92e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
#### 查询某个镜像
1. docker search 镜像名
    比如docker search redis 

![docker查询命令](http://upload-images.jianshu.io/upload_images/4237685-42733bbae6c4368f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
2. docker search Mongodb

![Mongodb镜像](http://upload-images.jianshu.io/upload_images/4237685-2a4f3511b45d36d9.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
#### 镜像下载
1. docker pull redis  下载镜像 。

![下载镜像成功](http://upload-images.jianshu.io/upload_images/4237685-026a8917afbd215c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
这个下载特别看网速，所以如果是网速不好的话 最好准备好提前下载不然用的时候很麻烦。我的云服务器就是1m带宽，下载很慢就换到本地虚拟机上学习用。 云服务器上的就继续下载其他的。、
#### 查看镜像列表
docker images 

![查看镜像列表](http://upload-images.jianshu.io/upload_images/4237685-9db67b758cc27a26.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
#### 镜像删除
docker rmi <image-id>   删除指定镜像
![删除成功](http://upload-images.jianshu.io/upload_images/4237685-76941f774be646e0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
docker rmi $(docker images -q)
#### 容器的基本操作
docker run --name container-name -d image-name

![运行一个redis](http://upload-images.jianshu.io/upload_images/4237685-65a9eb8521425ba3.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
docker run --name test-redis(ps就是起了一个名字) -d  redis(下载的容器名字)
其中CONTAINER ID 是在启动的时候生成的，IMAGE 是该容器使用的镜像，COMMAND 是容器启动时调用的命令，CREATED 是容器创建时间；STATUS是当前容器状态 PORTS 容器系统所使用的端口号。， NAMES是刚才给容器定义的名称

#### 容器列表
1. docker ps  或者docker ps -a 查看运行的和停止状态的容器
2. 停止容器  docker stop container-name/container-id 
 停止我们刚才启动的redis  docker stop test-redis
3. 启动容器  docker start container-name/container-id
    启动我们刚才停止的容器 docker start test-redis
4. 如果我们用的虚拟机需要做一个端口映射到本机
 docker run -d -p 6378:6379 --name port-redis redis
#### 删除容器
1. docker rm -f container-id  删除指定容器
2. docker rm $(docker ps -a -q)  删除所有容器
#### 查看容器日志
docker logs container-name/container-id
docker logs test-redis

![查看日志](http://upload-images.jianshu.io/upload_images/4237685-e6c7fb566d783fea.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
#### 删除docker 
1.  yum list installed |grep docker  查看 安装的docker内容

2.  yum -y remove docker-*  删除安装的docker 内容


