linux 进行分区汇总。新电脑配置了一个固态256G的还有一个3T的硬盘。新公司要求自己进行安装。刚开始安装系统的时候没有注意到还有一块硬盘。导致在安装系统的时候没有进行把普通硬盘进行分区和挂载，那么没法了只能手动去操作把剩余的硬盘进行分区和挂载操作。
## 分区操作
 我个人在操作linux分区的时候使用的是fdisk 命令 首先看下fdisk的命令学习下。使用fdisk --help 查看
 ![fdisk.png](https://upload-images.jianshu.io/upload_images/4237685-5b3db055a7763ad6.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
命令不用记住很多，大家不会使用的时候使用 --help可以看到这些。看怎么使用就好。
首先使用fdisk -l 检查电脑的硬盘 。展示需要尽行分区的文件内容。
![分区的信息](https://upload-images.jianshu.io/upload_images/4237685-566bd84e2ab74edd.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
使用sudo fdisk /dev/sdb 进行分区。输入m进行帮助展示。告知我们进行怎么的操作。
![对某个硬盘进行分区.png](https://upload-images.jianshu.io/upload_images/4237685-b6c71d51e8fb0969.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
然后按住n进行分区操作。创建一个新的分区
![n操作.png](https://upload-images.jianshu.io/upload_images/4237685-c79eb751bd5564c8.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
其中e属于扩展分区，p表示主分区。选择主分区，输入1表示第一个主分区
![选择p.png](https://upload-images.jianshu.io/upload_images/4237685-62efaa0b7b5e4860.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
默认选择1柱面，回车即可。输入开始的字节大小，回车之后输入结束的字节或者输入+500G代表在上面的字节开始的基础上增加500G大小。
![p查看分区.png](https://upload-images.jianshu.io/upload_images/4237685-d53a3e72bb7efc03.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
最后使用命令p查看下分区。
我这暂时已经分好区暂时没法进行操作。图形化操作：
![disk.png](https://upload-images.jianshu.io/upload_images/4237685-c33f16c8bb497cce.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
点击设置编辑图像，进行分区操作。然后设置磁盘大小和磁盘的磁盘名字，设置好之后保存。
## 挂载
进行分区完毕之后，需要挂载之后才能在df -h 命令下看到。
![最下面的/dev/sdb是新增加的分区.png](https://upload-images.jianshu.io/upload_images/4237685-a426637753db08b5.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
1. 挂载需要使用 mount 命令。 mount /dev/sdb1  需要挂载的路径，我是挂载在/home/ruiqi/Desktop/free目录下，挂载点可以是任意目录的。根据自己需要进行挂载。
2. 上面只是临时挂载我们需要进行永久性挂载。先检查下需要增加的分区uuid还有其类型。**sudo blkid /dev/sdb1** 进行查看uuid，保存好uuid和类型。
![uuid.png](https://upload-images.jianshu.io/upload_images/4237685-a6e6a7995c0e6a5f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
3. 修改/etc/fstab 文件：zai文件系统下面增加 uuid= “”  要挂在的目录， 磁盘文件类型。 默认操作 0 2 
![文件.png](https://upload-images.jianshu.io/upload_images/4237685-c8a5c91c4d79c816.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
4. 使用mount -a 命令检验下编辑使用成功。
5. 最后使用df -h  查看该目录是否增加了新的文件信息。

https://blog.csdn.net/dl6655/article/details/75045105  硬盘挂在 使用 普通用户权限
