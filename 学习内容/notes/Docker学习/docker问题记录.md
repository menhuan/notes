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

## network

问题内容是:`network has active endpoints`

```linux
docker network disconnect -f {network} {endpoint-name}
# 其中 endpoint-name 可以使用 docker network inspce network 查看Containers下的Name名称
[
    {
        "Name": "v40rc2dev_default",
        "Id": "b341b415495c5b3447da43de593b9a4b8009e45bdbd9e6dca4fd188c0bd907f0",
        "Created": "2020-02-19T11:09:51.271219138+08:00",
        "Scope": "local",
        "Driver": "bridge",
        "EnableIPv6": false,
        "IPAM": {
            "Driver": "default",
            "Options": null,
            "Config": [
                {
                    "Subnet": "8.8.6.1/24",
                    "Gateway": "8.8.6.1"
                }
            ]
        },
        "Internal": false,
        "Attachable": true,
        "Ingress": false,
        "ConfigFrom": {
            "Network": ""
        },
        "ConfigOnly": false,
        "Containers": {
            "05ec635f5bd4d52be5b7f83c9e4e76b217920ad107d4a0805e4f9d73d7a59a02": {
                "Name": "v40rc2dev_mongo_1",
                "EndpointID": "528f3d1e77ac4913cf9c2e321657935a63229172014af879cd8889b3de096fe5",
                "MacAddress": "02:42:08:08:06:03",
                "IPv4Address": "8.8.6.3/24",
                "IPv6Address": ""
            }
        },
        "Options": {},
        "Labels": {
            "com.docker.compose.network": "default",
            "com.docker.compose.project": "v40rc2dev"
        }
    }
]

docker network disconnect -f laradock_backend laradock_php-client_1
或者使用docker-compose up -d --remove-orphans

```
