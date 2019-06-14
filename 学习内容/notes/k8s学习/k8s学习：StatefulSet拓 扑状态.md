# StatefulSet 拓扑状态

用来保证程序的有状态特性而创建出来的编排模式。

## 有状态

那么什么是有状态呢？

比如分布式应用，多个实例之间可能存在依赖关系，如：主从关系，主备关系。数据存储类应用，多个实例，实例被创建出来之后，与数据盘绑定失败，会导致应用启动失败。

### 抽象了两种情况。

1. 拓扑状态：应用多个实例之间关系不对等，必须按照某种顺序启动才行。
2. 存储状态：多个实例绑定不同的存储数据，一个数据库实例对应多个存储实例内容。

StatefulSet的核心功能就是通过某种方式记录这种状态，然后在Pod被重新创建时，能够为Pod恢复这些状态。

## Headless Service

Service是Kubernetes项目中用来将一组Pod暴露给外界访问的一种机制。能访问到Service，那么就能访问到到这一组汇总的Pod。

### Service 怎么被访问的呢

两种方式 VIP方式，另外一种是DNS方式。

### VIP(Vitrual ip)

虚拟IP方式，比如虚拟IP是10.0.23.1,通过这个虚拟IP既可以访问到代理的Pod上。

### Service DNS方式

访问my.svc.my-namespace.svc.cluster.local DNS记录则可以访问到代理Pod内容。

1. Normal Service: 该方式会解析出IP,正式对应的VIP，后面流程则是跟VIP方式一样。
2. Headless Service: 该方式直接解析到的就是某一个Pod的Ip地址，不需要分配一个VIP，直接以DNS记录解析出代理Pod的IP地址。

### 标准文件

```yaml

apiVersion: v1
kind: Service
metadata:
  name: nginx
  labels:
    app: nginx
spec:
  ports:
  - port: 80
    name: web
  clusterIP: None  //注意这里
  selector:
      app: nginx
```

Headless Service 主要是clusterIP 字段内容是None，不会被分配一个VIP而是用DNS记录来暴露出代理的Pod。

### 如何维持Pod的拓扑状态

在Headless Service 中会按照如下方式绑定DNS记录。

```Linux
<pod-name>.<svc-name>.<namespace>.svc.cluster.local
```

通过上面dns记录类解析到某个Pod。

通过指导一个Pod的明泽，以及Service名字，就能通过DNS记录访问到Pod的IP地址。

使用watch 实时查看启动命令

```Linux
kubectl get pods -w -l app=ngnix 实时查看创建的内容
```

把创建的Pod删除后，Kubernetes 会按照原先编号的顺序创建新的Pod。通过严格控制，StatefulSet就保证了Pod网络标识的稳定性。

通过pod名字与序号与svcname绑定， 入口就是对应的DNS记录。

## 总结

StatefulSet 特点是 对Pod按照顺序进行标号完成创建工作，通过控制循环完成。

当实际状态与期望状态不一致情况下，按照Pod编号顺序。逐一完成操作。

