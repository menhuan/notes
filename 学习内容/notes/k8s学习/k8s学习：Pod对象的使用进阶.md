# Pod的进阶内容

介绍几种特殊Volume，这个Volume 并不是我们所说保存数据的磁盘。而是投射数据卷。

## Volume

特殊的Volume,翻译为投射数据卷。**这些特殊的Volume**是为了帮助容器提供预先定义好的数据。

现在kubernets 现在支持四种模式。

1. Secret
2. ConfigMap
3. Downward API
4. ServiceAccountToken

### Secret

Secret是帮助把Pod想要访问的数据存放到Etcd中，然后通过容器挂载这些特殊的Volume方式，访问到这些Secret保存的数据。

```Linux

apiVersion: v1
kind: Pod
metadata:
  name: test-projected-volume 
spec:
  containers:
  - name: test-secret-volume
    image: busybox
    args:
    - sleep
    - "86400"
    volumeMounts:
    - name: mysql-cred
      mountPath: "/projected-volume"
      readOnly: true
  volumes:
  - name: mysql-cred
    // 设置的是projected 类型，下面的这些参数与我们在Java程序里面设置的配置文件参数类似
    projected:
      sources:
      - secret:
          name: user
      - secret:
          name: pass

```

#### 创建Secret对象

创建Secret对象有两种方式

##### 命令创建

cat ./username.txtadmin
admin
然后利用上面文件创建secret对象。
kubectl create secret generic user --form-file=./username.txt

上面的Pod文件可以读取到创建的secret内容。

##### Yaml文件创建

```Linux
apiVersion: v1
kind: Secret
metadata:
  name: mysecret
type: Opaque
data:
  user: YWRtaW4=
  pass: MWYyZDFlMmU2N2Rm

```

上面的user ,pass都是经过base64位编码，可以使用shell命令编码。

```Linux
echo -n 'admin' | base64
```

### ConfigMap

ConfigMap 需要的内容是不需要记性加密的，创建方式与Secret类型。

### Downward Api

作用是让Pod里的容器能够直接获取到这个Pod Api对象本身的信息。但是能获取到的一定是Pod容器启动之前能确定下来的内容。

### Service Account

Service Account 是kubernets 系统内置的一种服务账户，是kubernets 进行权限分配的对象。

该对象内容会保存在一个特殊的Secret对象里，这个特殊的对象就是ServiceAccountToken， 运行在kubernetes 集群上的应用，都必须使用ServiceAccountToken 里保存的 授权信息，也就是Token才可以合法的访问Api Service.

kubernetes 其实 在每个Pod创建的时候会默认加上该Token 信息， 我们可以通过

```Linux
kubectl describe pod 名字 来查看
```

这种自动把kubernetes客户端以容器的方式挂载集群里面，然后使用default service Account自动授权的方式，被称作 InClusterConfig。

## Pod的重要配置：容器的检查与恢复机制

为Pod设置一个健康检查的探针Probe.然后Kublelet 就会根据Probe的返回值决定这个容器的状态作为依据，在生产环境中保证应用健康存储的重要手段。

### 恢复机制

当健康机制检查到容器出现异常情况，会被kubernetes重启，但实际上是重新创建了容器。

这个就是kubernets里面的Pod恢复机制。 默认是Always,恢复过程都是发生在当前节点上，不会跑到其他节点上，宿主机宕机了，这个Pod也会不迁移到其他机器，则我们需要使用Deployment 进行管理。

重启策略有两个设计原理。

1. Pod的restartPolicy 指定策略重启异常的机器，那么这个Pod机就会一直保持Running的状态，并进行容器的重启，否则Pod会进入Failed状态。
2. 包含多个容器的Pod，包含的所有容器都进入异常状态后，Pod才会进入Failed状态。READY 字段显示正常容器的个数。

## PodPreset

PodPreset 里面定义的内容会使用selector.matchLabels下面的定义的名字，去寻找这样的模板内容，然后当起启动的时候自动填充到对应的pod里面。

只会在Pod被创建之前追加到这个对象身上，而不会影响任何Pod控制器的定义。
