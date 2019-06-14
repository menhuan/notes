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
```