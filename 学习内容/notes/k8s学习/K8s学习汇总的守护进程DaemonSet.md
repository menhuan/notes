# 守护进程DaemonSet

在集群中运行一个Daemon Pod .

- 运行在集群中的每一个节点。
- 每个节点上只有一个实例。
- 新节点加入集群中，Pod会自动的在新节点创建，纠结点删除后，上面的Pod也会相应的被收掉。

常用的案例内容：

1. 网络插件Agent组件，必须运行在每一个节点，用来处理网络问题。
2. 存储组件，挂载远程存储目录。
3. 监控与日志组，用来在每个节点上收集日志信息。

DaemonSet 开始运行的时机，比整个Kubernetes 集群出现的时机都很早。

## 怎么确保每个Node 上有且只有一个被管理的Pod

DaemonSet 从Etcd中获取所有Node 列表，遍历所有的Node信息检查是不是携带某个标签的Pod运行。

涉及创建，删除，正好有一个三种情况。

使用nodeAffinity 来创建一个新的Pod。

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: with-node-affinity
spec:
## Pod跟调度相关的字段
  affinity:  
    nodeAffinity:
    ## 每次调度的时候考虑，也可以不考虑在某些情况下
      requiredDuringSchedulingIgnoredDuringExecution:
        nodeSelectorTerms:
        - matchExpressions:
        ## 设置 创建Pod的时候绑定在values指定的名字上
          - key: metadata.name
            operator: In
            values:
            - node-geektime

```

在创建Pod的时候，自动在这个Pod的API对象上加上这么个参数。

不修改用户提交的Yaml文件，而是在向Kubernetes发起请求之前，直接修改根据模板生成的Pod对象。

还会自动加上另外一个调度的字段内容，tolerations。容忍某些Node的污点。

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: with-toleration
spec:
  tolerations:
  - key: node.kubernetes.io/unschedulable
    operator: Exists
    effect: NoSchedule

代表容忍 被标记为unschedulabel 污点的Node

```

总结的说，DaemonSet其实是一个简单的控制器，在循环控制中，只需要遍历所有节点，然后又根据Pod管理的情况进行创建，删除，不过在创建的Pod的时候还会自动加入一个nodeAffinity的调度字段内容，保证只在指定的节点上启动，同时再加入另外一个调度字段内容Toleration，从而忽略节点的污点内容。


```Linux
kubectl rollout status 名字 查看滚动更新的状态
kubectl rollout history daemonset 名字 查看历史 记住需要在升级命令后面增加-record参数。
```

DaemonSet 的版本维护:
1. ControllerRevision 专门记录Controller 对象的版本。
通过这个查看版本内容。

```Linux
kubectl get controllerrevision -n kube-system -l name=fluentd-elasticsearch
NAME                               CONTROLLER                             REVISION   AGE
fluentd-elasticsearch-64dc6799c9   daemonset.apps/fluentd-elasticsearch   2          1h

//回滚到指定的字段上，回滚Revision 并不从2 退回到1 ，而是增加到3 新的被创建。
kubectl rollout undo daemonset fluentd-elasticsearch --to-revision=1 -n kube-system
daemonset.extensions/fluentd-elasticsearch rolled back

```