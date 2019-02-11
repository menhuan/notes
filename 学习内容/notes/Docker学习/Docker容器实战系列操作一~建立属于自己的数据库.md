经过多篇的命令学习，我们已经了解了基本上所有的命令，如今开始第二篇我们的实战操作。这次是操作数据库命令，好了也是两种方式一种是简单的命令行，第二种还是我们的DockerFIle文件操作。
当然首先还是看下我们简单的指令详解，巩固以下再去学习，这样让我们更加的熟悉与了解。

# 命令行的的操作
1. 首先从仓库中获取我们的数据库。
```
docker pull mysql 
docker run -it --name  test-mysql -d mysql /bin/bash  //该命令会自己先检查镜像是否存在，不存在会进行下载。然后在进入到容器中。
```
2. 启动容器
```
docker run -it --name test-mysql -d mysql  /bin/bash  //但是在这里会出现问题因为我们的mysql验证的问题需要进行账户和密码的操作 在这步进行设置好，当然也有编码的问题在这里也得设置。
//该命令就改变成为
docker run -p 3306:3306 --name test-mysql -v /home/ruiqi/Desktop/disk/mysql/conf/:/etc/mysql/my.cnf -v /home/ruiqi/Desktop/disk/mysql/logs:/logs -v /home/ruiqi/Desktop/disk/mysql/data:/mysql_data -e MYSQL_ROOT_PASSWORD=123456 -d mysql
    
    命令说明：
    
        -p 3306:3306：将容器的3306端口映射到主机的3306端口
    
        -v ~/mysql/conf/my.cnf:/etc/mysql/my.cnf：将主机~/mysql/conf/my.cnf挂载到容器的/etc/mysql/my.cnf (这里不额外加配置可以不用配置,我这边没有配置)
    
        -v ~/mysql/logs:/logs：将主机~/mysql/logs目录挂载到容器的/logs
    
        -v ~/mysql/data:/mysql_data：将主机~/mysql/data目录挂载到容器的/mysql_data 进行参数的挂载。保证容器挂了 ，数据还在
      
        -e MYSQL_ROOT_PASSWORD=123456：初始化root用户的密码
```
3. 如果上面执行启动容器报错出现已存在的字眼，我们需要用其他命令来启动
```
docker start test-mysql //该命令启动已停止的容器 。
docker stop test-mysql //该命令停止已启动的容器
```
4. 上面步骤设置好容器后，进入容器
```
docker exec -it test-mysql /bin/bash //进入容器就可以跟更平常操作数据库一样操作了
```

