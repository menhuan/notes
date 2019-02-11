新公司现在开发环境都是在Liunx 下进行开发,系统是Ubuntu 18.4的版本.但是我们经常用Liunx系统的人就知道          我们经常用原生的源下载软件。速度不是很快有时候网络不好的情况下会导致下载几M的软件也需要花费很长的时间，这就给我们带来体验的痛苦，所以我们一般在安装好系统后会先进性Linux系统源的替换。
# Liunx系统源的替换
## Ubuntu 系统源的修改
1. 首先进行原先源的备份，防止修改错误丢失原先的信息。
```
cp /etc/apt/sources.list /etc/apt/sources.list.bak
```
2. 选择合适源
我们要把原生的源替换为我们国内的源，这样国内网速就会相比国外的快很多。但是国内的源有很多 我们会在下面进行举例。
将上面文件进行编辑，删除原先的内容或者进行全部注释，将以下内容添加到文件中。选择其中比较适合自己的源，有的因为网路哟运营商的选择不同，效果也不同，选择适合自己的应用
### 阿里云
```
deb http://mirrors.aliyun.com/ubuntu/ trusty main restricted universe multiverse
deb http://mirrors.aliyun.com/ubuntu/ trusty-security main restricted universe multiverse
deb http://mirrors.aliyun.com/ubuntu/ trusty-updates main restricted universe multiverse
deb http://mirrors.aliyun.com/ubuntu/ trusty-proposed main restricted universe multiverse
deb http://mirrors.aliyun.com/ubuntu/ trusty-backports main restricted universe multiverse
deb-src http://mirrors.aliyun.com/ubuntu/ trusty main restricted universe multiverse
deb-src http://mirrors.aliyun.com/ubuntu/ trusty-security main restricted universe multiverse
deb-src http://mirrors.aliyun.com/ubuntu/ trusty-updates main restricted universe multiverse
deb-src http://mirrors.aliyun.com/ubuntu/ trusty-proposed main restricted universe multiverse
deb-src http://mirrors.aliyun.com/ubuntu/ trusty-backports main restricted universe multiverse
```
### 清华大学源
```
deb http://mirrors.tuna.tsinghua.edu.cn/ubuntu/ xenial main restricted
deb http://mirrors.tuna.tsinghua.edu.cn/ubuntu/ xenial-updates main restricted
deb http://mirrors.tuna.tsinghua.edu.cn/ubuntu/ xenial universe
deb http://mirrors.tuna.tsinghua.edu.cn/ubuntu/ xenial-updates universe
deb http://mirrors.tuna.tsinghua.edu.cn/ubuntu/ xenial multiverse
deb http://mirrors.tuna.tsinghua.edu.cn/ubuntu/ xenial-updates multiverse
deb http://mirrors.tuna.tsinghua.edu.cn/ubuntu/ xenial-backports main restricted universe multiverse
deb http://mirrors.tuna.tsinghua.edu.cn/ubuntu/ xenial-security main restricted
deb http://mirrors.tuna.tsinghua.edu.cn/ubuntu/ xenial-security universe
deb http://mirrors.tuna.tsinghua.edu.cn/ubuntu/ xenial-security multiverse  
```
### 网易源
```
deb http://mirrors.163.com/ubuntu/ wily main restricted universe multiverse
deb http://mirrors.163.com/ubuntu/ wily-security main restricted universe multiverse
deb http://mirrors.163.com/ubuntu/ wily-updates main restricted universe multiverse
deb http://mirrors.163.com/ubuntu/ wily-proposed main restricted universe multiverse
deb http://mirrors.163.com/ubuntu/ wily-backports main restricted universe multiverse
deb-src http://mirrors.163.com/ubuntu/ wily main restricted universe multiverse
deb-src http://mirrors.163.com/ubuntu/ wily-security main restricted universe multiverse
deb-src http://mirrors.163.com/ubuntu/ wily-updates main restricted universe multiverse
deb-src http://mirrors.163.com/ubuntu/ wily-proposed main restricted universe multiverse
deb-src http://mirrors.163.com/ubuntu/ wily-backports main restricted universe multiverse
```
3. 进行源更新
```
sudo apt-get update 
```

# Docker 源更换
我们最近的主要学习点在Dokcer的学习，但是我们的Docker 也是起源于国外，所以原生的源也都是采用的国外的地址，我们国内大家都明白，访问国外都是很慢的，所以我们在Docker操作的时候也要先进行换源操作。
1. 源列表。
我们也有很多源，所以我这里列举几个源地址，大家可以根据自己的选择进行抉择。

```
网易源：http://hub-mirror.c.163.com
Docker官方中国区：https://registry.docker-cn.com
ustc : https://docker.mirrors.ustc.edu.cn
阿里云:https://jzngeu7d.mirror.aliyuncs.com
```
2. 修改
 找到合适的源后，我们开始进行修改操作
- 临时修改 ： 
```
docker run redis --registry-mirror=https://registry.docker-cn.com
```
- 修改docker文件
```
vim /etc/default/docker
添加下面的参数，可以添加多个
DOCKER_OPTS="--registry-mirror=https://docker.mirrors.ustc.edu.cn"
DOCKER_OPTS="--registry-mirror=https://registry.docker-cn.com"
```
- 新版的Docker可以修改json配置文件来修改
```
vim /etc/docker/daemon.json
如果不是这个默认路径 那么需要修改dockerd中的 --config-file文件添加以下内容
{ 
"registry-mirrors": ["https://docker.mirrors.ustc.edu.cn"] 
}
```
3. 修改完毕后进行docker的重启docker在进行操作的时候就可以看到速度很快了
```
重启命令 service docker restart 
启动命令 service docker start 
停止命令 service docker stop
```
# 总结
至此，我们在操作中进行了Linux系统源的更换和Docker源的更换，大家可以肆意的享受国内的加速了。
