# 挂载

在Linux上进行硬盘的挂载有两种分别，一种是大于等于2T的硬盘，一种是小于2T容量的硬盘。

这里会涉及两种硬盘分区的命令，分别是适用于大于2T级别的硬盘分区的parted与适用于小于2T级别硬盘的方式fdisk。

下面我们分别使用尝试使用这两个命令操作。

## parted

对于不熟悉命令的朋友，先尝试parted --help ,查看命令的介绍，来操作。

```Linux
ruiqi@fruiqi:~$ parted --help 
Usage: parted [OPTION]... [DEVICE [COMMAND [PARAMETERS]...]...]
Apply COMMANDs with PARAMETERS to DEVICE.  If no COMMANmessageD(s) are given, run in
interactive mode.

OPTIONs:
  -h, --help                      displays this help 
  -l, --list                      lists partition layout on all block devices
  -m, --machine                   displays machine parseable output
  -s, --script                    never prompts for user intervention
  -v, --version                   displays the version
  -a, --align=[none|cyl|min|opt]  alignment for new partitions
```

在这里我们使用parted -l 展示出硬盘，然后找到自己要挂载的硬盘。

```Linux
[root@tidb1 network-scripts]# parted -l | grep Disk
Disk /dev/sda: 8001GB
Disk Flags:
Disk /dev/sdb: 119GB
Disk Flags:
Disk /dev/sdc: 8001GB
Disk Flags:
Disk /dev/sdd: 8001GB
Disk Flags:
Disk /dev/sde: 119GB
Disk Flags:
Disk /dev/mapper/centos-home: 52.6GB
Disk Flags:
Disk /dev/mapper/centos-swap: 11.9GB
Disk Flags:
Disk /dev/mapper/centos-root: 53.7GB
Disk Flags:
```

从图中可以找到想要分区的磁盘，我们拿磁盘/dev/sda 举例。

### 例子

1. 进入 磁盘操作

```Linux
parted /dev/sda/
```

2. 定义分区格式

```Linux
mklabel gpt
// 常用的有msdos和gpt分区表格式，msdos不支持2TB以上容量的磁盘，所以大于2TB的磁盘选gpt分区表格式
```

3. 创建分区

```Linux
parted  p  这里是创建分区 并且把分区命名为p。

File system type？  [ext2]?  ext3  支持ext2与ext3 两种文件格式，需要设置为ext4 ，可以在分区结束后使用mkfs.ext4 /dev/sda操作 

Start？  0%

END?  100%   这两个数值可以用百分比，也可以使用1T 2T这样的单位实现。

使用print 查看下分区。

quit 退出。
```

4. 格式化分区文件系统

```Linux
mkfs.ext4 /dev/sda
```

分区这样就完成了

## fdisk

说完大于等于2T硬盘分区，我们在分区一个小于2T的硬盘。

1. 选择要分区的硬盘

```Linux
fdisk /dev/sdb
```

2. 查看使用的命令

```Linux
command (m for help): m
Command action
   a   toggle a bootable flag
   b   edit bsd disklabel
   c   toggle the dos compatibility flag
   d   delete a partition
   l   list known partition types
   m   print this menu
   n   add a new partition
   o   create a new empty DOS partition table
   p   print the partition table
   q   quit without saving changes
   s   create a new empty Sun disklabel
   t   change a partition's system id
   u   change display/entry units
   v   verify the partition table
   w   write table to disk and exit
   x   extra functionality (experts only)
```

3. 创建一个分区

```Linux
Command (m for help): n
Command action
   e   extended
   p   primary partition (1-4)
p    //建立主分区
Partition number (1-4): 1  //分区号
First cylinder (1-391, default 1):  //分区起始位置
Using default value 1
last cylinder or +size or +sizeM or +sizeK (1-391, default 391): 100  //分区结束位置，单位为扇区

Command (m for help): n  //再建立一个分区
Command action
   e   extended
   p   primary partition (1-4)
p 
Partition number (1-4): 2  //分区号为2
First cylinder (101-391, default 101):
Using default value 101
Last cylinder or +size or +sizeM or +sizeK (101-391, default 391): +200M  //分区结束位置，单位为M
```

4. 查看分区的建立情况

```Linux
Command (m for help): p

Disk /dev/sdb: 3221 MB, 3221225472 bytes
255 heads, 63 sectors/track, 391 cylinders
Units = cylinders of 16065 * 512 = 8225280 bytes

   Device Boot      Start         End      Blocks   Id  System
/dev/sdb1               1         100      803218+  83  Linux
/dev/sdb2             101         125      200812+  83  Linux
```

5. 保存退出

```Linux

Command (m for help): w
The partition table has been altered!

Calling ioctl() to re-read partition table.
Syncing disks.
```