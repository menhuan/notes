###  ***kill命令***
![kil命令](http://upload-images.jianshu.io/upload_images/4237685-6219b76f8d3b4052.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
- kill -9 +进程号 彻底杀死进程
- kill -15 +进程号  正常关闭进程
- kill -19 +进程号  暂停 
- pkill -9  +名称就能删除进程
### ***ps指令***
- ps -aux  所有进程 
- ps -ef  进程
### ***top指令***
![top命令](http://upload-images.jianshu.io/upload_images/4237685-2711e5e692958f28.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
首先输入top 
- 第一行是系统时间， 运行时间， 使用者  一分钟5 分钟 15分钟系统的平均负载情况
- 第二行是进程数 ，总的进程数，几个在运行，几个在睡眠，几个停止，
- 第三行是cpu的运行情况 在这里可以按数字1 来显示多核情况。
6.7% us — 用户空间占用CPU的百分比。
0.4% sy — 内核空间占用CPU的百分比。
0.0% ni — 改变过优先级的进程占用CPU的百分比
92.9% id — 空闲CPU百分比
0.0% wa — IO等待占用CPU的百分比
0.0% hi — 硬中断（Hardware IRQ）占用CPU的百分比
0.0% si — 软中断（Software Interrupts）占用CPU的百分比
- 第四行：内存状态
8306544k total — 物理内存总量（8GB）
7775876k used — 使用中的内存总量（7.7GB）
530668k free — 空闲内存总量（530M）
79236k buffers — 缓存的内存量 （79M）
- 第五行：swap交换分区
2031608k total — 交换区总量（2GB）
2556k used — 使用的交换区总量（2.5M）
2029052k free — 空闲交换区总量（2GB）
4231276k cached — 缓冲的交换区总量（4GB）
- 第七行以下：各进程（任务）的状态监控
PID — 进程id
USER — 进程所有者
PR — 进程优先级
NI — nice值。负值表示高优先级，正值表示低优先级
VIRT — 进程使用的虚拟内存总量，单位kb。VIRT=SWAP+RES
RES — 进程使用的、未被换出的物理内存大小，单位kb。RES=CODE+DATA
SHR — 共享内存大小，单位kb
S — 进程状态。D=不可中断的睡眠状态 R=运行 S=睡眠 T=跟踪/停止 Z=僵尸进程
%CPU — 上次更新到现在的CPU时间占用百分比
%MEM — 进程使用的物理内存百分比
TIME+ — 进程使用的CPU时间总计，单位1/100秒
COMMAND — 进程名称（命令名/命令行）
平常我们所说的内存计算 在这里应该是第四行的free + 第四行的buffers + 第五行的cached。
-按下b 会自动排序按照大到小排序
-shift +< 或者> 可以自动改变排序的位置
- 当我们进入top命令之后，然后我们开始使用P 根据CPU的使用百分比代销进行排序
，按下M根据驻留内存大小尽心排序
### ***wc指令***
![wc指令]
![wc指令](http://upload-images.jianshu.io/upload_images/4237685-b4099188a3733865.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
- wc 直接加文件名称 可以计算出 行-l，英文单字-w，多少字符-m


### ***scp命令***
1. 上传到远程

- #scp /home/administrator/news.txt root@192.168.6.129:/etc/squid
- /home/administrator/      本地文件的绝对路径

- news.txt                  要复制到服务器上的本地文件

- root                                 通过root用户登录到远程服务器（也可以使用其他拥有同等权限的用户）

- 192.168.6.129                远程服务器的ip地址（也可以使用域名或机器名）

- /etc/squid                       将本地文件复制到位于远程服务器上的路径
2. 从远程下载到本地
- #scp remote@192.168.2.2:/usr/local/sin.sh /home/administrator

- remote                       通过remote用户登录到远程服务器（也可以使用其他拥有同等权限的用户）

- www.abc.com              远程服务器的域名（当然也可以使用该服务器ip地址）

- /usr/local/sin.sh           欲复制到本机的位于远程服务器上的文件

- /home/administrator  将远程文件复制到本地的绝对路径

- 注意两点：

- 1.如果远程服务器防火墙有特殊限制，scp便要走特殊端口，具体用什么端口视情况而定，命令格式如下：

- #scp -p 4588 remote@www.abc.com:/usr/local/sin.sh /home/administrator

- 2.使用scp要注意所使用的用户是否具有可读取远程服务器相应文件的权限
