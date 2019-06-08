# 作业副本与水平扩展

在控制器模式中Deployment实现一个经典的功能，**Pod的水平扩展/收缩**。

在Depolyment中采用的是滚动更新来升级现有的容器。采用的API对象就是ReplicaSet。

## ReplicaSet

文件yaml对象是

``` yaml
apiVersion: apps/v1
kind: ReplicaSet
metadata:
  name: nginx-set
  labels:
    app: nginx
spec:
  replicas: 3
  selector:
    matchLabels:
      app: nginx
  template:
    metadata:
      labels:
        app: nginx
    spec:
      containers:
      - name: nginx
        image: nginx:1.7.9
```

主要由副本数目与一个Pod模板组成。

Deployment对象实际操作的是ReplicaSet对象，而不是Pod对象。则在Deployment管理的Pod中 ownerReference 是ReplicaSet.

三个副本程序。
![2019-06-09-01-33-27](http://jikelearn.cn/2019-06-09-01-33-27.png)

这也是Deployment 只允许容器restartPolicy=Always的主要原因，在容器保证自己始终是Running状态的前提下。

### 水平扩展

水平扩展则是将spec.replicas 的个数进行新增即可，也可以去修改yaml文件或者使用命令行的方式实现。

```Linux
kubectl scale deployment nginx-deployment --replicas=4
deployment.apps/nginx-deployment scaled

修改yaml文件
kubectl apply file.yaml 生效

```

### 滚动更新

![2019-06-09-01-38-29](http://jikelearn.cn/2019-06-09-01-38-29.png)

#### 字段内容解释

如图可看到有四个字段状态内容。

1. READY: 前面代表实际状态，当前已经处于Running状态Pod的个数，后面代表用户期待的副本个数。
2. UP-TO-DATE:当前已经处于最新版本的Pod个数，最新版本指Pod中的Spec部分与Pod模板定义内容完全一致。
3. AVAILABLE: 当前可用的Pod个数，Running状态，最新的版本，处于Ready状态Pod个数。

如果想实时查看Deployment对象的状态变化,用如下命令。

```Linux
kubectl rollout statys deploment/名字。
```

查看ReplicaSet

```Linx
kubectl get rs
```

#### 修改

修改Deployment 的Pod模板有好多方法，可以使用edit命令直接编辑。

```Linux
kubectl edit 名字
```

edit 本质是将API对象内容下载到了本地文件，修改后再提交上去。

#### 提交流程

提交后：

1. Deployment Controller 会使用新的Pod模板，创建新的ReplicaSet 初始副本数量则为0.
2. 先在新的Deployment管理Pod上副本进行增加，然后旧的副本数量减少，循环完成最后的更新。

交替逐一升级过程就是滚动更新。

#### 出现问题

如果滚动更新出现问题，那么滚动更新就会停止，然后需要人为的介入。

为了保证服务的连续性，会保证一定的比例Pod处于离线状态。

![新旧流程更新图](http://jikelearn.cn/2019-06-09-01-53-36.png)

##### 回滚原先的版本。

```Linux

kubectl rollout undo 名字 即可。

```

##### 恢复到指定版本

```Linux
kubectl rollout history 名字 --version=数字
```

##### 多余的rs，只生成一个ReplicaSet

每次滚动更新都会生成新的ReplicaSet，

在更新Deployment之前 先执行如下命令。

```Linux
kubectl rollout pause 名字。
```

让deployment 进入一个暂停状态，然后我们可以使用编辑命令去编辑Deployment了。

暂停恢复过来。

```Linux
kubectl rollout resume 名字
```

控制ReplicaSet数量，

设置spec.revisionHistoryLimit保留的历史版本，设置为0就不能做回滚操作。