# git 遇到的问题集合

## git status 中文乱码

在mac系统中git status 中文乱码解决方案是使用  git config --global core.quotepath false 进行设置中文乱码问题就会被解决,结果如图所示。
![2019-12-19-11-58-55](http://jikelearn.cn/2019-12-19-11-58-55.png)

## 配置一些git全局快捷键

```linux
修改 ~/.gitconfig 文件

[alias]
     tree = !git log --graph --decorate --pretty=oneline --abbrev-commit --all
     lg = log --color --graph --branches --pretty=format:'%C(auto)%h %C(auto)%d %C(auto)%s %Cgreen(%cr) %C(bold blue)<%an>%Creset' --decorate --abbrev-commit --all
     cp = cherry-pick
```
