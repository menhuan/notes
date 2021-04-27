# 基本命令的使用汇总

k8s学习上，根据现在程序流程汇总下命令，还有相关的helm文件内容。方便自己后期进行查找。

## 创建本地的客户端

我们默认服务端已经安装好k8s,需要在本地安装相关命令，链接到服务器上的k8s空间，在本地操作特定命名空间的服务。

### 安装本地命令客户端

安装kubectl使用如下命令,由于源的问题，国内环境换成阿里源，切换到root用户 用户需要在命令
上加入sudo。

1. 下载安装kubectl

```Linux
apt-get update && apt-get install -y apt-transport-https
curl https://mirrors.aliyun.com/kubernetes/apt/doc/apt-key.gpg | apt-key add -
cat <<EOF >/etc/apt/sources.list.d/kubernetes.list
deb https://mirrors.aliyun.com/kubernetes/apt/ kubernetes-xenial main
EOF
apt-get update
apt-get install -y kubelet kubeadm kubectl
```

2. 设置config

在～/.kube/ 目录下编辑 config 文件，如果不存在就创建一个。内容如下：

```Linux
apiVersion: v1
kind: Config
clusters:
- name: kubernetes
  cluster:
    certificate-authority-data: cluster内容
    server: https://192.168.130.20:6443
contexts:
- name: kubernetes-dev@kubernetes
  context:
    cluster: kubernetes
    namespace: dev  // 指定命名空间
    user: kubernetes-dev
current-context: kubernetes-dev@kubernetes
users:
- name: kubernetes-dev
  user:
    token: 内容
```

需要自己进行配置。在k8s中会进行命名空间权限设置，如果不指定命名空间，会出现没有权限的提示信息。

不在配置文件中配置命名空间，在输入指令时需要制定命令空间。

```Linux
kubectl -n dev  get events  
-n dev 指代命名空间
```

### 生效

查看是否生效

```Version
出现如下内容，则代表已经生效
$ kubectl version
Client Version: version.Info{Major:"1", Minor:"14", GitVersion:"v1.14.2", GitCommit:"66049e3b21efe110454d67df4fa62b08ea79a19b", GitTreeState:"clean", BuildDate:"2019-05-16T16:23:09Z", GoVersion:"go1.12.5", Compiler:"gc", Platform:"linux/amd64"}
Server Version: version.Info{Major:"1", Minor:"14", GitVersion:"v1.14.0", GitCommit:"641856db18352033a0d96dbc99153fa3b27298e5", GitTreeState:"clean", BuildDate:"2019-03-25T15:45:25Z", GoVersion:"go1.12.1", Compiler:"gc", Platform:"linux/amd64"}
```

## 常用几个命令

安装完客户端，就简单尝试使用下，从创建一个Ngnix开始。

```yaml
//使用这个yaml文件
apiVersion: apps/v1
kind: Deployment
metadata:
  name: nginx-deployment
spec:
  selector:
    matchLabels:
      app: nginx
  replicas: 2
  template:
    metadata:
      labels:
        app: nginx
    spec:
      containers:
      - name: nginx
        image: docker.infervision.com/library/nginx:1.7.9
        ports:
        - containerPort: 80
```

### 创建命令

创建一个ngnix服务。

```Linux
 kubectl -n dev  create -f ngnix.yaml

// 出现下面代表执行完毕
$ kubectl -n dev  create -f ngnix.yaml
deployment.apps/nginx-deployment created
```

### 查看创建状态

- 查看资源
  
```Linux
kubectl -n dev get deployment

NAME                 READY   UP-TO-DATE   AVAILABLE   AGE
nginx-deployment     2/2     2            2           50s

READY 前面的2 代表实际的，后面是期待成功数量
```

- 查看资源创建情况

```Linux
kubectl -n dev get pods

NAME                                  READY   STATUS             RESTARTS   AGE
nginx-deployment-678dd69468-4s28t     1/1     Running            0          8m11s
nginx-deployment-678dd69468-dmmvj     1/1     Running            0          8m11s

// 查看指定Pod
 kubectl -n dev  get pods -l app=nginx
  kubectl -n dev  get pods -L app   过滤包含app 标签及显示值
// 查看service rc ，也是同样的命令
kubectl -n dev get serivce
kubectl -n dev get rc
```

### 创建失败

在创建过程中，可能存在各种异常错误，导致我们创建服务失败，那怎么看呢？

