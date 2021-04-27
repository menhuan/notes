最近自己在学习Docker方面的知识，一是因为公司这边也在用Docker，自己不会，每次都去请教别人总显的很麻烦，自己学会了总算一门技术。并且学习微服务我们总是需要用到容器技术，在这个快速发展的路上，我们还是有必要去学习尝试的。
现在关于基础命令的学习已经算全部完成，但是总体还是说只是了解，还不能达到熟练的目的，并且容器知识还有很多需要去融会贯通的。那么我们就开始实战练习，去掌握这些技术。实战操作也会形成一个系列来写，也是自己的操作过程。希望我们在实战操作后都能顺利的掌握住这门技术。
# 使用commit命令创建一个新的容器
我们是建立一个ubuntu的容器。
1. 首先搜索下ubuntu的
```
docker@ubuntu:~$ docker search ubuntu 
NAME                                                      DESCRIPTION                                     STARS               OFFICIAL            AUTOMATED
ubuntu                                                    Ubuntu is a Debian-based Linux operating sys…   8309                [OK]                
dorowu/ubuntu-desktop-lxde-vnc                            Ubuntu with openssh-server and NoVNC            213                                     [OK]
rastasheep/ubuntu-sshd                                    Dockerized SSH service, built on top of offi…   170                                     [OK]
```
在这里我们也可以使用docker pull ubuntu 拉取新的版本ubuntu容器。在执行我们的run，但是run在操作的时候会首先检查下是否存在本地镜像，不存在就直接去仓库拉取新的镜像，我们就直接操作镜像即可，并且操作上加上/bin/bash 在运行后直接进入容器里面。
```
docker@ubuntu:~$ docker run -it --name myubuntu ubuntu /bin/bash 
Unable to find image 'ubuntu:latest' locally
latest: Pulling from library/ubuntu
124c757242f8: Pull complete 
2ebc019eb4e2: Pull complete 
dac0825f7ffb: Pull complete 
82b0bb65d1bf: Pull complete 
ef3b655c7f88: Pull complete 
Digest: sha256:72f832c6184b55569be1cd9043e4a80055d55873417ea792d989441f207dd2c7
Status: Downloaded newer image for ubuntu:latest
root@61c5b26e974e:/# apt-get update 
Get:1 http://archive.ubuntu.com/ubuntu bionic InRelease [242 kB]
Get:2 http://security.ubuntu.com/ubuntu bionic-security InRelease [83.2 kB]
Get:3 http://security.ubuntu.com/ubuntu bionic-security/universe Sources [17.4 kB]
```
因为我们只是用来创建自己的ubuntu容器 可能需要操作其他的内容，所以我在本地容器中增加了vim操作命令,然后退出容器进行提交
```
root@61c5b26e974e:/# apt-get install vim 
root@61c5b26e974e:/# exit
```
执行提交命令将容器保存为一个新的ubuntu镜像。
```
docker@ubuntu:~$ docker commit myubuntu myubuntu:latest   
sha256:494bbb7b88af3e761a976facefa3a276cb3908cc4dac98abcdb3f91d0cb19e63
```
# 使用DockerFile创建需要的容器
1. 创建需要的文件与脚本文件 然后通过DockerFile文件映射到容器中
```
docker@ubuntu:~/Desktop/sshd_ubuntu$ touch Dockerfile run.sh 
docker@ubuntu:~/Desktop/sshd_ubuntu$ ll
total 8
drwxr-xr-x 2 docker docker 4096 Sep  2 05:43 ./
drwxr-xr-x 4 docker docker 4096 Sep  2 05:42 ../
-rw-r--r-- 1 docker docker    0 Sep  2 05:43 Dockerfile
-rw-r--r-- 1 docker docker    0 Sep  2 05:43 run.sh
docker@ubuntu:~/Desktop/sshd_ubuntu$ vim run.sh 
```
2. 写DockerFile文件内容
```
# 设置继承镜像
FROM ubuntu:14.04
# 提供一些作者的信息
MAINTAINER from frq
# 下面是开始运行的命令 
RUN  echo "deb http://mirrors.aliyun.com/ubuntu/ trusty main restricted universe multiverse" > /etc/apt/sources.list
RUN  echo "deb http://mirrors.aliyun.com/ubuntu/ trusty-security main restricted universe multiverse" > /etc/apt/sources.list
RUN  echo "deb http://mirrors.aliyun.com/ubuntu/ trusty-updates main restricted universe multiverse" > /etc/apt/sources.list
RUN  echo "deb http://mirrors.aliyun.com/ubuntu/ trusty-proposed main restricted universe multiverse" > /etc/apt/sources.list
RUN  echo "deb http://mirrors.aliyun.com/ubuntu/ trusty-backports main restricted universe multiverse" > /etc/apt/sources.list
RUN  echo "deb-src http://mirrors.aliyun.com/ubuntu/ trusty main restricted universe multiverse" > /etc/apt/sources.list
RUN  echo "deb-src http://mirrors.aliyun.com/ubuntu/ trusty-security main restricted universe multiverse" > /etc/apt/sources.list
RUN  echo "deb-src http://mirrors.aliyun.com/ubuntu/ trusty-updates main restricted universe multiverse" > /etc/apt/sources.list
RUN  echo "deb-src http://mirrors.aliyun.com/ubuntu/ trusty-proposed main restricted universe multiverse" > /etc/apt/sources.list
RUN  echo "deb-src http://mirrors.aliyun.com/ubuntu/ trusty-backports main restricted universe multiverse"> /etc/apt/sources.list
RUN  apt-get update
#安装ssh服务
RUN  apt-get update
RUN  apt-get install -y openssh-server
RUN  mkdir -p /var/run/sshd
RUN  mkdir -p /root/.ssh
#取消pam限制
RUN sed -ri 's/session required pam_loginuid.so/#session required pam_loginuid.so/g' /etc/pam.d/sshd
#复制配置文件到响应的位置，并执行脚本可执行权限
ADD authorized_keys /root/.ssh/authorized_keys
ADD run.sh /run.sh 
RUN chmod 755 /run.sh
#开放端口
EXPOSE 22
#设置自启动命令
CMD ["/run.sh"]
```
最后一步 我们将内容打包成docker文件,最后使用命令查看下即可
```
docker@ubuntu:~/Desktop/sshd_ubuntu$ docker build -t sshd:dockerfile .
docker@ubuntu:~/Desktop/sshd_ubuntu$ docker images 
''REPOSITORY          TAG                 IMAGE ID            CREATED             SIZE
sshd                dockerfile          5abaa0c085ab        37 seconds ago      251MB
```




