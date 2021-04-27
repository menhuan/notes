随着我们几篇文章Docker的命令学习，关于Docker本地镜像管理的命令学习已经接近尾声。今天我们就学习下本地镜像管理的最后三个命令history,save, import。当然前面命令没有掌握熟练的同学可以先回顾下原先的命令，在学习本章知识。
[容器Docker学习系列一](https://www.jianshu.com/p/4619963629a6)
[容器Docker学习系列二](https://www.jianshu.com/p/ed3b3e740d28)
[容器Docker学习系列三~命令学习](https://www.jianshu.com/p/e53dad602dcc)
[容器Docker学习系列四~源的修改增加效率](https://www.jianshu.com/p/9704bd1121a3)
那么现在开始我们现在的命令学习
# history
docker history命令是用来查看指定镜像的创建历史。
> 语法糖 ： docker history [OPTIONS]  image

OPTION还是原先的可选参数：
- -H:按照可读的格式打印镜像大小和日期，默认为true
- --no-runc: 显示提交记录
- -q:列出提交记录的ID.
例子如下：
```
docker@ubuntu:~$ docker history mongo
IMAGE               CREATED             CREATED BY                                      SIZE                COMMENT
8bf72137439e        8 days ago          /bin/sh -c #(nop)  CMD ["mongod"]               0B                  
<missing>           8 days ago          /bin/sh -c #(nop)  EXPOSE 27017/tcp             0B                  
<missing>           8 days ago          /bin/sh -c #(nop)  ENTRYPOINT ["docker-entry…   0B                  
<missing>           8 days ago          /bin/sh -c #(nop) COPY file:18c5d9b642a89adf…   10.4kB              
<missing>           8 days ago          /bin/sh -c #(nop)  VOLUME [/data/db /data/co…   0B                  
<missing>           8 days ago          /bin/sh -c mkdir -p /data/db /data/configdb …   0B                  
<missing>           8 days ago          /bin/sh -c set -x  && apt-get update  && apt…   256MB               
<missing>           8 days ago          /bin/sh -c echo "deb http://$MONGO_REPO/apt/…   73B                 
<missing>           8 days ago          /bin/sh -c #(nop)  ENV MONGO_VERSION=4.0.1      0B                  
<missing>           2 weeks ago         /bin/sh -c #(nop)  ENV MONGO_MAJOR=4.0          0B                  
<missing>           2 weeks ago         /bin/sh -c #(nop)  ENV MONGO_PACKAGE=mongodb…   0B                  
<missing>           2 weeks ago         /bin/sh -c #(nop)  ARG MONGO_REPO=repo.mongo…   0B                  
<missing>           2 weeks ago         /bin/sh -c #(nop)  ARG MONGO_PACKAGE=mongodb…   0B              

docker@ubuntu:~$ docker history -q mongo
8bf72137439e
<missing>
<missing>
<missing>
<missing>
<missing>
```
 
# save
docker save 是值将指定镜像保存为tar归档文件。我们可以将docker文件转发给别人直接来使用。
>语法糖 docker save [OPTIONS] IMAGE[]数组

OPTIONS 说明：
- -o:输出到的文件

例子：
```
docker@ubuntu:~$ docker save -o mongo.tar mongo
docker@ubuntu:~$ ls
Desktop    Downloads         mongo.tar  Pictures  Templates
Documents  examples.desktop  Music      Public    Videos
```
# import 
我们刚才在上面使用了save命令来进行docker文件的归档，那么我们如果拿到归档tar文件，我们怎么恢复成docker文件呢？现在我们就可以使用到import命令了
> 语法糖 docker import [OPTIONS] file[URL] [REPOSITORY:TAG]

OPTIONS:
- -c:应用docker指令创建镜像
- -m: 提交时的说明文字

例子：
```
docker@ubuntu:~$ docker import mongo.tar mong:v1
sha256:9a611936a0107c2f07e04ea586722dba2b6c6451c3f1ae68fda30496b91c606e
docker@ubuntu:~$ docker images
REPOSITORY          TAG                 IMAGE ID            CREATED             SIZE
mong                v1                  9a611936a010        26 seconds ago      386MB
mongo               13.2                8bf72137439e        8 days ago          380MB
mongo               latest              8bf72137439e        8 days ago          380MB
```
我们可以看到里面新增了一个TAG为V1的mong 。这就是我们新增加的。

# 总结
从上面看的命令我们可以了解到这些本地镜像命令都是相关的，用来帮助我们操作docker的信息。本地镜像管理命令到今天为止就学完了。下次我们在学习别的。
学习完这一章我们可以回顾原先学习到的内容，进行回顾总结。
[容器Docker学习系列一](https://www.jianshu.com/p/4619963629a6)
[容器Docker学习系列二](https://www.jianshu.com/p/ed3b3e740d28)
[容器Docker学习系列三~命令学习](https://www.jianshu.com/p/e53dad602dcc)
[容器Docker学习系列四~源的修改增加效率](https://www.jianshu.com/p/9704bd1121a3)
