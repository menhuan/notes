在原先的文章中我们进行了简单的容器的拉取，获取现在的运行的docker列表docker ps，查看下载的docker信息docker images。等等信息 。具体我们可以参考文章[docker的使用学习一](https://www.jianshu.com/p/4619963629a6)接下来我们具体运行到ubuntu环境下进行查看。使用我们的docker
# ubuntu环境
我们的docker是部署在ubuntu18.04版本上的具体系统安装教程就不说了。
系统安装好之后会先进行sudo apt-get update进行包内信息 更新，如果出现锁的信息
![锁信息](https://upload-images.jianshu.io/upload_images/4237685-3cd52e25715b5995.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
执行如下命令将锁信息解除
```
sudo rm /var/lib/apt/lists/lock
sudo rm /var/cache/apt/archives/lock
```
然后在执行我们的 sudo apt-get update即可。
# 安装docker
- 因为docker默认使用的源是官方的hub源，暂时不修改下载源.
```
1. 使用命令sudo apt-get install docker.io 进行安装
2. 安装好之后使用sudo docker -v 查看版本号，形式如下。
```
![安装好之后的图](https://upload-images.jianshu.io/upload_images/4237685-590192a0d47e31c5.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
- 但是这个安装好之后我们可以发现 使用sudo 可以使用 docker命令 ，但是我们使用普通用户的时候就没法使用了，那应该怎么办呢？我们可以把权限发送给我们的普通用户组，操作步骤如下
 1. 首先查看docker用户组是否存在
```
sudo cat /etc/group | grep docker
```
![image.png](https://upload-images.jianshu.io/upload_images/4237685-546498e90cd42a8f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

 2.如果不存在那么就需要重新建立docker组，存在就不要考虑该步骤 。
```
sudo groupadd -g 999 docker    // 其中 -g 999 是设置组id当然 我们也可以不进行设置。
```
3. 将普通用户啊加入到该组当中
```
sudo usermod -aG docker docker  //后面这个docker是我的用户名  在这里改成你的用户名即可 
```
4. 查看效果
使用命令查看效果，并且进行重启docker ，然后直接使用docker命令查看是否修改成功。docker info 或者docker images等 指令都可以。
```
cat /etc/group | grep docker  //这个是过滤的docker用户组 内容跟上面的图 比 已经加上的我自己的用户 权限
```
![image.png](https://upload-images.jianshu.io/upload_images/4237685-4866435874661904.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![image.png](https://upload-images.jianshu.io/upload_images/4237685-a65461bcab749c0a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
5. 最终普通用户也有操作权限去执行docker。
# 命令操作
总体来说Docker的命令总体不是很多。总体可以分为六类。那么我们进行6类操作。
1.本地镜像操作
- images
>docker images 列出本地所下载的镜像。这是我们常用的操作命令。
语法：
        docker images [OPTIONS] [REPOSITORY[:TAG]]

其中我们看到的OPTIONS是可选的。个人一般没有具体使用过参数，不过如果镜像太多的情况，建议还是使用参数为好。
- a :列出本地所有的镜像（含中间映像层，默认情况下，过滤掉中间映像层）；

- digests :显示镜像的摘要信息；

- f :显示满足条件的镜像；

- format :指定返回值的模板文件；

- no-trunc :显示完整的镜像信息；

- q :只显示镜像ID。
![images.png](https://upload-images.jianshu.io/upload_images/4237685-63a8bf7939a69751.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
这是我们已经从网络上下载好的docker镜像。TAG表示我们所用的版本，其中最新版本使用latest来表示。
如果我们需要查看具体docker的镜像 可以使用命令 例如：**docker images  redis** 这是查看redis的本地镜像
![redis.png](https://upload-images.jianshu.io/upload_images/4237685-a2c07ab511b30984.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


