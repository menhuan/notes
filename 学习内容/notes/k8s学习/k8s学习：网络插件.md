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
      role: db
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