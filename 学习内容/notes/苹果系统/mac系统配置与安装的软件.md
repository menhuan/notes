# 软件安装与集合

本文主要是配置自己安装mac黑苹果后配置的软件与采坑，装备自己好的系统.主要分为两种。开发软件和办公软件

## 开发软件

### IDE

- IDEA:Java强大的IDE
- PyCharm：Python IDE
- DataGrip: 数据库连接工具
- Navicat:强大的数据库管理软件，你懂的

### 升级Python

brew install python3

设置当前的环境为Python3,进入到`/usr/local/Cellar/python/3.7.6.1/bin目录下找到python3`

在zshrc文件中增加变量设置`alias python="/usr/local/Cellar/python/3.7.6_1/bin/python3"`
使用source ~/.zshrc文件生效即可

该方式使用pip3下载内容

#### 设置pipenv

pip3或者pip install pipenv

使用自动激活虚拟环境

```linux
# 复制环境
git clone "https://github.com/MichaelAquilina/zsh-autoswitch-virtualenv.git" "$ZSH_CUSTOM/plugins/autoswitch_virtualenv"

# 在插件中增加autoswitch_virtualenv

source ~/.zshrc
```

### 安装go环境

brew install go

```linux
zsh文件中配置go路径信息 可以自定义GOPATH
export GOPATH=$HOME/go
export PATH=$PATH:/usr/local/go/bin::$GOPATH/bin

source ~/.zshrc
# 配置go使用的代理
export GO111MODULE=on
export GOPROXY=https://goproxy.io
```

### 安装sshw

sshw 方便去操作ssh ，不用每次都手动输入ssh 命令去连接终端。省时省力

