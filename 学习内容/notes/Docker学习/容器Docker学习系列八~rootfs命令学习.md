基础是一步一步的get到的。随着自己写基础知识，也发现了很多原先没有注意到的知识点。工作是我们把知识进行应用的地方，但是也不应该不能让工作把我们局限住。继续扩展学习。
我们今天学习下Docker的**rootfs**相关的命令
#commit
从容器中创建一个新的镜像。
> 语法糖 docker commit  [OPTIONS] CONTAINER ID [新的容器名字与标签]
OPTIONS 选项说明：
- -a: 提交镜像的作者名字；
- -c：使用DockerFile指令来创建镜像
- -m:提交时的说明文字
- -p : 在commit的时候，将容器暂停
例子：
```
runoob@runoob:~$ docker commit -a "runoob.com" -m "my apache" a404c6c174a2  mymysql:v1 
sha256:37af1236adef1544e8886be23010b66577647a40bc02c0885a6600b33ee28057
runoob@runoob:~$ docker images mymysql:v1
REPOSITORY          TAG                 IMAGE ID            CREATED             SIZE
mymysql             v1                  37af1236adef        15 seconds ago      329 MB
```
# cp
在使用的过程中会进行多个主机与容器之间的数据交换。
>语法糖 docker cp [OPTIONS] CONTAINER:SRC_PATH DEST_PATH|-
docker cp [OPTIONS] SRC_PATH|- CONTAINER:DEST_PATH

Options 可选参数:
- --archive -a   存档模式（复制所有UID/GID信息）
- --follow-link , -L 始终遵循SRC路径中的符号链接
实例
```
将主机/ruiqi/content目录拷贝到容器96fwf1wcv9ab的/ruiqi目录下。
docker cp /ruiqi/content 96fwf1wcv9ab:/ruiqi/

将主机 /ruiqi/content目录拷贝到容器96fwf1wcv9ab中，目录重命名为 /ruiqi。
docker cp /ruiqi/content  96fwf1wcv9ab:/ruiqi

将容器96fwf1wcv9ab的/www目录拷贝到主机的/ruiqi目录中。
docker cp  96fwf1wcv9ab:/ruiqi /ruiqi/
```
# diff
用来检查容器力文件结构的更改
>语法糖 docker diff [OPTIONS] 容器

列子
```
ruiqi@fruiqi:~/Desktop/code/database-H2-demo$ docker ps
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS                      NAMES
629da98805e8        mongo               "docker-entrypoint.s…"   9 days ago          Up 7 days           0.0.0.0:27017->27017/tcp   mongo
5cc5bf6da993        redis               "docker-entrypoint.s…"   9 days ago          Up 7 days           0.0.0.0:6379->6379/tcp     redis
ff8fbd80ead8        mariadb             "docker-entrypoint.s…"   9 days ago          Up 7 days           0.0.0.0:3306->3306/tcp     mariadb
ruiqi@fruiqi:~/Desktop/code/database-H2-demo$ docker diff mongo
C /tmp
A /tmp/mongodb-27017.sock
ruiqi@fruiqi:~/Desktop/code/database-H2-demo$ docker diff redis
ruiqi@fruiqi:~/Desktop/code/database-H2-demo$ docker diff mariadb
C /run/mysqld
A /run/mysqld/mysqld.pid
A /run/mysqld/mysqld.sock
C /tmp
```
今天三个命令主要集中在数据信息的了解，平常使用不是很多，但是也需要了解，如果docker出现问题或者进行数据的迁移我们就需要用到，了解到平常偶尔练习下，就不会出现不熟练的问题了。
[容器Docker学习系列一](https://www.jianshu.com/p/4619963629a6)
[容器Docker学习系列二](https://www.jianshu.com/p/ed3b3e740d28)
[容器Docker学习系列三~命令学习](https://www.jianshu.com/p/e53dad602dcc)
[容器Docker学习系列四~源的修改增加效率](https://www.jianshu.com/p/9704bd1121a3)
[容器Docker学习系列五~命令学习history,save, import](https://www.jianshu.com/p/2e998757a231)
[容器Docker学习系列六~命令学习仓库命令](https://www.jianshu.com/p/30c96cf533e3)
[容器Docker学习系列七~系统信息学习](https://www.jianshu.com/p/b4b913e4ec45)
