# docker问题记录

## 批量停止docker

```linux
docker ps -a | awk '{print $1}'|xargs docker stop
docker
```

## 查看docker中记录的日志

```linux
sudo su 输入当前用户密码，切换到root用户

cat /var/lib/docker/containers/docker_id/docker_id-json.log*
```

## 批量删除镜像

```linux
docker image prune --force --all或者docker image prune -f -a` : 删除所有不使用的镜像
docker rm $(docker ps -aq)
```
