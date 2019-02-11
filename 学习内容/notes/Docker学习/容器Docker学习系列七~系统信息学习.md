前面了解的Docker学习的本地镜像管理还有镜像仓库的命令学习，怎么说呢其实这些命令已经够我们平常使用的了。但是如果随着发展我们在系统程序调优上可能就需要了解更多的知识。比如Docker系统信息，Docker版本信息，等等。这样才能在程序调优上帮助我们很多。我们今天就来了解下Docker系统信息和版本信息有关的命令。
# info
用来显示系统信息,包含镜像和容器数量
> 语法糖 Docker info [OPTIONS]
- --format -f 使用给定的GO模板格式化输出
列子： 展示docker信息，我们可以仔细的了解其中的内容
```
ruiqi@fruiqi:~/Desktop/code/database-H2-demo$ docker info 
Containers: 4
 Running: 3
 Paused: 0
 Stopped: 1
Images: 18
Server Version: 17.12.1-ce
Storage Driver: overlay2
 Backing Filesystem: extfs
 Supports d_type: true
 Native Overlay Diff: true
Logging Driver: json-file
Cgroup Driver: cgroupfs
Plugins:
 Volume: local
 Network: bridge host macvlan null overlay
 Log: awslogs fluentd gcplogs gelf journald json-file logentries splunk syslog
Swarm: inactive
Runtimes: runc
Default Runtime: runc
Init Binary: docker-init
containerd version: 9b55aab90508bd389d7654c4baf173a981477d55
runc version: 9f9c96235cc97674e935002fc3d78361b696a69e
init version: v0.13.0 (expected: 949e6facb77383876aeff8a6944dde66b3089574)
Security Options:
 apparmor
 seccomp
  Profile: default
Kernel Version: 4.15.0-32-generic
Operating System: Ubuntu 18.04.1 LTS
OSType: linux
Architecture: x86_64
CPUs: 12
Total Memory: 31.35GiB
Name: fruiqi
ID: JLSZ:WYWD:4JDS:TCT2:VFCU:OM5Z:BD3L:YRPJ:OTHM:PBE7:BPO5:2FBG
Docker Root Dir: /var/lib/docker
Debug Mode (client): false
Debug Mode (server): false
Registry: https://index.docker.io/v1/
Labels:
Experimental: false
Insecure Registries:
 127.0.0.0/8
Live Restore Enabled: false
```
# version 
Docker版本信息
>语法糖： docker version [OPTIONS]
OPTIONS : 
- -f:指定返回值的模板文件
```
tx-deepocean@tx-cloud-01:/media/data/labeltest$ docker version 
Client:
 Version:           18.06.0-ce
 API version:       1.38
 Go version:        go1.10.3
 Git commit:        0ffa825
 Built:             Wed Jul 18 19:11:02 2018
 OS/Arch:           linux/amd64
 Experimental:      false

Server:
 Engine:
  Version:          18.06.0-ce
  API version:      1.38 (minimum version 1.12)
  Go version:       go1.10.3
  Git commit:       0ffa825
  Built:            Wed Jul 18 19:09:05 2018
  OS/Arch:          linux/amd64
  Experimental:     false
```
查看版本信息与具体配置信息，帮助我们在进行运维的时候快速定位问题，我们应该了解起根本原因，而不能只停留在表面上。
原先的命令忘记了，可以点击以下文章进行学习
[容器Docker学习系列一](https://www.jianshu.com/p/4619963629a6)
[容器Docker学习系列二](https://www.jianshu.com/p/ed3b3e740d28)
[容器Docker学习系列三~命令学习](https://www.jianshu.com/p/e53dad602dcc)
[容器Docker学习系列四~源的修改增加效率](https://www.jianshu.com/p/9704bd1121a3)
[容器Docker学习系列五~命令学习history,save, import](https://www.jianshu.com/p/2e998757a231)
[容器Docker学习系列六~命令学习仓库命令](https://www.jianshu.com/p/30c96cf533e3)

