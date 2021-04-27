安装gogs服务会使用docker 安装。首先安装docker环境 。本次安装是在Centos7上安装其他版本没有尝试请自己尝试。
#### docker安装
1. yum install docker ，安装完毕后使用 docker -v  查看下版本号

![安装成功](http://upload-images.jianshu.io/upload_images/4237685-bdc80369d3ea598b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
2. 使用systemctl start docker 启动docker
#### 安装gogs
1. 使用docker pull gogs/gogs 把镜像下载下来

![下载完成](http://upload-images.jianshu.io/upload_images/4237685-25fa678699ee7c94.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
2. 创建存储代码的仓库和配置文件所在的位置  使用mkdir -p /var/gogs
3. 使用docker 命令启动gogs docker run --name=gogs -p 10022:22 -p 10080:3000 -v /var/gogs:/data gogs/gogs 
 其中 10022映射的是docker中的22端口  10080映射的是http的3000端口 ，这个在我们启动的时候会使用到该配置。
4. docker start gogs 启动gogs

![启动成功](http://upload-images.jianshu.io/upload_images/4237685-b8793822666c7ab9.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
5. 访问gogs，访问链接是ip+端口
![访问成功](http://upload-images.jianshu.io/upload_images/4237685-580ad8cd92923393.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
6. 配置gogs
- 数据库配置
![数据库配置](http://upload-images.jianshu.io/upload_images/4237685-1be0ca4e3497df34.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
- ssh与http配置

![http配置](http://upload-images.jianshu.io/upload_images/4237685-4fef431265c4483a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
- 设置管理员账号

![管理员账号](http://upload-images.jianshu.io/upload_images/4237685-82c9db9575a62bcb.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
-设置完之后自动登陆，我们创建一个git仓库用来测试

![创建仓库测试](http://upload-images.jianshu.io/upload_images/4237685-62a116c5a820b5de.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
- git clone 执行，获得远程仓库

![执行成功](http://upload-images.jianshu.io/upload_images/4237685-97e0ff19f759ef9a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
#### 可能遇到的问题
1. 如果启动后，使用ip+端口号还是访问不到 ，请查看下防火墙的端口是否打开。如果是云服务器可以，阿里的可以使用安全策略进行修改。如果是金山云的大米云 可以去控制台增加对应的端口放开防火墙限制。
