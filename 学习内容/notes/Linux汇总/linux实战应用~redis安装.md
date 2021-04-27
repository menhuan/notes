今天在写毕设的时候，突然想在毕设项目中增加缓存这个东西，来减轻数据库的压力，就简单的研究了下redis的单独安装与集群安装，本项目只是在一个云服务器上安装用到的是本地的东西。
#1安装redis
  1. 我的系统是centos7系统那么可以用yum或者wegt的方式来安装，我选择的是按照wegt的方式安装，具体执行方案可以看逛网https://redis.io/download 下面介绍
首先 wget http://download.redis.io/releases/redis-3.2.8.tar.gz 在保存这个压缩包的地方执行命令 下载下来。然后 **tar xzf redis-3.2.8.tar.gz** 执行这个命令解压缩文件 ，**cd redis-3.2.8** 这个是解压缩下来的文件可以，将其改名为redis-什么的，我暂时没有改名，因为搭建集群是为了方便与其他的名称区别开来，这个只是作为单机版的***redis***.  进去文件后 执行make ,就直接安装了 ，在这里还可以指定安装的位置 make  install 命令来指定位置。
  1. 启动redis
   接上部文件，我们进入到redis-3.2.8中后，会展示如图所示文件内容。
![redis解压后文件内容](http://upload-images.jianshu.io/upload_images/4237685-89ccf3962246d453.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
  启动redis有前台启动（src/redis-server）和后台启动（后台启动需要我们修改redis.conf中的daemonize 为yes）  命令为src/redis-server  ./redis.conf
  2. 远程链接
 在redis中默认用的是本地连接，不支持远程连接需要我们自己修改redis.conf中文件来支持远程连接。 还需要修改conf中的daemonize 为yes  支持链接
![修改ip](http://upload-images.jianshu.io/upload_images/4237685-619edac13e54641c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
我们把#bind 127.0.0.1 这一行注释掉，是为了让所有ip都可以用来访问，当然可以用来远程连接，但是对于redis3.2版本以上的还需要修改一个命令才能支持远程调用。

![新的远程连接命令修改](http://upload-images.jianshu.io/upload_images/4237685-8d05461e4ee2523d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)我们需要把这个命令关闭掉并且结合ip设置那里，才能支持远程服务调用redis.
#2 修改密码
  我们进入redis中有的需要我们进行密码设置 ***src/redis-cli*** ，客户端连接好之后输入 ***config set requirepass 密码*** 就能设置我们的密码 不需要再去修改配置文件。我们重新在进入redis时就需要重新输入密码 auth 密码
#3集群的安装
我的这个集群是安装在一个云服务器上的。
![集群安装目录](http://upload-images.jianshu.io/upload_images/4237685-46f8e4f2d460b78b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
1. 首先把刚才安装的redis单机版信息复制过来 ，然后修改redis.conf中的配置文件端口号 ，并且打开Cluster-enable yes 这个命令。
2. 我们需要一个redis-trib.rb的脚本这个是在解压的源文件中就存在的，在src文件目录中，将其复制到我们刚才新建的目录下与其他redis共同存在。
3. 执行ruby脚本之前，需要安装ruby相关的环境，***yum install ruby***，***yum install rubygems***，还需要安装运行需要依赖的ruby的包 ***gem install redis-3.0.0.gem***，这个可以在百度上搜索到。
4. 启动我们所有的redis实例，写入到start-all.sh文件中，在这里可能出现，我们的操作人员没有权限执行的问题，需要我们执行chomd u+x *.sh 赋予权限 才可以执行，其他文件可以用来这个命令来赋予权限。
5. /redis-trib.rb create --replicas 1 192.168.24.15:7001 192.168.24.15:7002 192.168.24.15:7003 192.168.24.15:7004 192.168.24.15:7005  192.168.24.15:7006  执行这个命令后就能启动我们的集群了；
需要注意的是ip可以是我们的远程服务器的ip地址哦。
关于liunx上安装redis的介绍，就简单的到这里吧。
