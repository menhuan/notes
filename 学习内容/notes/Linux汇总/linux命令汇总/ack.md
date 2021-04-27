# ack

ack是一款比grep更好的文本搜索工具。

## 安装

ubuntu上安装使用方式如下：

```linux
# ubuntu下要安装ack-grep，因为在debian系中，ack这个名字被其他的软件占用了。
sudo apt-get install ack-grep
```

## 使用

主要使用如下参数

- c :用来统计
- i : 忽略大小写
- h : 不显示名称
- l ：只显示文件名
- n : 加行号
- v : 显示不匹配

## 实例

### 查找文件

```linux
ack-grep -f test.txt
ack-grep -g  test.txt&
```

### 搜索文本

```linux
ack-grep -l "test" #包含文件名
ack-grep -L "test" #不包含文件名
```

当然还有更多的用法，你可以参考ack的[官网](https://beyondgrep.com/install/)

