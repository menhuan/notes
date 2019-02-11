为了方便开发，特意把数据库配置到远程服务器上，方便自己连接。不用每次换地方后再把数据库重新安装配置。因为我的饿liunx 系统是centos7 上的。所以该方法是否适合在其他的的liunx 系统上使用暂不清楚。
####下载YUM库
连接：wget http://dev.mysql.com/get/mysql57-community-release-el7-7.noarch.rpm
#### 安装YUNM库
yum localinstall -y mysql57-community-release-el7-7.noarch.rpm
####安装数据库
yum install -y mysql-community-server
####启动数据库
 systemctl start mysqld.service 
因为刚开始的密码是空的那么我们需要修改一下密码。当然这里可能会因为其他原因导致有密码。首先验证下是否有没有密码
mysql -u root -p    然后终端会让我们输入密码

![空密码](http://upload-images.jianshu.io/upload_images/4237685-b9978a66efc9d06c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
我们直接敲回车就行，如果出现如图所示情况，那么就成功进入mysql

![进入mysql](http://upload-images.jianshu.io/upload_images/4237685-a11cd53793dd88e8.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
如果出现的是ERROR 1045 (28000): Access denied for user 'root'@'localhost' (using password: NO)，那么需要我们坐下调整。
首先进入my.cnf 文件中。
vim /etc/my.cnf 中。在图示的地方输入skip-grant-tables
skip-networking  ，跳过安全验证。
![跳过安全验证.png](http://upload-images.jianshu.io/upload_images/4237685-9a259f1c3bbd3b4a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
然后我们需要重新启动mysql . systemctl restart mysqld;
进入后修改我们的密码：update mysql.user set authentication_string=password("123456") where user="root" and  host='root' or Host="localhost";
 flush privileges; 
quit；退出后，需要重新启动mysql . systemctl restart mysqld;
我们在前面修改了my.cnf文件 需要把刚才的验证去掉 vim /etc/my.cnf 。 将其注释掉即可skip-grant-tables，skip-networking。
重新启动mysql。
我们大部分会将mysql布置在我们的远程服务器上为了方便使用。我们也修改下命令使其支持远程服务。
刚才启动mysql后，我们成功进入mysql后，使用use  mysql 。 然而这可能出现 问题 让我们修改 密码：You must reset your password using ALTER USER statement before executing this statement. 那么这个是因为安全验证导致的问题。
我们可以使用以下命令帮助我们修改
mysql> set global validate_password_policy=0;  去掉安全验证 
mysql> set global validate_password_length=1;   然后这个是改变长度
。
如果这个方式不可以的话，那么我们可以修改下密码
set password for root@localhost =password('Idea2017');
这样我们就在修改我们的密码即可
use mysql ； 进入数据源中
GRANT ALL PRIVILEGES ON  *.*  TO 'root'@'%' IDENTIFIED BY 'yourpassword' WITH GRANT OPTION; 
这样之后我们重启mysql即可。
因为我们平常使用的utf-8的字符集，在修改一下我们平常用的字符集即可。vim /etc/my.cnf .   character-set-server=utf8，
collation-server=utf8_general_ci

![字符集命令](http://upload-images.jianshu.io/upload_images/4237685-22587edc01f33bff.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
添加上即可。
然后重新启动我们的mysql 。远程连接成功如图所示。


![成功远程连接](http://upload-images.jianshu.io/upload_images/4237685-c20bff79c69c1bbb.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
