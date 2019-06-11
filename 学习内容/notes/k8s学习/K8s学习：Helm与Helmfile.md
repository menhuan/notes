# Helm与Helmfile

helm 是kubernetes 包管理工具，引用chat的概念，有效对Kuberbetes上的应用部署进行了优化，采用模板方式，达到跟使用apt-get一样简单使用。

已经默认服务端已安装相关内容。前面在k8s中配置好命名空间，这样方便下面命名空间的使用

## Helm安装

### linux系统下

1. 访问下载https://github.com/helm/helm/releases，下载合适的版本
2. 解压后，将其可执行文件放到/usr/local/bin目录下
3. 初始化账户 helm init --service-account default --tiller-namespace dev
4. helm init --client-only --stable-repo-url http://mirror.azure.cn/kubernetes/charts
5. 设置默认的namespace空间

```Linux
如果 bash 是zsh
vim ～/.zshrc
添加 export TILLER_NAMESPACE=dev
如果是默认bash
vim ~/.bash
添加 export TILLER_NAMESPACE=dev
添加内容是一样的

source ~/.zshrc 生效
echo $TILLER_NAMESPACE 查看输出是否是我们设置的值
```

## 安装helmfile

1. 访问https://github.com/roboll/helmfile/releases 下载
2. 解压将其拷贝到/usr/local/bin/目录下，注意重名为helfile

## 安装helm-diff

1. 访问 https://github.com/databus23/helm-diff 根据README安装

安装教程如上:

## 需要用的命令内容

在chart需要其他的包支持，当本地还没有chart包首先需要build 相关依赖。

```Linux
1. helm dependency build
2. helm package . 打包 --save=false 加这个参数代表 不讲打包的程序加入默认local仓库中。
```

### 创建一个本地仓库

创建一个本地仓库，方便大家对修改的chart文件进行测试。

```Linux
1. mkdir ~/Desktop/disk/k8s/local-repo
2. nohup helm serve --address 192.168.111.12:8888 --repo-path ~/Desktop/disk/k8s/local-repo
3. helm repo add local-repo http://192.168.111.12:8888
4. helm repo list 查看本地仓库是否加入成功
```

仓库创建成功后，怎么把我们的包加入到创建的仓库中呢？

```Linux
1. helm  search  名字 搜索对应的打包工具
2. 去对应的 chart 下面进行打包 helm package . --save=false 
3. 复制打包好的内容到刚才仓库下面
4. 到仓库目录下  
5. helm repo index --url=http://192.168.111.12:8880 .
6. helm repo update
7. helm search 名字 查看是否在本地制定的仓库中已经存在
```

### 使用helmfile 更新

chart 修改完毕后，需要更新deploy 配置文件，在这里使用的是helmfile.

helmfile 常使用命令

```Linux

1. helmfile diff 比较与上一个版本有什么区别的代码
2. helmfile apply  进行更新程序
3. 如果程序更新后有多余出来的环境变量，需要将原先的helmfile内容删除
4. helm list 查看helm 集合
5. 删除某个helm 内容 helm delete --purge helm名字 删除后重新执行即可
```
