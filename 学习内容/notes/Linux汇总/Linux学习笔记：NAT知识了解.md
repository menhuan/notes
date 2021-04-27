# NAT

可能导致网络延迟的因素还与网络地址转换的问题存在，这就是NAT。

## NAT原理

NAT技术可以重写IP数据包的源IP或者目的IP.被普遍的用来解决公网IP地址短缺的问题。

网络中的多台主机，通过共享一个公网IP地址，来访问外面的资源，屏蔽内网网络为局域网提供完全隔离。

### NAT的三种分类

- 静态NAT，既内网IP与公网IP是一对一的永久映射关系。
- 动态NAT,内网IP与从公网IP池里面选择一个进行映射。
- 网络地址端口转换NAPT，把内网IP映射到公网IP的不同端口上，多个内网IP共享一个公网IP地址。

### NAPT三种分类

- 源地址转换SNAT,目的地址不变，**替换源IP或者端口**。多用于内网IP共享一个公网IP，访问外网资源。
- 目的地址转换DNAT，保持源IP不变，**替换目的IP或者源端口**，
- 双向地址转换，同时使用SNAT,DNAT。接收到外部网络包执行DNAT,目标ip地址转换为内网ip。发送网络包执行SNAT,源ip替换为外部ip。

![2019-03-06-22-29-58](http://jikelearn.cn/2019-03-06-22-29-58.png)

## iptables 与NAT

网络数据包是通过Netfilter的工作流向。
![2019-03-06-22-36-14](http://jikelearn.cn/2019-03-06-22-36-14.png)

绿色背景的方框表示 **表**，用来管理链。Linux支持 filter(过滤),nat(NAT),mangle(用于修改分组数据),raw(用于原始数据包)四种模式。

白色背景表示链，用来管理具体的iptables规则。每个表中有多条链。

- filter中，内置INPUT,OUTPUT和FORWARD链。
- nat表，内置PREROUTING,POSTROUTING,OUTPUT等。
  
iptables所有规则都会放到这些表和链中，并按照图中的顺序先后执行。

nat内置三条链。

- PREROUTING 用于路由判断前所执行的规则，比如对接受到的数据包进行DNAT.
- POSTOUTING，用于路由判断后所执行的规则，比如对发送活转发的数据包进行SNAT或者MASQUERADE.
- output,类似于prerouting,但只处理本机发出去的包。

## 三种配置

### SNAT

```Linux
给子网配置SNAT
 iptables -t nat -A POSTROUTING -s 192.168.0.0/16 -j MASQUERADE
给具体的地址配置SNAT
 iptables -t nat -A POSTROUTING -s 192.168.130.55 -j SNAT --to-source 要转换的IP地址

```

### DNAT

```Linux
 iptables -t nat -A PREROUTING -d 源ip地址 -j DNAT --to-destination 192.168.130.50  目的ip地址

```

### 双向地址转换

同时添加以上两个规则即可

### 注意需要开起IP转发功能

```Linux
sysctl net.ipv4.ip_forwad


手动开起 
sysctl -w net.ipv4.ip_forward=1

写入到配置文件中/etc/sysctl.conf中
cat /etc/sysctl.conf |grep ip_forward
```