```Linux
1. 查看创建的服务日志，检查是不是服务本身的问题
kubectl -n dev logs -f pod的name 就可以看到容器打印出来的日志内容

2019-06-11 10:39:36.789 [http-nio-8090-exec-1] INFO  c.i.basecenter.controller.AuthorityController - [INFO] 查询权限列表信息
2019-06-11 10:39:37.047 [http-nio-8090-exec-5] ERROR c.i.b.service.impl.PermissionValidateServiceImpl - 权限校验用时===0
2019-06-11 10:39:37.939 [http-nio-8090-exec-7] INFO  c.i.basecenter.controller.AuthorityController - [INFO] 查询权限列表信息
2019-06-11 10:39:38.093 [http-nio-8090-exec-3] ERROR c.i.basecenter.service.impl.HospitalServiceImpl - 查询全部医院用时===5
2019-06-11 10:39:38.519 [http-nio-8090-exec-3] ERROR c.i.basecenter.service.impl.HospitalServiceImpl - 查询全部医院总共用时===431
2019-06-11 10:39:46.595 [http-nio-8090-exec-5] INFO  c.i.basecenter.controller.AuthorityController - [INFO] 查询权限列表信息
2019-06-11 10:39:50.540 [http-nio-8090-exec-2] INFO  c.i.basecenter.controller.AuthorityController - [INFO] 查询权限列表信息
2019-06-11 10:39:50.680 [http-nio-8090-exec-6] ERROR c.i.basecenter.service.impl.HospitalServiceImpl - 查询全部医院用时===3
2019-06-11 10:39:51.048 [http-nio-8090-exec-6] ERROR c.i.basecenter.service.impl.HospitalServiceImpl - 查询全部医院总共用时===371


2. 如果不是容器内部问题

查看启动过程内容
在启动的时候看到为什么没有启动情况

kubeclt get -w events    -w 表示不间断查看日志内容，找到错误信息  

77m         Normal    Created            order/repu-koubei-tls-1848848988               Created Challenge resource "repu-koubei-tls-1848848988-0" for domain "koubei.dev.inferread.com"
89s         Normal    Created            order/repu-koubei-tls-1848848988               Created Challenge resource "repu-koubei-tls-1848848988-0" for domain "koubei.dev.inferread.com"
29s         Normal    Created            order/repu-koubei-tls-1848848988               Created Challenge resource "repu-koubei-tls-1848848988-0" for domain "koubei.dev.inferread.com"
45s         Warning   FailedOrder        certificate/repu-koubei-tls                    Order "repu-koubei-tls-1848848988" failed. Waiting 1h0m0s before retrying issuance.
32s         Normal    OrderCreated       certificate/repu-koubei-tls                    Created Order resource "repu-koubei-tls-1848848988"
9m57s       Normal    OrderCreated       certificate/repu-koubei-tls                    Created Order resource "repu-koubei-tls-1848848988"
5m36s       Normal    Scheduled          pod/sso-server-74dd44d6f9-g4l2r                Successfully assigned dev/sso-server-74dd44d6f9-g4l2r to tx-cluster-01
3m57s       Normal    Pulling            pod/sso-server-74dd44d6f9-g4l2r                Pulling image "hub.infervision.com/tools/base_center:dev"
3m55s       Normal    Pulled             pod/sso-server-74dd44d6f9-g4l2r                Successfully pulled image "hub.infervision.com/tools/base_center:dev"
3m55s       Normal    Created            pod/sso-server-74dd44d6f9-g4l2r                Created container sso-server
3m55s       Normal    Started            pod/sso-server-74dd44d6f9-g4l2r                Started container sso-server
3m58s       Normal    Killing            pod/sso-server-74dd44d6f9-nsmqx                Stopping container sso-server
5m37s       Normal    SuccessfulCreate   replicaset/sso-server-74dd44d6f9               Created pod: sso-server-74dd44d6f9-g4l2r

```

### 查看启动资源内容

当应用启动后，查看镜像是否更新，修改配置参数之后参数是否更新等内容。

