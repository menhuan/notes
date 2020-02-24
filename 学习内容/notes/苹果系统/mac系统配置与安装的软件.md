# 软件安装与集合

本文主要是配置自己安装mac黑苹果后配置的软件与采坑，装备自己好的系统

## 包管理工具 brew

安装方式，网络不好情况下多尝试几次

`ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"`

## 截图工具

腾讯出品截图工具：[mac商店地址](https://apps.apple.com/cn/app/jie-tu-jietu/id1059334054?mt=12)

[第二个截图软件](https://apps.apple.com/cn/app/snip/id512505421?mt=12)

## iterm2

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

## zsh与oh_my_zsh

安装方式有很多，这里只介绍在用的插件与主题

## 配置代理

在~/.zshrc文件中添加

```Linux
alias setproxy="export ALL_PROXY=socks5://127.0.0.1:1086"
alias unsetproxy="unset ALL_PROXY"
```

配置使其生效source ~/.zshrc

## 配置vscode 快捷

将vscode安装到APP启动中，然后在~/.zshrc文件中增加如下内容。

```mac
alias code="/Applications/Visual\ Studio\ Code.app/Contents/Resources/app/bin/code"
```

source 重启其文件，就可以其快捷键。

## autojump

自动路径跳转

## grances

软件监控服务，查看CPU,内存，网络等信息
安装命令：

brew install grances

输入：grances 启动

![2019-12-27-10-14-07](http://jikelearn.cn/2019-12-27-10-14-07.png)

## CheatSheet

快捷键提示软件.

brew install CheatSheet

## 升级Python

brew install python3

设置当前的环境为Python3,进入到`/usr/local/Cellar/python/3.7.6.1/bin目录下找到python3`

在zshrc文件中增加变量设置`alias python="/usr/local/Cellar/python/3.7.6_1/bin/python3"`
使用source ~/.zshrc文件生效即可

该方式使用pip3下载内容

## 设置pipenv

pip3或者pip install pipenv

使用自动激活虚拟环境

```linux
# 复制环境
git clone "https://github.com/MichaelAquilina/zsh-autoswitch-virtualenv.git" "$ZSH_CUSTOM/plugins/autoswitch_virtualenv"

# 在插件中增加autoswitch_virtualenv

source ~/.zshrc
```

## 安装go环境

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

## 安装sshw

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

## 配置22端口

系统偏好设置->共享->远程登录

## 视频播放软件

商店搜索 Elmedia Video Player。

修改默认的 视频播放软件,任意点击一个map文件，使用command+i键，出现文件简介页面，如下图所示：
![2020-02-24-11-52-12](http://jikelearn.cn/2020-02-24-11-52-12.png)
点击打开方式，选择修改成的播放器，再点击全部修改

![2020-02-24-11-54-44](http://jikelearn.cn/2020-02-24-11-54-44.png)

## 截图软件

PicGo 软件或者Vscode中插件