# 存储状态

存储机制主要是利用Persistent Volume Claim 功能 简称PVC.

Kubernetes项目引入了一组PVC和PV的API对象，大大降低用户声明和使用持久化Volume门槛。

## 定义PVC文件

```yaml
kind: PersistentVolumeClaim
apiVersion: v1
metadata:
  name: pv-claim
spec:
  accessModes:
  - ReadWriteOnce
  resources:
    requests:
      storage: 1Gi
```

在PVC文件中只需要描述性的属性和定义即可。

- storage: 1Gi,代表的是需要的存储。
- accessModes:ReadWriteOnce 表示挂载方式是可读写，并且只能被挂载在一个节点上而非被多个节点共享。

```accessModes
ReadWriteOnce – the volume can be mounted as read-write by a single node
ReadOnlyMany – the volume can be mounted read-only by many nodes
ReadWriteMany – the volume can be mounted as read-write by many nodes

三种方式
```

详细介绍可以参考K8s文档点击[详细列表](https://kubernetes.io/docs/concepts/storage/persistent-volumes/#access-modes)查看。

PVC内容只需要申请，那么具体的Volume是从哪里来的呢？是有运维人员维护的PV对象来分配。

## PV内容

```yaml
kind: PersistentVolume
apiVersion: v1
metadata:
  name: pv-volume
  labels:
    type: local
spec:
  capacity:
    storage: 10Gi
  rbd:
    monitors:
    - '10.16.154.78:6789'
    - '10.16.154.82:6789'
    - '10.16.154.83:6789'
    pool: kube
    image: foo
    fsType: ext4
    readOnly: true
    user: admin
    keyring: /etc/ceph/keyring
    imageformat: "2"
    imagefeatures: "layering"

```

PV与PVC的设计类似于接口与实现的思想开发者关注于使用接口PVC，运维人员给具体的实现PV.

解耦避免给开发者暴露太多的存储系统内容而带来隐患。

## StatefulSet存储状态

PVC使用的名字是pvc的名字-StatefulSet的名字-编号方式命名的，都处于Bound状态。

当StatefulSet 管理的Pod被删除之后，他们创建的PVC与PV并没有被删除，而是依旧保存在存储服务器里面。

并且Pod被删除，StatefulSet还会重新 创建一个新的Pod但名字还是原先名字，，纠正该情况不一致的状态，新的Pod还是继续挂载到原先的Volume文件上，获取保存的数据。

通过这种方式Kubernetes的StatefulSet实现了对应用存储状态的管理。

### 管理工作原理

1. StatefulSet的控制器直接管理Pod，区分不同的实例方式，Pod的名字里面加上实现约定好的编号。
2. Kubernetes通过Headless Service 为这些有编号的Pod，在DNS服务器中生成带有同样编号的DNS记录。
3. StatefulSet 还为每一个Pod分配并创建一个同样编号的PVC，保证每一个Pod拥有一个独立的Volume。



