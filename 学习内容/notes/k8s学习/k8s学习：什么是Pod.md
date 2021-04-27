# Pod

在容器中 NameSpace 做隔离，Cgroups做限制，rootfs做文件系统。

K8s中新增的Pod是什么呢？

容器的本质是进程，而K8s是操作系统。

在一个操作系统总，进程不是孤立存在，而是以进程组的方式有原则的组织在一起。进程之间相互协作，完成共同的任务。

k8s做的就是将**进程组**这个内容映射到容器技术中去。 Pod在K8s中原子调度单位，这意味着K8s项目的调度是按照Pod而非容器的资源需求进行计算的。

## Pod在k8s中的意义

Pod在kubernetes 中的意义是**容器设计模式**。

### 实现原理

Pod是一个逻辑概念，在kubernets中，还是处理宿主操作系统上Linux容器的Namespace和Cgroups而非一个Pod的边界与隔离环境。

Pod被创建出来是**一组共享了某些资源的容器**，在同一个Pod里面的所有容器，共享的是同一个Network Namespace,还可以共享同一个Volume 数据卷。

在kubernets中 Pod实现依赖了一个中间容器，叫做**Infra容器**，该容器是永远被第一个创建的，其他我们自己定义的容器则通过Network Namespace的方式与Infra容器关联起来。![2019-05-31-23-53-56](http://jikelearn.cn/2019-05-31-23-53-56.png)

Pod对于其内部的容器来说，有以下作用：

- 可以通过localhost通信。
- 网络内容跟Infra容器看到的是一致的。
- 只有一个ip地址
- 网络资源一个Pod一份，并且被该Pod里面的所有容器共享。
- Pod的声明周期跟Infra容器一致，跟用户容器无关。

则我们在一个Pod里面共享一个Volume就很方便了，将所有的Volume定义设计在Pod层级即可。

### Init Container

在所有Pod中，所有Init Container定义的容器，都会比spec.containers定义的容器先启动。多个Init Container容器的话，会按照顺序进行启动，都启动完毕退出后，用户容器才会启动。

### sidecar

容器的一种设计模式，是一个多个容器的组合操作，在一个Pod中启动一个辅助容器，帮助完成独立于主进程之外的工作。

[k8s容器的设计模式连接](https://www.usenix.org/conference/hotcloud16/workshop-program/presentation/burns)