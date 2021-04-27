# 分析网络流量

经常用的两个分析网络流量的服务延迟工具是tcpdump和wireshark两个工具。

- tcpdumo 支持命令行格式使用，常用在服务器中抓取和分析网络包
- wireshark 除了可以抓包外还有图形化界面和汇总分析工具，分析网络场景简单和实用。

## 再看ping

ping 命令缓慢的时候，不要着急可能是域名解析慢了，通过抓包来进行查看每一个情况。

ping -c3 geektime.org

```Java
抓包命令是： dcpdump -nn udp port 53 or host 35.190.27.188
-nn 表示不解析抓包中的域名，协议以及端口号。

tcpdump: verbose output suppressed, use -v or -vv for full protocol decode
listening on eth0, link-type EN10MB (Ethernet), capture size 262144 bytes

14:02:31.100564 IP 172.16.3.4.56669 > 114.114.114.114.53: 36909+(代表的是查询标识值，会出现在响应中，) A?（表示查询的A记录） geektime.org.（查询的域名） (30)（查询的耗费时间）

域名服务器的响应
14:02:31.507699 IP 114.114.114.114.53 > 172.16.3.4.56669: 36909 1/0/0 A 35.190.27.188 (46)

ICMP 每一个请求和ICMP echo reply
时间戳做减法。
14:02:31.508164 IP 172.16.3.4 > 35.190.27.188: ICMP echo request, id 4356, seq 1, length 64
14:02:31.539667 IP 35.190.27.188 > 172.16.3.4: ICMP echo reply, id 4356, seq 1, length 64

14:02:31.539995 IP 172.16.3.4.60254 > 114.114.114.114.53: 49932+ PTR? 188.27.190.35.in-addr.arpa. (44)
14:02:36.545104 IP 172.16.3.4.60254 > 114.114.114.114.53: 49932+ PTR? 188.27.190.35.in-addr.arpa. (44)
14:02:41.551284 IP 172.16.3.4 > 35.190.27.188: ICMP echo request, id 4356, seq 2, length 64
14:02:41.582363 IP 35.190.27.188 > 172.16.3.4: ICMP echo reply, id 4356, seq 2, length 64
14:02:42.552506 IP 172.16.3.4 > 35.190.27.188: ICMP echo request, id 4356, seq 3, length 64
14:02:42.583646 IP 35.190.27.188 > 172.16.3.4: ICMP echo reply, id 4356, seq 3, length 64
```

禁止PTR的方式可以通过查询man ping命令来查看。

```Java
ping -n -c3 geektime.org
这样就不会卡顿了
```

当然也可以动态的指定DNS服务器查询解析。

```Java
nslookup -type =PTR 35.190.27.188 8.8.8.8 指定8.8.8.8的域名服务商来解析。
```

## tcpdump

上面简单的看了下tcpdump的解析，现在还可以看到更多的方式来过滤信息。

![2019-03-04-23-18-17](http://jikelearn.cn/2019-03-04-23-18-17.png)。

![2019-03-04-23-18-43](http://jikelearn.cn/2019-03-04-23-18-43.png)

tcpdump 输出格式。

```Java
时间戳 协议 原地址，源端口> 目的地址，目的端口，网络包详情
```

## Wireshark

简单学习下使用即可。官网下载进行抓包。