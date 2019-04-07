# git命令学习

## stash

保存修改的文件，将工作区保存为干净的工作区。

常用的用命令。

- git stash apply: 恢复为保存文件的之前的内容，将其放到工作区中，stash列表内容不删除。
- git stash pop：将堆栈区内的内容取出来，并且stash内容进行删除。
- git stash list:保存的堆栈区的列表信息。
- git stash :将内容放到堆栈区内。

## 备份命令

### clone

克隆 git clone --bare 路径/file:协议/ssh协议/http 协议内容。 --bare 代表不带工作区的协议

### remote

- git remote -v：查看相关的远程协议内容
- git remote add: 增加远程协议内容。跟上面clone使用的类似
- git push --set-upstream: 更新到远端可以使用这个命令。

### merge

- git merge 分支（包含远程分支）
- git merge --alow-unrelated-histories: 允许历史不相关的分支，进行关联起来。

### push

- git push  github(远程地址在本地设置的名字) master(远程的master)：将本地的master分支推送到远端

### local

- git config set --local user.name ="用户名"，设置本地用户
- git config --local -l :展示用户名

### checkout

用来切换分支，但是如果想根据远程分支来创建本地分支需要使用如下命令。

- git checkout -b 本地分支的名字 远程分支的名字.
