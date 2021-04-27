# 声明式API与编程范式

声明式API操作，对于请求是一次能处理多个写操作，并且具备Merge能力。减少了操作步骤。

## Lstio项目

Lstio是微服务项目的治理框架，结构如下：
![2019-06-19-22-31-46](http://jikelearn.cn/2019-06-19-22-31-46.png)
在每个Pod里面运行一个Envoy容器，该容器是一个高性能的C++网络代理。

以sidecar容器的方式运行在了每一个被治理的应用Pod中。

Pod里面所有容器共享一个Network Namespace 所以Envoy容器就能够通过Pod里面的iptables规则，将进出流量管控下来。

Lstio 的控制里的Pilot组件，通过对容器的API操作，进行代理配置，实现微服务治理。

每个Pod里面都安装了Envoy容器时通过使用Dynamic Admission Control 来实现用户无感的。

### Dynamic Admission Control

每一个Pod或者Api对象提交ApiServce后，总有一些初始化性质的工作要做，该初始化的操作就是Admission的功能。

但 Dynamic Admission Control 属于热插拔的Admission机制，不同重启ApiServer就可以实现的也可以叫做Initializer.

## 声明式Api的独到之处

- 只需要提交一个定义好的API对象来声明，期望的状态是什么
- 允许有多个API写端，以PATCH的方式对API进行修改，无需关心本地原始的YAML文件内容。
- Kubernetes项目可以基于对API对象的增删改查，无需外界干预的情况下，完成对实际状态和期望状态的调谐过程。