![sshw](http://jikelearn.cn/img/20200311214503.gif)

```linux
go get -u github.com/yinheli/sshw/cmd/sshw

写配置文件 ~/.sshw
- name: 组名
  children:
  - { name: spring, user: spring, host: 192.168.111.12, password: XX******XXX, port: 22}
```

## tree 命令

brew install tree

## docker

brew cask install docker

## 查看支持的字体

brew install fontconfig

查看中文：fc-list :lang=zh

## 安装java环境

brew cask install java

## 安装plant uml

brew cask install java
brew install graphviz

## 软件工具

常用的Mac软件工具

## 视频播放软件

IINA 强大的免费播放器，可以播放任何可以播放的视频。
![20200311215545-2020-3-11-.png](http://jikelearn.cn/img/20200311215545-2020-3-11-.png)

## 包管理工具 brew

安装方式，网络不好情况下多尝试几次

`ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"`

## 输入法

自然还是大**搜狗输入法**了。

### 自动切换输入法

自动切换输入法，帮助在切换到其他应用时，能切换到需要的输入法上。减少每次输入法的切换。

### 超级右键

增强鼠标右键的功能，跳转到iterm,终端，新建文件件，跳转到具体文件夹中。

### 安装可以挂载NTFS挂载软件

1. [NTFS For Mac 帮助读取NTFS硬盘的工具](https://www.ntfsformac.cn/)
2. [Mounty for NTFS](https://mounty.app/)
3. [ntfstool](https://github.com/ntfstool/ntfstool/releases) 免费的开源挂载工具

### 虚拟机

在Mac上有时候必不可少需要使用Windows系统,比如打游戏，ie浏览器，这就需要一个强大的虚拟机来提供帮助了帮助了。 这就是 [Parallels](https://www.parallels.cn/landingpage/pd/general/?gclid=Cj0KCQjw9ZzzBRCKARIsANwXaeLyKA0k3I_yl7UV05P-mYRy1YaswruzYsRFwDdJmtk9Ud0Esv2lBG0aAvijEALw_wcB),不经可以运行Windows，还可以运行Ubuntu等Linux系统。

![20200310160624-2020-3-10-.png](http://jikelearn.cn/img/20200310160624-2020-3-10-.png?imageView2/1/w/800/h/600)

### grances

软件监控服务，查看CPU,内存，网络等信息
安装命令：

brew install grances

输入：grances 启动

![2019-12-27-10-14-07](http://jikelearn.cn/2019-12-27-10-14-07.png)

### CheatSheet

快捷键提示软件.`brew cask install CheatSheet` 长按Commod键弹出快捷键提示
![20200310161824-2020-3-10-.png](http://jikelearn.cn/img/20200310161824-2020-3-10-.png)

### 浏览器

浏览器建议是谷歌浏览器Chrome或者火狐浏览器。![20200310161658-2020-3-10-.png](http://jikelearn.cn/img/20200310161658-2020-3-10-.png)

### 护眼宝

长期面对电脑，眼睛容易疲劳.使用护眼软件保护眼睛，降低屏幕蓝关，保护眼睛。[护眼宝](http://huyanapp.com/app/eyepro.pc.html)
![护眼宝截图](http://jikelearn.cn/img/20200310171831-2020-3-10-.png)

### 压缩软件

Keka压缩软件是一款开源的压缩软件，可以从github上看到他们的代码。[keka官网](https://www.keka.io/zh-cn/)

### Picgo

图片上传工具，可以把自己图片上传到云床上。支持多个自定义云床，包含Github,七牛，阿里云，等[PicGo官网](https://molunerfinn.com/PicGo/)

![动图](http://jikelearn.cn/img/20200310231252.gif)

### 截图工具

腾讯出品截图工具：
[mac商店地址](https://apps.apple.com/cn/app/jie-tu-jietu/id1059334054?mt=12)
[第二个截图软件](https://apps.apple.com/cn/app/snip/id512505421?mt=12)

强大的SnipASTE截图软件
[Snipaste截图软件](https://zh.snipaste.com/)

## VS code

强大的文本编辑器，使用它，完全可以抛弃其他任何的编辑器了，配置插件，写markdown文本，写作，制图，写程序完全搞定。本篇文章就是使用Vscode编辑的。用好VsCode插件系统打造一个完美的效率工具。

### Tencent Lemon

腾讯出品的Mac清理工具，重点是免费，功能还不少。

### 效率神器

Alfred:不用多说，用过的都知道，[操作指导](https://github.com/zenorocha/alfred-workflows)
国内出品的效率神器Utools:[Utools](https://www.u.tools/)

### 文档

1. wps:国产本地软件，重要的是免费。
2. office:微软产品，不再多说
3. 腾讯文档：云文档，支持Markdown语法，与word兼容。如果不想在本地使用wps或者office，腾讯文档是你的选择之一
4. 石墨文档：第一个云文档产品，值得推荐。

### iterm2

必备的iterm2 ，命令行客户端，但是默认是文件夹与文件都是统一的黑白颜色，看的很不舒服，需要修改文件夹与文件的配色。
技术实现方案如下.

1. mac上没有Dircolor，我们需要在mac系统上装上coreutil这个配色包，安装方式如下

    ```mac
    wget https://raw.github.com/trapd00r/LS_COLORS/master/LS_COLORS -O $HOME/.dircolors
    ```

2. 在我们的shell里面配置，bash 配置.bashrc文件,zsh 配置.zshrc文件。

    ```mac
    eval $(gdircolors -b $HOME/.dircolors)
    if [ -n "$LS_COLORS" ]; then
        zstyle ':completion:*' list-colors ${(s.:.)LS_COLORS}
    fi
    ```

3. 重新启动terminal，再次查看文件的路径颜色就会发现已经被改变了。

为了方便的北京颜色，item2上增加了很多种配色方案，但是需要自己去下载并修改。[github地址](https://github.com/mbadolato/iTerm2-Color-Schemes/blob/master)。

### zsh与oh_my_zsh

安装方式有很多，这里只介绍在用的插件与主题

#### 配置代理

在~/.zshrc文件中添加

```Linux
alias setproxy="export ALL_PROXY=socks5://127.0.0.1:1086"
alias unsetproxy="unset ALL_PROXY"
```

配置使其生效source ~/.zshrc

#### autojump

自动路径跳转

#### 配置vscode 快捷

将vscode安装到APP启动中，然后在~/.zshrc文件中增加如下内容。

```mac
alias code="/Applications/Visual\ Studio\ Code.app/Contents/Resources/app/bin/code"
```

source 重启其文件，就可以其快捷键。