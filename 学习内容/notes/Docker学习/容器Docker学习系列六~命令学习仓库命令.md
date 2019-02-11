在前面几篇文章中，我们学习了怎么安装Docker，并进行本地Docker的操作，现在我们就学习下Docker仓库的学习，其实Docker很多理念操作也是来自git,比如我们在下载一个镜像的时候，可以看到多个下载hash。
下载的时候已经下载过的就可以不在进行下载。继续下载其他没有下载的就好。我们仓库提供了这个功能的实现，接下来我们来看下镜像仓库有什么命令吧。
# login/logout
Docker也是有权限验证的，所以我们也需要登录去上传自己制作的Docker文件。没有账户的朋友是没法上传镜像到仓库中的。
>语法糖 docker login[OPTIONS] [SERVER]  
docker logout [OPTIONS] [SERVER]

OPTIONS : 还是可选参数
- -u:登录的用户名
- -p:登录的密码

列子
```
ruiqi@fruiqi:~/Desktop/code/database-H2-demo$ docker logout  hub.xxxa.com 
Removing login credentials for hub.infervision.com
ruiqi@fruiqi:~/Desktop/code/database-H2-demo$ docker login -u fruiqi -p ×××× sfa.xxxa.com 
WARNING! Using --password via the CLI is insecure. Use --password-stdin.
Login Succeeded
```
# pull 
从我们指定的仓库拉去或者更新指定镜像
>语法糖 docker pull [OPTIONS]  [Dcoker Registory 地址[:端口号]/] 仓库名[:标签]

其中 OPTIONS可选的：
- -a:拉取所有的tagged镜像
- --disable-content-trues:忽略镜像的校验，默认开启

列子:
```
//查询mongo版本 看自己使用哪个
docker@ubuntu:~$ docker search mongo 
NAME                                DESCRIPTION                                     STARS               OFFICIAL            AUTOMATED
mongo                               MongoDB document databases provide high avai…   4852                [OK]                
mongo-express                       Web-based MongoDB admin interface, written w…   280                 [OK]                
tutum/mongodb                       MongoDB Docker image – listens in port 27017…   225                                     [OK]
mvertes/alpine-mongo                light MongoDB container                         79                                      [OK]
mongoclient/mongoclient             Official docker image for Mongoclient, featu…   53                                      [OK]
bitnami/mongodb                     Bitnami MongoDB Docker Image                    45                                      [OK]
frodenas/mongodb                    A Docker Image for MongoDB                      17                                      [OK]
mongooseim/mongooseim               Small docker image for MongooseIM - robust a…   16                                      
mongooseim/mongooseim-docker        MongooseIM server the latest stable version     11                                      [OK]
cvallance/mongo-k8s-sidecar         Kubernetes side car to setup and maintain a …   8                                       [OK]
centos/mongodb-26-centos7           MongoDB NoSQL database server                   5                                       
centos/mongodb-32-centos7           MongoDB NoSQL database server                   5                                       
istepanov/mongodump                 Docker image with mongodump running as a cro…   5                                       [OK]
eses/mongodb_exporter               mongodb exporter for prometheus                 4                                       [OK]
khezen/mongo                        MongoDB Docker image supporting RocksDB stor…   4                                       [OK]
neowaylabs/mongodb-mms-agent        This Docker image with MongoDB Monitoring Ag…   2                                       [OK]
centos/mongodb-34-centos7           MongoDB NoSQL database server                   1                                       
centos/mongodb-36-centos7           MongoDB NoSQL database server                   1                                       
openshift/mongodb-24-centos7        DEPRECATED: A Centos7 based MongoDB v2.4 ima…   1                                       
webhippie/mongodb                   Docker images for mongodb                       1                                       [OK]
circleci/mongo                      CircleCI images for MongoDB                     1                                       [OK]
ekesken/mongo                       docker image for mongo that is configurable …   1                                       [OK]
ansibleplaybookbundle/mongodb-apb   An APB to deploy MongoDB.                       0                                       [OK]
amd64/mongo                         MongoDB document databases provide high avai…   0                                       
quilt/mongo                         MongoDB container for quilt.io                  0                                       [OK]
//执行下载最新的版本  有点大 需要一段时间下载  如果需要下载别的自己根据名称下载即可
docker@ubuntu:~$ docker pull mongo
Using default tag: latest
latest: Pulling from library/mongo
docker@ubuntu:~$ docker pull -a  mongo   拉取所有的版本 具体自己可以尝试下
2.2.7: Pulling from library/mongo
```
# push
将本地的镜像上传到镜像仓库，但是这里需要我们先登录到镜像仓库
> 语法糖 docker push [PTIONS] NAME:[:TAG]