```Linux
kubectl describe pod pod名字
里面包含了所需镜像的Hash值，环境变量等信息。
根据不同的业务来判断是否程序代码已经更新到最新。

Name:               sso-server-74dd44d6f9-g4l2r
Namespace:          dev
Priority:           0
PriorityClassName:  <none>
Node:               tx-cluster-01/192.168.130.31
Start Time:         Tue, 11 Jun 2019 14:30:21 +0800
Labels:             app.kubernetes.io/instance=sso-server
                    app.kubernetes.io/name=sso
                    pod-template-hash=74dd44d6f9
Annotations:        cni.projectcalico.org/podIP: 10.244.3.130/32
Status:             Running
IP:                 10.244.3.130
Controlled By:      ReplicaSet/sso-server-74dd44d6f9
Containers:
  sso-server:
    Container ID:   docker://548765e695cc8cf11e88385c4432c7ed286b4ccb201eaaf5ad53dcbb3ab075da
    Image:          hub.infervision.com/tools/base_center:dev
    Image ID:       docker-pullable://hub.infervision.com/tools/base_center@sha256:d2e89409461e2d7a169fec0057b1d67b49988c7d81a6d9df6c83edde54521216
    Ports:          8090/TCP, 48068/TCP
    Host Ports:     0/TCP, 0/TCP
    State:          Running
      Started:      Tue, 11 Jun 2019 14:30:24 +0800
    Ready:          True
    Restart Count:  0
    Liveness:       tcp-socket :http delay=180s timeout=1s period=10s #success=1 #failure=3
    Readiness:      tcp-socket :http delay=0s timeout=1s period=10s #success=1 #failure=3
    Environment:
      COM_START_SYNC:              false
      PORTAL_CONFIG_LOGIN_PAGE:    https://portal.dev.inferread.com/login?rd=
      PORTAL_CONFIG_HOME_PAGE:     https://base.dev.inferread.com
      PORTAL_CONFIG_PORTAL_PAGE:   https://portal.dev.inferread.com
      SPRING_DATASOURCE_URL:       jdbc:mysql://sso-mysql/base_center?useUnicode=true&characterEncoding=utf-8&useSSL=false
      SPRING_DATASOURCE_USERNAME:  tuixiang
      SPRING_DATASOURCE_PASSWORD:  <set to the key 'mysql-password' in secret 'sso-mysql'>  Optional: false
      SPRING_REDIS_HOST:           sso-redis-master
      SPRING_RABBITMQ_HOST:        sso-rabbitmq
      SPRING_RABBITMQ_PORT:        5672
      SPRING_RABBITMQ_USERNAME:    infra
      SPRING_RABBITMQ_PASSWORD:    <set to the key 'rabbitmq-password' in secret 'sso-rabbitmq'>  Optional: false
    Mounts:
      /var/run/secrets/kubernetes.io/serviceaccount from default-token-cltvc (ro)
Conditions:
  Type              Status
  Initialized       True
  Ready             True
  ContainersReady   True
  PodScheduled      True
Volumes:
  default-token-cltvc:
    Type:        Secret (a volume populated by a Secret)
    SecretName:  default-token-cltvc
    Optional:    false
QoS Class:       BestEffort
Node-Selectors:  <none>
Tolerations:     node.kubernetes.io/not-ready:NoExecute for 300s
                 node.kubernetes.io/unreachable:NoExecute for 300s
Events:
  Type    Reason     Age    From                            Message
  ----    ------     ----   ----                            -------
  Normal  Scheduled  9m21s  default-scheduler               Successfully assigned dev/sso-server-74dd44d6f9-g4l2r to tx-cluster-01
  Normal  Pulling    7m42s  kubelet, localhost.localdomain  Pulling image "hub.infervision.com/tools/base_center:dev"
  Normal  Pulled     7m40s  kubelet, localhost.localdomain  Successfully pulled image "hub.infervision.com/tools/base_center:dev"
  Normal  Created    7m40s  kubelet, localhost.localdomain  Created container sso-server
  Normal  Started    7m40s  kubelet, localhost.localdomain  Started container sso-server

```

查看配置文件内容还有另外一个方式

```Linux
kubectl -n dev get pod pod名字 -o yaml 该方式能看到完全的配置文件内容。
```

### 编辑资源

编辑pod 资源，尝试报露出IP地址。

```yaml
kubectl edit svc service名字

报露出IP 使用LoadBalancer
metadata:
  annotations:
    metallb.universe.tf/address-pool: default
    metallb.universe.tf/allow-shared-ip: "true"
spec:
  loadBalanceIP: 192.168.130.17  //如果IP被使用了，使用 get svc 查看IP显示是pending，可能存在一定的延时
  type: LoadBalancer // 设置成该类型
```

edit编辑完之后，kubectl 会自动更新。

### 更新pod

在kubectl 中更新pod 采用delete命令，在不删除svc情况下。

```Linux
kubetcl -n dev delete pod pod名字 会自动拉取新的镜像
```

