# Service,DNS，服务发现

在k8s中被selector选中的Pod被称为Service的Endpoints，可以使用kubectl get ep查看，但是该命令只能查看处于Running状态且readinessProbe检查通过的Pod，才会出现在Endpoints列表里面。

当一个Pod出现问题的时候，会自动把他从Service中去掉。

VIP地址是kubernetes 自动为Service 分配的，这种方式我们称为ClusterIP模式，并且可以提供Round Robin方式的负载均衡。

## Service的工作

实际上是通过kube-proxy组件与iptables来共同实现的。

1. 提交给kubernetes,kube-proxy通过Service的Informer感知到Service对象的添加，就在宿主机上创建一条iptable规则。
2. 该规则只是让其跳转到另外一条KUBE-SVC-~这样的iptables链上处理，就是给Service设置一个固定的入口位置。并没有真正的网络设备，所以ping是无法连通的
3. 我们跳转到的KUBE-SVC这样的规则是一组规则的集合，当然也是一组随机模式的iptables链。
4. 代理的是我们目的地的Pod,并且也是Service实现负载均衡的位置。
5. iptablers规则的匹配是从上到下逐条进行的。保证每条被选中的概率一直，可以将probability字段的值设置为1/3,1/2,1三种模式。
6. 在进入到PRESOUTING检查点之前，还有DNAT规则需要处理，该规则是将流入IP包的目的地与端口修改为所指定的新的目的地与端口则是Pod被代理的IP地址与端口。

## IPVS模式的Service

一直以来 基于iptables的Service的实现都是制约Kubernetes项目承载更多量级的Pod的主要障碍。

IPVS模式的Service就是解决该问题的一个有效的办法。

