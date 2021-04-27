我们平常在进行程序开发的时候很大程度上都是在window下进行开发的。虽然window下开发对于我们很多人来说很方便的进行，但是对于学习Linux系统不是很方便。因为如果在Linux在进行开发的话有很大的好处。
1. 首先我们很多线上的环境都是Linux环境下，对于一个后台开发工程师来说掌握Linux系统命令就是一件十分重要的事情。所以推荐在开发的时候也在Linx系统下进行。
2. 开发环境部署，我们在Linux下总是十分方便的使用命令进行操作。帮助我们熟悉这些环境。windows的图形化界面很容易让我们丢失一部分对系统操作的理解。虽然十分便捷性好。
3. 如果我们学习数据库操作，很多时候在Linux环境上，那么大家使用Linux进行调试的时候会使用命令行的模式，方便大家进行数据库命令的学习，而不是一直去操作图形化方式，一直使用图形化操作，很容易让我们把一些命令都忘记，导致基础知识丢了。

说了这么多其实还是建议如果有环境的话，程序员还是在Linux环境下开发的好。虽然刚开始有点难，但是后面会发现有很多好处。那么我们在Linux系统下开发首先会遇到的一个问题就是快捷方式。
# 快捷方式的解决方案
有人说Linux开发就是要使用命令行操作，不使用图形化操作，这也是不对的。图形化操作帮助我们减少一些不必要的浪费时间的操作。那么帮助我们进行了工作的效率我们还是建议使用的。本次环境是在Ubuntu18.04环境下进行进行学习。其他方式没有尝试，大家可以进行尝试下。以安装Pycharm为例进行

- 下载安装： 这次安装是通过压缩包的方式。
![下载Pycharm](https://upload-images.jianshu.io/upload_images/4237685-711cf85389ed4a1d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
- 使用tar -zxvf pycharm-2018.1.4.tar.gz 
- 解压完毕后其实我们可以进入到bin目录下找到pycharm.sh 进行启动，就可以进行编码了。这里不讲解怎么破解，个人不建议破解的，有的时候破解完之后能用的很稳定，但是有时候不行，很多开发工具都是支持进行教育版的方式进行注册。可以去万能宝买个教育邮箱来注册使用，这样很稳定，花钱也不多。
- 我们刚才的解压目录是：/home/ruiqi/Desktop/soft/pycharm-2018.1.4
- 设置启动快捷方式：我们的快捷方式都是存放在/usr/share/applications/目录下的。 所以首先创建我们的文件pycharm.desktop。命令 sudo vim pycharm.desktop  或者使用sudo gedit  pycharm.desktop该命令也是可以的。
- 写入内容
```
//在使用的时候 只需要把Icon 和Exec 修改成为你自己的安装目录即可
 [Desktop Entry]
Version=1.0
Type=Application
Name=PyCharm
Icon=/home/ruiqi/Desktop/soft/pycharm-2018.1.4/bin/pycharm.png   
Exec="/home/ruiqi/Desktop/soft/pycharm-2018.1.4/bin/pycharm.sh" %f
Comment=The Drive to Develop
Categories=Development;IDE;
Terminal=false
StartupWMClass=jetbrains-pycharm
```
- 使用命令使其生效：sudo chmod u+x pycharm.desktop 
- 最后复制 该文件到桌面 cp pycharm.desktop  /home/rui/Desktop 上
- 双击启动程序即可 我们就在桌面上能看到该图标了。

上面显示方案在其他的软件安装也是同样的操作，创建快捷方式，方便我们快速的启动一些软件，进行使用。
# 普通用户权限
我们在平常使用Linux的使用因为权限的问题一般不会使用root账户去操作，使用普通用户去操作我们按转发的java环境的命令 会发现 指令不存在的问题，这就是我们所遇到的权限问题了。在普通用户的试用下我们需要在别的地方增加路径才能使用指令的方式。下面就是操作的指令
```
JAVA_HOME=/home/ruiqi/Desktop/soft/jdk1.8.0_181
JRE_HOME=/home/ruiqi/Desktop/soft/jdk1.8.0_181/jre
CLASS_PATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar:$JRE_HOME/lib
PATH=$PATH:$JAVA_HOME/bin:$JRE_HOME/bin
export JAVA_HOME JRE_HOME CLASS_PATH PATH
export GRADLE_HOME=/home/ruiqi/Desktop/soft/gradle-4.9
export PATH=$GRADLE_HOME/bin:$PATH
export GRADLE_USER_HOME=$GRADLE_HOME/.gradle
把这些路径放入到.bashrc  并进行生效。source .bashrc 原先使用的是root  /etc/profile 这是不一样的操作。
```
我们这些路径信息原先是放到/etc/profile ，但是这样情况下普通用户是没有权限操作的。普通用户应该放到 ~/.bashrc  里面 ，我们可以使用vim来操作该内容 
添加上之后 使用source ~/.bashrc 使其生效。剩下的操作我们就是一样的了。我们就可以跟使用root用户一样进行 命令行的操作
![命令行操作](https://upload-images.jianshu.io/upload_images/4237685-9e22d94821766a9e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
这就是我们今天所说的快捷方式与用户权限的问题操作方案。希望大家喜欢，能快速的加入到Linux环境中进行开发。
