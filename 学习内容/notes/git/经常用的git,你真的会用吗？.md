# git真的会使用吗？

## 前言

作为一个经常与git命令行陪伴的程序员，今天给大家带来一个我使用git命令行的体验。

如果你还没有使用过git，刚刚开始使用git，经常使用git图形化工具，那么我建议你可以跟我一起学习下。

## 了解git命令行

使用git作为我们的版本控制软件，已经成为日常习惯，但我们可能只是会粗暴的使用，并不明白为什么要这也没使用。

最近在公司我跟同事小明也遇到了这样的情况。在公司使用git的时候，身边同事很少使用命令，基本上都是使用的git可视化工具在操作命令。

这次在跟一个同事对**分支代码**时，让他拉去我代码，突然出现了与本地冲突的情况，然后他当时蒙蔽了，为什么会冲突呢？

本质上是我在使用git时，每次push都会rebase master代码。

小明原先本地已经拉过我原先的代码，被我使用push -f 覆盖分支代码后，他再拉取就会跟本地代码进行冲突。

这种情况的解决方式很简单，也不用解决冲突，切换下分支，将原先本地的分支代码进行删除，然后重新拉去最新的即可。

## 会使用git已经是全球流行的事情

这个案例告诉我们，对于git 不仅仅只是了解它的几个命令就代表会使用了，而是应该了解git为什么这么做。

git并不是局限于我们这个公司，它已经融入到了程序员的各个生活中。

**被微软收购的GitHub，刚刚上市的GitLab，国内流行的gitee，国内外各个互联网大厂都已经切换到Git来管理仓库代码。**

进入一家使用git的公司，不会使用，还需要去熟悉，耽误工作的进度。

我们想参与开源项目，但如果我们不会git，那么想参与开源项目的第一步就被卡主。

生活中也有很多朋友会写一些文章，我们也可以使用git管理我们的资料。

### 那怎么学习呢？

如果你已经对git命令熟悉过，可以直接跳过到场景化去补充自己的场景

其实很简单，分为两种情况。

1. 快速入门：那么就找几篇文章，看看怎么配置git，怎么往git仓库中提交代码，基本上就入门了
2. git高阶使用:根据场景化来学习怎么更好的学习git。

那么我们就开始学习吧。

## git命令介绍

git 常用的命令如下图所示

* [ ] 待会插入git命令行内容

在git我们基本上使用这些命令就够了。如果你对一些命令熟悉，可跳过。

### git的工作区域

在了解git命令前，我们先了解下git的工作区域。

在git中存在三个区域，分别为**工作区，暂存区，仓库区域**。也有说四种区域的，那是把**远程仓库区域**加上。

- 工作区域：基本上我们打开一个文件夹就是一个工作区域。可以认为是放代码的地方
- 暂存区：事实上就是把我们在工作区域修改的内容，先暂时放到这里。对应的命令就是我们的git add操作
- 仓库区域：用来存储我们修改完毕的内容，提交到本次仓库中，会形成一个新的版本数据，对应的命令就是commit
- 远程仓库：前面三个操作都是在本地操作，我们与其他人写作，需要把本地的修改push到远程仓库(可以认为是大家公用的一个存储地方)。然后其他人就可以看到我们所做的修改了

git的所有操作都是围绕这几个区域进行操作的。

### git 命令修改时文件状态

git 在对文件进行操作时，会有几种不同的状态转换。

#### 是否被git进行管理

文件是否被git进行管理处于两种状态。

1. 当文件还没有被git进行管理时，处于**untracked。**
2. 当文件被git进行管理时，这时候处于**tracked状态**。

**使用add命令可以将文件被git进行管理。**

#### 文件被管理后流转

当我们使用git add 将文件内容放入到git 管理后，我们文件就会处于以下几种

- new file: 代表这个文件是刚被git进行管理新创建出来的。
- modified: 代表本次修改中，把这个文件进行了修改
- deleted: 代表本次修改中，把文件进行了删除。

#### git文件状态区域的变更

当一个文件被修改，被新建，或者被删除后，这些修改都刚开始是在工作区。

这时候使用git status 命令，可以看到他们是在**Changes not staged for commit ，这时候是在工作区**

使用git add 后 变成 **Changes to be commited，这时候是在暂存区。**

使用git commit 之后，就保存到的本地仓库中。这时候是在仓库区域

#### 状态总结

总体而言就是下面这种图的流转状态。

* [ ] 待画图。

### git clone

git clone 用来克隆远程仓库。

git clone https://github.com/menhuan/notes.git 会直接在本地建立个notes文件夹

如果不想使用notes,可以自己命令

git clone https://github.com/menhuan/notes.git -b study 代表是把名称换成study.

### git add

add的动作就是把我们在工作区域的修改的内容，放入到暂存区，也是把当前文件没有被git进行管理的，进行管理。

`git add  . `

`git add filepath`

### git status 

git status 可以用来看当前不同区域有什么样的修改。

比如说文件处于工作区域，这时使用git status 显示的**Changes not staged for commit。**

如果文件处于暂存区，这时候使用git status 显示的是**Changes to be committed**

都没有改变，这时候使用git status 显示的是空。

### git commit 

git commit 用于提交本次的修改。

```shell
git commit -m "本次提交的内容" 

git commit # 这样提交会进入到编辑页面，可以多写一些注释

git commit --amend 将这次修改内容合并到上次提交中，并可以修改提交注释
```

