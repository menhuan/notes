在学习Docker中我们必不可少的需要学习下Docker Compose。
# 简介
Docker Compose 是用来解决编排的问题，随着应用程序中，需要关联的Docker应用过多，那么对于Docker编排管理就响应的需要起来了。

Docker Compose 我们可以通过简单的模板文件，快速的创建和管理基于Docker容器的应用集群管理。
该项目是一个开源的项目，我们可以通过github上查看其源代码。[源代码](https://github.com/docker/compose)
在该项目中有两个重要的概念：
- 服务：一个应用的容器，可以包括若干的运行相同镜像的容器实例
- 项目： 由一组关联的应用容器，组成一个完成的单元

# 安装
