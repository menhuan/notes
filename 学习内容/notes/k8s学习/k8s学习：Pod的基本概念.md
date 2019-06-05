# Pod的基本概念

Pod不是容器，知识Kubernetes项目中的最小编排单位。容器只是Pod属性里面的一个普通字段。

Pod扮演的是传统部署环境里面的虚拟机角色，使用户方便的从虚拟机环境向容器环境迁移更加的平滑。

凡是调度、网络、存储，以及安全相关的属性，基本上都属于Pod。

Linux Namespace相关的属性也是Pod级别的。

描述的是机器的整体，还不是里面运行的某个程序。

## 字段内容介绍

- NodeSelector: 供用户将Pod和Node进行绑定的字段。
- NodeName：这个字段被赋值，Kubernets项目认为其已经经过调度了。
- HostAliases：定义了Pod的hosts文件，/etc/hosts里面的内容,不能在容器里面修改，修改了Pod被删除重建，里面内容就会丢失。

NameSpace 也属于pod级别的，比如在yaml文件中定义spec.shareProcessNamespce: ture 代表pod里面的容器共享PID Namespace.

stdin：true 和tty：true 代表的是 开起shell操作 进入。相当于docker run -it操作。

### 容器级别：

- ImagePullPolicy: 定义了镜像拉取的策略。策略如果没有修改一般都是Always。每次创建Pod都重新拉取一下镜像。
- Lifecycle：容器状态发生变化时触发一系列钩子。

## Pod声明周期

状态变化主要体现在Status部分，除了Metadata和Spec之外的第三个字段，pod.status.phase 就是Pod的状态。

- Pending,意味着Pod的yaml文件提交给Kubernetes了，Api对象已经被保存到Etcd当中。可能由于某种原因调度不成功。
- Running,这个状态下Pod已经调度成功，创建成功。
- Succeeded，这个状态意味着Pod所有容器都正常运行，运行一次性任务时比较常见。
- Failed,Pod链至少有一个容器不正常运行。
- Unknown一个异常装填，意味着Pod的状态不能车系的给kubeapiserver，主从节点通信出现问题。
  
  