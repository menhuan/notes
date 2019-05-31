# k8s 初始使用初探

k8s的使用，建立在已经对Docker有了一定的理解的基础上，公司推进k8s的使用，则自己开始进行相关命令初探的了解。

## k8s初探

k8s 跟docker有很大的不同是，很多内容都是定义在yaml文件中，然后使用kubectl create -f  创建的yaml文件来启动。

```yaml
# pvc 内容
apiVersion: v1
kind: PersistentVolumeClaim
metadata:
  name: mysql-pvc
spec:
  accessModes:
  - ReadWriteOnce
  resources:
    requests:
      storage: 2Gi
  volumeMode: Filesystem

# deployment
apiVersion: apps/v1
kind: Deployment
metadata:
  name: mysql
spec:
  selector:
    matchLabels:
      app: mysql-pod
  template:
    metadata:
      labels:
        app: mysql-pod
    spec:
      containers:
      - name: mysql
        image: docker.infervision.com/library/mysql:5.6
        env:
        - name: MYSQL_ROOT_PASSWORD
          value: fengruiqi
        ports:
        - containerPort: 3306
          name: mysql
        volumeMounts:
        - name: mysql-persistent-storage
          mountPath: /var/lib/mysql
      volumes:
        - name: mysql-persistent-storage
          persistentVolumeClaim:
            claimName: mysql-pvc

# service
apiVersion: v1
kind: Service
metadata:
  name: mysql-svc
  annotations:
    metallb.universe.tf/address-pool: default
    metallb.universe.tf/allow-shared-ip: "true"
spec:
  loadBalancerIP: 192.168.130.19
  ports:
  - port: 3306
  selector:
    app: mysql
  type: LoadBalancer

kubectl create -f mysql-service.yaml

执行这个命令就能将其启动

```

从图中可以看到 mysql相关的服务已经启动还有service 项目启动。
![2019-05-30-23-19-21](http://jikelearn.cn/2019-05-30-23-19-21.png)

图中可以看到有一个kind 被设置成Deployment,是一个定义多副本应用的对象，多副本代表多个副本Pod的对象。

**Pod在发生更新的时候采用的是滚动更新**。

Pod是什么呢？ 可以简单的理解为是k8s中的一个应用，一个应用是由多个容器组成的。

我们使用Deployment管理Pod，在k8s中被称为**控制器模式**。

每个template都存在metadata字段，该字段代表API对象的标识，在k8s通过这个标识来寻找到这个对象，是在label下进行控制的，通过label下面的字段找出自己关心的控制对象。

既然要过滤那么过滤规则是什么呢？

答曰：sepc.selector.matchLabels 该字段。 也可以称为Label Selector。

与metadata同等级的字段还有Annotations，但该字段内容**大部分**被k8s自动加在API对象中的。

## 流程查看

### 创建一个应用

```linux
-n dev 代表有权限的命名空间， 如果刚开始没有使用权限控制则可以不加

kubectl -n dev create -f mysql-service.yaml 文件启动
```

### 查看应用的状态

```Linux
kubectl -n dev get pods  查看所有的pods 内容

```

![2019-05-30-23-19-21](http://jikelearn.cn/2019-05-30-23-19-21.png)

从图中可以看到，上面有几个STATUS 状态处于RUNING状态，当然也有其他失败的状态没有启动起来的，我们先看Running成功的状态内容。

### 查看描述内容

启动后，我们查看应用的描述内容，成功或者失败了查看EVENT即可知道失败的问题出现在哪里。

```Linux

kubectl -n dev describe pod + 名字 即可查看，部分内容如下

```

![2019-05-30-23-39-45](http://jikelearn.cn/2019-05-30-23-39-45.png)

### yaml文件发生改变

当yaml文件发生改变，我们可以使用如下命令来更新。

```yaml
kubectl -n dev replace -f mysql-service.yaml 文件
```

新版本的k8s中推荐使用如下命令

```yaml
kubectl -n dev apply -f mysql-service.yaml 文件进行更新内容。当然第一次使用的时候也推荐使用该方式。
```

在k8s中，用户不必关心当前的操作是创建还是更新，始终使用命令 kubectl apply。

## 存储

k8s中怎么进行数据的挂载呢？ 在yaml文件中提供了volumes参数进行设置。

```yaml

挂载的目录文件就是再宿主机上。
volumes：
  - name: mysql-con
    hosPath:
        path: /var/data
```

然后使用 apply -f yaml文件尽心更新。