### git branch

branch 是git中的一大特色，基本上每次一个任务我们就会建立出来一个新分支，来进行开发。

比如我们现在分支是feature_001.

我们还可以建立出其他分支比如feature_002.

这时候feature_001已经被我们合并到了master主干分支上，我们本地就可以把feature_001进行删除。`git branch -d feature_001.`

当然还有其他命令看使用。

* 删除一条分支：
  `git branch -D branchName`
* 删除当前分支外的所有分支：
  `git branch | xargs git branch -d`
* 删除分支名包含指定'dev'的分支：
  `git branch | grep 'dev\\*' | xargs git branch -d`
* 删除远程分支
  git push origin --delete FD-60642
* 将本地分支与远程分支关联
  git branch -u origin/远程分支的名字 ，让本地的tracking 分支与远程分支进行关联起来。
* 查看每个本地分支track的远程分支
  git branch -vv
* 删除分支
  git branch -d 分支
  git branch -D 分支 直接删除
* 查看分支是否被merge
  git branch —merged 查看哪些分支被merge到当前分支
  git branch —no-merged 查看哪些分支还没有被merge到当前分支上。

### git checkout 

git checkout 用于来创建分支并且可以关联到哪个分支或者主干上。

git checkout -b feature_001 会以当前分支为基础，创建一个feature_001的分支

git checkout feature_001 会以远程origin/feature_001 为基础，创建一个feature_001的分支。

git checkout feature_001 origin/master 会以远程origin/master为基础创建一个feature_001的分支。

### git cherry-pick

git cherry-pick commitId 摘桃子

git cherry-pick  `<start-commit-id>`..`<end-commit-id>` 左开右闭 不包含开始的commitid

git cherry-pick `<start-commit-id>`^..`<end-commit-id>` 左闭右闭 合并到当前分支

git cherry-pick —continue

git cherry-pick —abort 放弃

### git rebase

相当于 变基操作，本质是将自己开始的代码开始进行了更变。

git rebase -i HEAD^^^进行多次commit的内容修改，合并，删除，多个commit合并一个等。

git commit 进行拆分也可以使用git rebase -i ,然后使用git reset HEAD^进行回退。

git rebase —onto commitid1 commitid2 保留哪几个commitid,其他都合并到这里

### git merge

切换到某个分支上dev，然后执行git merge 要被merge到该分支的名字test。

git checkout dev & git merge test .

代表将test merge到dev上。

git fetch & git merge origin/分支名字  merge 远程分支。


### git log

git log —patch -n 进行diff 最近n次提交的代码diff git log —patch -2  每次提交的diff信息

git log —stat 显示commit的统计信息。

git log —pretty=oneline commit 显示一行

git log --oneline --abbrev-commit --graph 图形化显示

git log —abbrev-commit master..feature/001 比较两个分支差几个提交

git log —abbrev-commit origin/master..HEAD 看一下本地HEAD代码还有哪些没有推送到远程仓库上。

git log —abbrev-commit feature/001..feature/002  —not master 两个分支都有commit但是master还没有。

git log --oneline --decorate --graph --all  图形化看提交树

还可以配置快捷操作git lg

### git reflog

用于查看git 命令的历史。

### git push

git push -u origin 远程分支名字  给远程分支创建一个同名的分支

git push  直接push 代码，前提是已经做好关联

git push -f **强制刷新代码，在跟他人协作时，慎用。**

### git pull

git pull 默认使用的是merge操作

执行这个操作流程内容包含下面几方面。 假设本地分支commit id 512541 ，远程分支commit id 451512

1. 首先先把远程分支上的内容进行下载到本地
2. 跟本地的分支进行合并，如果有冲突的话，那么就会出现冲突的标识 ，没有出现冲突，那么就远程和本地分支合并成一个新的commit id 15121124
3. 出现冲突，那么就需要解决冲突后，生成新的commit id
4. 如果本地的分支是在远程分支的过程中，那么就直接将本地分支移动到最新的commit id上。

git pull 操作默认会把远程分支上的提交跟本地的[tracking分支](https://www.notion.so/Git-6a8444a057704ab695a95447ea62db12)进行合并操作

### git fetch

git fetch origin 代表的是从远程分支拉取 会有一个remote-tracking 分支，如果现在该分支上有新增的修改，那么就会与本地分支形成分叉，就需要我们进行合并操作。

### git diff

* git diff : 工作区与暂存区的区别
* git diff —cached: 暂存区与仓库的区别
* git diff HEAD : 工作区与仓库的区别
* git diff 本地分支 与 另外分支：比较两个分支的区别
* git diff HEAD HEAD^ : 比较两次提交的区别

### git stash

git stash list 列表

git stash apply 将最近的一次 stash恢复过来 git stash apply stash@{0} 恢复到指定的一次stash

git stash apply —index 有存放到暂存区的内容可以使用这个命令。

git stash drop stash名称 删除某个stash

git stash pop 移出来最顶层的stash内容

git stash push -m "内容"

git stash 默认情况下只操作tracked内容，如果需要stash untracked内容，那么就需要git stash —index —include-untracked 将暂存区内容和tracked和untracked内容暂存

## 场景化学习

* [ ] 待完善场景化学习

## 总结
