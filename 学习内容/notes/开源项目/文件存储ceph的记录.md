# ceph

ceph是一款专注于分布式，弹性可扩展的，高科高的，性能优异的系统存储平台，是跟阿里云的OOS同样可以提供对象存储的云服务。能支持P级别的数据存储

一说到大数据，我们能想到的是hadoop，底层数据存储的hdfs，但是实际上现在进入云时代之后，hdfs的要求内容对于一些存储就并不是十分合适。

比如阿里每天需要处理的图片就几P级别的数据量，在hdfs中压缩存储对于快速反应的性能来说是很大的损耗。他们自研的OSS和亚马逊的S3对象存储都是一样的。

我们今天说的ceph是一个开源的对象存储，并且兼容了亚马逊的S3接口与Swift对象存储接口，也就是我们可以使用s3的sdk来使用ceph了。

## 安装流程

我们安装工具是借助官网提供的一键安装进行安装。

### 机器分配

由于我们采用的是三台真实服务器，没有再接收多余的服务器配置，并且程序也是处于mvp版本，先实现基础功能，后期再进行扩展。

![2019-03-14-20-06-08](http://jikelearn.cn/2019-03-14-20-06-08.png)

三台一样的机器配置

- 2个128的固态硬盘，一个系统盘，另外一个所谓数据库存储。
- 3个8T普通机器硬盘。
- 32核32G内存。

tidb1上部署安装工具，ceph-deploy。数据节点部署在三台服务器上。监控节点与rgw，对象存储服务配置在tidb2节点上。

### 配置ceph源

由于centos中并没有我们需要的配置源，那么就需要配置ceph的安装源。由于在国内，我们一般都是配置阿里的源操作。

```Linux
cat >/etc/yum.repos.d/ceph.repo<<EOF
[ceph]
name=ceph
baseurl=http://mirrors.aliyun.com/ceph/rpm-jewel/el7/x86_64/
gpgcheck=0
priority=1

[ceph-noarch]
name=cephnoarch
baseurl=http://mirrors.aliyun.com/ceph/rpm-jewel/el7/noarch/
gpgcheck=0
priority=1

[ceph-source]
name=Ceph source packages
baseurl=http://mirrors.163.com/ceph/rpm-jewel/el7/SRPMS
enabled=0
gpgcheck=1
type=rpm-md
gpgkey=http://mirrors.163.com/ceph/keys/release.asc
priority=1
EOF
yum makecache
```

### 安装ceph-deploy

如果在上面没有执行阿里源的配置，我们在执行的时候就需要配置下源，否则无法正常安装

```Linux

1. 配置
vim /etc/yum.repos.d/ceph.repo
[ceph-noarch]
name=Ceph noarch packages
baseurl=https://download.ceph.com/rpm-jewel/el7/noarch
enabled=1
gpgcheck=1
type=rpm-md
gpgkey=https://download.ceph.com/keys/release.asc

2. 更新系统
yum update -y
3. 执行安装
yum install -y ceph-deploy

```

### 配置免密登录的ssh

#### 配置互相登录的host

```Linux
//  每台机器都需要 增加ip与对应的名称
192.168.130.81 tibd1
192.168.130.82 tidb2
192.168.130.83 tidb3
```

#### 配置秘钥

每台机器上都需要执行下，这样才能达到互相登录的目的

在这里不使用root用户执行，创建一个新的用户来操作ceph.

```Linux
//每一个节点都需要创建该用户.

sudo useradd -d /home/cephd -m cephd
sudo passwd cephd

//新创建的用户需要有权限
echo "cephd ALL = (root) NOPASSWD:ALL" | sudo tee /etc/sudoers.d/cephd
sudo chmod 0440 /etc/sudoers.d/cephd
```

创建秘钥,然后分发到其他节点上

```Linux

ssh-keygen -t rsa
Enter file in which to save the key (/root/.ssh/id_rsa): #回车
Enter passphrase (empty for no passphrase): #回车
Enter same passphrase again:#回车

分发秘钥
ssh-copy-id cephd@tidb1
ssh-copy-id cephd@tidb2
ssh-copy-id cephd@tidb3
```

执行完秘钥的生成之后，可能会遇到的问题是.ssh文件权限不是700 ，所以我们一般会设置下权限。**注意：有的不需要执行**。

```Linux
sudo chmod 700　.ssh
```

```Linux
执行测试 尝试是否能不输入密码 就登陆到相应的服务器，成功后代表免登陆设置完毕
ssh cephd@tidb1
ssh cephd@tidb2
ssh cephd@tidb3
```

## 安装ceph

在tidb1上我们安装了ceph-deploy,使用这个工具在各个节点去安装ceph,保证分布式，我们在三台机器上都进行安装。

### 安装

准备工作做好，开始安装我们的ceph。

#### 创建ceph-deploy文件夹

该文件夹会保存一些通信需要使用的文件内容。

```Linux
mkdir my-cluster
cd my-cluster
```

#### 清理安装的内容

在安装的过程中如果出现未知的错误，可以使用如下命令，将相关内容清除，保证环境的清洁，再次进行安装。

```Linux
ceph-deploy purge {ceph-node} [{ceph-node}]
ceph-deploy purgedata {ceph-node} [{ceph-node}]
ceph-deploy forgetkeys
rm ceph.*

// 上面的可能跟个人安装过程有关，需要执行下面删除才能将前面出错的内容删除干净
rm -rf /var/lib/ceph
rm -rf /etc/ceph
rm -rf /var/run/ceph/
```

#### 初始化monitor节点

刚开始在测试阶段我们可以先创建一个monitor节点monitor-node，

```Linux
ceph-deploy new tidb2 // 监控节点不与ceph-deploy 节点在一台机器上
```

该命令会在我们刚才创建的目录下生成ceph.conf文件

```Linux
[global]
fsid = 447a06ba-6992-4875-bfe5-706ed2da4ce4
mon_initial_members = tidb2
mon_host = 192.168.130.82
auth_cluster_required = cephx
auth_service_required = cephx
auth_client_required = cephx
filestore_xattr_use_omap = true
//在集群中有多个网卡应该进行如下配置
// public network 是公共网络，负责对外提供服务的流量，
// cluster network  是私有网络，负责集群中的数据传输通信
public network = 10.10.10.0/24
cluster network = 10.10.10.0/24
```

#### 安装ceph在各个节点

使用ceph-deploy，可以一次性安装多个节点。

```Linux
ceph-deploy install tidb1 tidb2 tidb3
// 安装过程中，会按照顺序在各个机器上安装ceph
```

#### 安装monitor节点并生成key

```Linux
ceph-deploy mon create-initial
```

会在我们创建的目录my-cluster下生成如下文件。
![ceph](http://jikelearn.cn/2019-06-04-17-36-30.png)

#### 将key分发到各个机器

```Linux
ceph-deploy admin tidb1 tidb2 tidb3
ceph-deploy mgr create tidb2
```

#### 添加数据盘

在ceph中，添加数据盘有两种方式，一种是将数据盘直接做成osd，另外一种是利用文件夹的方式。

##### 文件夹方式osd

用文件夹的方式来创建osd，在各个节点创建目录文件夹。

```Linux
// 我们创建9个osd，则在每个机器上都创建三个这样的目录文件
mkdir -p /media/data1
mkdir -p /media/data2
mkdir -p /media/data3

// 使用命令创建osd
ceph-deploy osd prepare tidb1:/media/data1/ tidb1:/media/data2/ tidb1:/media/data3/  tidb2:/media/data1/ tidb2:/media/data2/ tidb2:/media/data3/
tidb3:/media/data1/ tidb3:/media/data2/ tidb3:/media/data3/

// 创建完毕后 激活osd
ceph-deploy osd activate tidb1:/media/data1/ tidb1:/media/data2/ tidb1:/media/data3/  tidb2:/media/data1/ tidb2:/media/data2/ tidb2:/media/data3/
tidb3:/media/data1/ tidb3:/media/data2/ tidb3:/media/data3/
```

##### 磁盘直接做成osd

如果磁盘已经在电脑上进行挂载，需要将挂载进行取消，否则下面命令会执行失败。

```Linux
//在tidb1上执行如下命令
ceph-deploy osd create --data /dev/sda  tidb1
ceph-deploy osd create --data /dev/sdd  tidb1
ceph-deploy osd create --data /dev/sdc  tidb1

ceph-deploy osd create --data /dev/sda  tidb2
ceph-deploy osd create --data /dev/sdb  tidb2
ceph-deploy osd create --data /dev/sdc  tidb2

ceph-deploy osd create --data /dev/sda  tidb3
ceph-deploy osd create --data /dev/sdb  tidb3
ceph-deploy osd create --data /dev/sdc  tidb3
```

#### 检查安装结果

安装完毕后需要检查下状态是否成功的安装，两种方式进行检查

```Linux
1. ceph health 
HEALTH_OK
2. ceph -s
    cluster 447a06ba-6992-4875-bfe5-706ed2da4ce4
     health HEALTH_OK
     monmap e1: 1 mons at {tidb2=192.168.130.82:6789/0}
            election epoch 9, quorum 0 tidb2
     osdmap e401: 9 osds: 9 up, 9 in
            flags sortbitwise,require_jewel_osds
      pgmap v1939720: 704 pgs, 10 pools, 3408 GB data, 13487 kobjects
            10365 GB used, 56635 GB / 67000 GB avail
                 703 active+clean
                   1 active+clean+scrubbing+deep
```

显示是HEALTH_OK代表已经成功的安装。

### 扩展集群

为了保证集群的高可用性，我们需要部署成分布式的结构，但由于现在机器只有三台，则在测试环境中暂时使用单机部署，等容量提高再使用分布式。

#### 提高mon节点

```Linux
// 创建两个节点
ceph-deploy mds create tidb3 tidb4

//将创建的节点加入到集群中。
ceph-deploy mon add tidb3 tidb4

// 查看节点部署情况，状态显示内容 mons 数量为3
ceph -s

// 在查看monitor节点的状态
ceph quorum_status --format json-pretty

ceph-deploy mgr create tidb3 node4
```

#### 提高磁盘的容量

当我们空间的容量不足时，需要进行osd的扩容，创建方式跟前面的osd创建使用一样。

### 使用S3对象存储

ceph提供了对象存储的方案，有S3/Swift存储功能。公司使用的S3方案，方便后期转移到亚马逊云服务或者阿里云云服务。

#### 创建rgw网关

```Linux
//官网也放在tidb2接口上
cep-deploy rgw create tidb2
// rgw 默认是 80端口 配置文件是这样的。如果后期需要做成负载均衡ceph也提供了ngnix的方案
[client]
rgw frontends = civetweb port=80
```

访问http:刚才节点的ip：80 有如下结果代表创建成功。

```Xml
<?xml version="1.0" encoding="UTF-8"?>
<ListAllMyBucketsResult xmlns="http://s3.amazonaws.com/doc/2006-03-01/">
  <Owner>
    <ID>anonymous</ID>
    <DisplayName></DisplayName>
  </Owner>
    <Buckets>
  </Buckets>
</ListAllMyBucketsResult>
```

#### 使用S3

在使用S3之前，需要创建一个用户。

```Linux
sudo radosgw-admin user create --uid="testuser" --display-name="First User"

会有如下内容：
{
        "user_id": "testuser",
        "display_name": "First User",
        "email": "",
        "suspended": 0,
        "max_buckets": 1000,
        "subusers": [],
        "keys": [{
                "user": "testuser",
                "access_key": "I0PJDPCIYZ665MW88W9R",
                "secret_key": "dxaXZ8U90SXydYzyS5ivamEP20hkLSUViiaR+ZDA"
        }],
        "swift_keys": [],
        "caps": [],
        "op_mask": "read, write, delete",
        "default_placement": "",
        "placement_tags": [],
        "bucket_quota": {
                "enabled": false,
                "max_size_kb": -1,
                "max_objects": -1
        },
        "user_quota": {
                "enabled": false,
                "max_size_kb": -1,
                "max_objects": -1
        },
        "temp_url_keys": []
}

```

创建好之后，可以使用官方提供的例子进行测试，检测S3是否能成功的被访问。

1. 安装python的s3包
admin
admin
admin python-boto
admin

2. 写测试脚本

注意里面的秘钥，ip等内容的写入

```Python
import boto
import boto.s3.connection

access_key = 'IWSAM9Z527Y1ABH0PZ6P'
secret_key = '189jZ3WJ3OixfwYkHuXSm4uoqsytRr9mkwThE6Qz'
conn = boto.connect_s3(
        aws_access_key_id = access_key,
        aws_secret_access_key = secret_key,
        host = '192.168.130.82',port=7480,
        is_secure = False, calling_format = boto.s3.connection.OrdinaryCallingFormat(),
        )

bucket = conn.create_bucket('admin')
for bucket in conn.get_all_buckets():
        print "{name}".format(
                name = bucket.name,
                created = bucket.creation_date,
        )
```

1. 执行脚本测试

```Linux
Python test.py
```

打印出对应的桶名字，即代表成功。

## 总结

本文主要从ceph安装步骤走起，讲解在安装过程中注意的问题，一方面自己记录这些内容方便后期查询，也让其他朋友有个可参考的方案。
