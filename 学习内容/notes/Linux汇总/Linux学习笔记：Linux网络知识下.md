# 衡量网络性能的指标

了解指标帮助我们了解网络性能的好坏。

## 性能指标

- 带宽：最大的传输速率，单位是b/s ，我们通常说的千兆百兆都是小b为单位的。网速是按照大B来说的10MB/s
- 吞吐量: 单位时间内成功传输的数据量，单位也是b/s或者B/s 字节每秒。吞吐量/带宽就是网络的使用率。
- 延时：网络请求后，一直到收到远端响应，所需要的时间延迟。指标具有不同的含义，数据往返所需要的时间，建立连接的时间也可以叫做延迟。
- PPS: 表示网络包为单位的传输速率。通常用来评估网络的转发能力。

还有网络的可用性，并发连接数，丢包率，重传率常用的性能指标。

## 网络配置

常用的网络工具 ifconfig , ip 两个命令指令。

- 网络接口状态，ifconfig 中的输出是RUNNING， ip 输出LOWER_UP 表示网络连通，没有代表网线被拔掉。
- MTU的大小。
- 网络接口的地址，ip掩码，MAC地址等内容。
- 网络接收包，字节数，错误以及丢包情况。TX与RX的 errors,dropped,overruns,carrier以及collisions 指标不为0的时候代表网络出现问题。

## 套接字信息

网络协议栈中的统计信息 ，我们可以使用 netstat 或者ss 来观察，查看套接字。网络栈，网络接口以及路由表的内容。

建议使用ss，有更好的性能。

```Linux
-l 表示只监听套接字
-t 表示只显示TCP套接字
-n 表示显示数字地址与端口。
-p 显示进程信息
ss -ltnp | head -n 3

State      Recv-Q Send-Q Local Address:Port               Peer Address:Port              
LISTEN     0      128          *:22                       *:*                  
LISTEN     0      128          *:5432                     *:*                  
LISTEN     0      128    127.0.0.1:6010                     *:*                  
LISTEN     2      1            *:8670                     *:*                  
LISTEN     0      128         :::7946                    :::*                  
LISTEN     0      128         :::5432                    :::*
```

重点关注接收队列Recv-Q和Send-Q。

不同的状态数字有不同的含义。

### Established连接状态

- Recv-Q 表示套接字缓冲还没有被应用程序取走的字节数。接收队列的长度。
- Send-Q 表示还没有被远端主机确认的字节数，发送队列的长度

### Listening 监听状态

1. syn backlog 表示TCP协议栈中的半连接队列长度。**半连接表示**：未完成TCP三次握手的连接，只进行了一半。

2. accept queue 表示全连接队列。表示，服务器已经接收到客户端的ACK，完成三次握手，把连接迁移到全连接队列中，再被系统的accept()系统调用，服务器开始处理客户端的请求。

- Recv-Q 表示syn backlog 的当前值
- Send-Q 表示 最大的 syn backlong 。

## 协议栈统计信息。

使用 ss 或者netstat 查看。

## 网络吞吐和PPS

sar -n 查看网络的统计信息，当然还可以看CPU 内存 I/O信息。

- rxpck/s 和txpck/s 接收和发送的PPS， 单位为包每秒。
- rxkB/s 和 txkB/s 接收和发送的吞吐量。单位是KB/S
- rxcmp/s 和txcmp/s 接收和压缩的数据包量，单位是包每秒。
- %ifutil表示网络接口的使用率。
- Bandwidth 可以用ethtool 查询

```Linux
ethtool eth0 |grep Speed

```

## 连通性与延迟

使用指令 ping -c3 ip地址 ，查看。