# DockerFIle文件操作数据库
```
FROM debian:stretch-slim
# 用于增加工作组和用户组
RUN groupadd -r mysql && useradd  -r -g mysql mysql
RUN apt-get update && apt-get install -y --no-install-recommends gnupg dirmngr && rm -rf /var/lib/apt/lists/*

ENV GOSU_VERSION 1.7
RUN set -x \
&& apt-get update && apt-get install -y --no-install-recommends ca-certificates wget && rm -rf /var/lib/apt/lists/* \
&& wget -O /usr/local/bin/gosu "https://github.com/tianon/gosu/releases/download/$GOSU_VERSION/gosu-$(dpkg --print-architecture)" \
&& wget -O /usr/local/bin/gosu.asc "https://github.com/tianon/gosu/releases/download/$GOSU_VERSION/gosu-$(dpkg --print-architecture).asc" \
&& export GNUPGHOME="$(mktemp -d)" \
&& gpg --keyserver ha.pool.sks-keyservers.net --recv-keys B42F6819007F00F88E364FD4036A9C25BF357DD4 \
&& gpg --batch --verify /usr/local/bin/gosu.asc /usr/local/bin/gosu \
&& gpgconf --kill all \
&& rm -rf "$GNUPGHOME" /usr/local/bin/gosu.asc \
&& chmod +x /usr/local/bin/gosu \
&& gosu nobody true \
&& apt-get purge -y --auto-remove ca-certificates wget
RUN mkdir /docker-entrypoint-initdb.d

RUN apt-get update && apt-get install -y --no-install-recommends \
# for MYSQL_RANDOM_ROOT_PASSWORD
        pwgen \
# for mysql_ssl_rsa_setup
        openssl \
# FATAL ERROR: please install the following Perl modules before executing /usr/local/mysql/scripts/mysql_install_db:
# File::Basename
# File::Copy
# Sys::Hostname
# Data::Dumper
        perl \
    && rm -rf /var/lib/apt/lists/*

RUN set -ex; \
# gpg: key 5072E1F5: public key "MySQL Release Engineering <mysql-build@oss.oracle.com>" imported
    key='A4A9406876FCBD3C456770C88C718D3B5072E1F5'; \
    export GNUPGHOME="$(mktemp -d)"; \
    gpg --keyserver ha.pool.sks-keyservers.net --recv-keys "$key"; \
    gpg --export "$key" > /etc/apt/trusted.gpg.d/mysql.gpg; \
    gpgconf --kill all; \
    rm -rf "$GNUPGHOME"; \
    apt-key list > /dev/null

# 设置环境变量    
ENV MYSQL_MAJOR 5.7
ENV MYSQL_VERSION 5.7.23-1debian9

RUN echo "deb http://repo.mysql.com/apt/debian/ stretch mysql-${MYSQL_MAJOR}" > /etc/apt/sources.list.d/mysql.list
RUN { \
        echo mysql-community-server mysql-community-server/data-dir select ''; \
        echo mysql-community-server mysql-community-server/root-pass password ''; \
        echo mysql-community-server mysql-community-server/re-root-pass password ''; \
        echo mysql-community-server mysql-community-server/remove-test-db select false; \
    } | debconf-set-selections \
    && apt-get update && apt-get install -y mysql-server="${MYSQL_VERSION}" && rm -rf /var/lib/apt/lists/* \
    && rm -rf /var/lib/mysql && mkdir -p /var/lib/mysql /var/run/mysqld \
    && chown -R mysql:mysql /var/lib/mysql /var/run/mysqld \
# ensure that /var/run/mysqld (used for socket and lock files) is writable regardless of the UID our mysqld instance ends up having at runtime
    && chmod 777 /var/run/mysqld \
# comment out a few problematic configuration values
    && find /etc/mysql/ -name '*.cnf' -print0 \
        | xargs -0 grep -lZE '^(bind-address|log)' \
        | xargs -rt -0 sed -Ei 's/^(bind-address|log)/#&/' \
# don't reverse lookup hostnames, they are usually another container
    && echo '[mysqld]\nskip-host-cache\nskip-name-resolve' > /etc/mysql/conf.d/docker.cnf
# 设置一个挂载点
VOLUME /var/lib/mysql
# 将本目录下的脚本文件复制到
COPY docker-entrypoint.sh  /entrypoint.sh
COPY helthcheck.sh /healthcheck.sh
# 指定镜像的默认入坑了楼
ENTRYPOINT ["/entrypoint.sh"]

# 暴露端口
EXPOSE 3306 3306
#执行
CMD ["mysqld"]
```
1. 该DockerFile是直接照搬github上的[mysql5.7DockerFile](https://github.com/docker-library/mysql/tree/master/5.7),我用来主要是练习其命令。大家可以夺取github上多看。
2. 执行脚本文件也参照文件中[mysql5.7DockerFile](https://github.com/docker-library/mysql/tree/master/5.7)
3.进行doocker build -t mysql:test . 
![等待执行完毕即可](https://upload-images.jianshu.io/upload_images/4237685-055cae529380e4f3.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
4. 剩下的操作跟上面就一直，可以参考上面所演示的内容启动docker.
5. 实战的目的是让我们自己熟悉命令操作，并且掌握住，经常看别人的操作代码，也会提高我们自己的操作技能。
