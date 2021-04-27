# 网络插件k8s

k8s中为什么只有soft multi-tenancy?

k8s中的网络模型只关注容器间的连通，不关心容器之间的网络隔离。

## k8s中的网络插件

在k8s中进行网络隔离采用一个专门的API对象来描述的，是NetworkPolicy。

```yml
apiVersion: networking.k8s.io/v1
kind: NetworkPolicy
metadata:
  name: test-network-policy
  namespace: default
spec:
#如果将podSelector：{} 代表该NetworkPolicy作用于当下所有的Pod
  podSelector:
    matchLabels:
      role: db  # 指定的命名空间角色
  policyTypes:
  - Ingress # 定义的两种规则 流入与流出
  - Egress
  ingress:
  - from:
    - ipBlock:
        cidr: 172.17.0.0/16
        except:
        - 172.17.1.0/24
    - namespaceSelector:
        matchLabels:
          project: myproject
    - podSelector:
        matchLabels:
          role: frontend
    ports:
    - protocol: TCP
      port: 6379
  egress:
  - to:
    - ipBlock:
        cidr: 10.0.0.0/24
    ports:
    - protocol: TCP
      port: 5978
```

NetworkPolicy下面定义的规则代表是白名单，在属于规则下的则可以操作。

在上面定义的规则总共分为以下几种情况。
1. 隔离规则只针对默认Namespace下指定的pod有效，限制的请求包含流入与流出。
2. k8s会拒绝任何访问被隔离Pod的请求，除非这些请求是在白名单里面。
3. 并列规则下位置并列带有 - 代表是或的关系，如果不带有-代表是and的关系

```yml 
  
or关系
  ...
  ingress:
  - from:
    - namespaceSelector:
        matchLabels:
          user: alice
    - podSelector:
        matchLabels:
          role: client
  ...

and关系
...
  ingress:
  - from:
    - namespaceSelector:
        matchLabels:
          user: alice
      podSelector:
        matchLabels:
          role: client
  ...

```

想让NetworkPolict 在生产环境产生作用，那么CNI网络插件必须支持k8s的 NetworkPolicy。

已经实现NetworkPolicy的网络插件包含Calico,Weave和kube-router。

一键安装Flannel+Calico[链接](https://docs.projectcalico.org/v3.2/getting-started/kubernetes/installation/flannel)

## 理解这个项目

```yml

apiVersion: extensions/v1beta1
kind: NetworkPolicy
metadata:
  name: test-network-policy
  namespace: default
spec:
  podSelector:
    matchLabels:
      role: db #拦截这个空间内的携带了role=db标签的pod 下面规定是流入白名单
  ingress:
   - from:
     - namespaceSelector:
         matchLabels:
           project: myproject
     - podSelector:
         matchLabels:
           role: frontend
     ports:
       - protocol: tcp
         port: 6379

```

kubernetes的网络插件会使用其定义在宿主机上生成iptables规则，对pod的隔离就是再主机上生成NetworkPolict对应的iptable规则。

### 第二步

生成上述规则后，网路插件还需要将隔离的Pod请求转发到规则上进行匹配，请求匹配通不过，则该请求被拒绝。

CNI网络插件中，设置两组iptable规则实现。

1. 第一组负责对被合理POD的访问请求，在内部实现转发FORWARD.

### iptables知识

iptables 是一个操作Linux内核Netfilter子系统对策界面，挡在网卡和用户态进程之间的防火墙。
![2019-08-01-23-53-27](http://jikelearn.cn/2019-08-01-23-53-27.png)

在IP包的进出通道上，有被称为链的检查点。
![2019-08-01-23-54-41](http://jikelearn.cn/2019-08-01-23-54-41.png)

IP包经过路由后决定下一步的确定，两种情况进行处理。

1. 继续本地处理
2. 被转发到其他目的地

第一种去向流向上层协议栈，然后通过传输层进入用户空间，交给用户处理，用户进程会通过本地发出返回的IP包，再进入流出路径。

第二种去向，IP包不进入传输层，而是继续在网络层流动，转发路径汇总设置一个FORWARD的检查点，经过之后就会来到流出路径，**转发的IP包由于目的已经确认**，则不需要再进入到流出路径中，直接到POSTROUTING检查点。

POSTROUTING的作用就会汇聚在一起的最后检查点。

### NerworkPolicy

iptables -A FORWARD -d $podIP -m physdev --physdev-is-bridged -j KUBE-POD-SPECIFIC-FW-CHAIN
iptables -A FORWARD -d $podIP -j KUBE-POD-SPECIFIC-FW-CHAIN
...

第一条FORWARD链拦截就是一种特殊情况，同一台宿主机上容器之间经过CNI网桥进行通信的流入数据包

第二条拦截是普遍的情况，容器跨主通信，都是经过路由转发FORWARD检查点来的。

最后都调到NetworkPolicy设置的第二组规则。让其进行匹配规则