OPTIONS 说明：
- -disable-content-trust：忽略镜像的校验，默认开启
我这里自己使用的是阿里云的镜像仓库，大家可以自己去创建一个[阿里云镜像仓库](https://dev.aliyun.com/search.html)(管理中心–>创建镜像仓库–>我的是华东1绑定github账户即可）
列子：这里我用的是我们公司的仓库
```
ruiqi@fruiqi:~/Desktop/code/database-H2-demo$ docker logout  hub.asdada.com 
Removing login credentials for hub.infervision.com
ruiqi@fruiqi:~/Desktop/code/database-H2-demo$ docker login -u fruiqi -p 密码  hub.asdada.com 
WARNING! Using --password via the CLI is insecure. Use --password-stdin.
Login Succeeded
//显示登录成功后，我们就可以直接进行push操作了。
ruiqi@fruiqi:~/Desktop/code/database-H2-demo$ docker push hub.asdada.com/fruiqi/datamanagesystem:2.0.2 
The push refers to repository [hub.asdada.com/fruiqi/datamanagesystem]
5da685150776: Pushed 
7a42af8ace8d: Layer already exists 
e80bd912af31: Layer already exists 
8bc7bbcd76b6: Layer already exists 
298c3bb2664f: Layer already exists 
73046094a9b8: Layer already exists 
2.0.2: digest: sha256:aa27d45306540e5cd9e4dc1871b9229a276196a1dacf14eef6fe44bda63a945b size: 1576

```
上面的操作就完成了，我们就可以在我们的仓库看到已经上传的docker容器。
# search
docker 搜索镜像，查找符合自己想要的版本镜像
> 语法糖 docker search [OPTIONS] TERM 

OPTIONS说明：
- --no-trunc:显示完整的镜像描述
- -s:列出收藏数不小于指定值的镜像。
列子：
```
ruiqi@fruiqi:~/Desktop/code/database-H2-demo$ docker search -s 100 mysql
Flag --stars has been deprecated, use --filter=stars=3 instead
NAME                         DESCRIPTION                                     STARS               OFFICIAL            AUTOMATED
mysql                        MySQL is a widely used, open-source relation…   6765                [OK]                
mariadb                      MariaDB is a community-developed fork of MyS…   2159                [OK]                
mysql/mysql-server           Optimized MySQL Server Docker images. Create…   495                                     [OK]
percona                      Percona Server is a fork of the MySQL relati…   360                 [OK]                
zabbix/zabbix-server-mysql   Zabbix Server with MySQL database support       116                                     [OK]
```
今天我们学习这四个命令帮助我们快速的构建查找，更新，推送新的版本镜像。当然我们还可以进行登录，操作等等。通过今天的学习，我们可以很方便的管理自己的私人远程仓库。当然如果原先的命令忘记了，可以点击以下文章进行学习
[容器Docker学习系列一](https://www.jianshu.com/p/4619963629a6)
[容器Docker学习系列二](https://www.jianshu.com/p/ed3b3e740d28)
[容器Docker学习系列三~命令学习](https://www.jianshu.com/p/e53dad602dcc)
[容器Docker学习系列四~源的修改增加效率](https://www.jianshu.com/p/9704bd1121a3)
[容器Docker学习系列五~命令学习history,save, import](https://www.jianshu.com/p/2e998757a231)




