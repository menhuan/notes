# 数据库查询慢

$ curl http://192.168.0.10:10000/products/geektime
Got data: () in 15.364538192749023 sec
# 注意下面的随机字符串是容器 ID，每次运行均会不同，并且你不需要关注它
$ docker run --name=redis -itd -p 10000:80 feisky/redis-server
ec41cb9e4dd5cb7079e1d9f72b7cee7de67278dbd3bd0956b4c0846bff211803
$ docker run --name=app --network=container:redis -itd feisky/redis-app
2c54eb252d0552448320d9155a2618b799a1e71d7289ec7277a61e72a9de5fd0



$ while true; do curl http://192.168.141.133:10000/products/geektime; sleep 5; done


docker run --rm -v /usr/local/bin:/target jpetazzo/nsenter 

极客时间版权所有: https://time.geekbang.org/column/article/78984



# 由于这两个容器共享同一个网络命名空间，所以我们只需要进入 app 的网络命名空间即可
$ PID=$(docker inspect --format {{.State.Pid}} app)
# -i 表示显示网络套接字信息
$ nsenter --target $PID --net -- lsof -i
COMMAND    PID            USER   FD   TYPE   DEVICE SIZE/OFF NODE NAME
redis-ser 9085 systemd-network    6u  IPv4 15447972      0t0  TCP localhost:6379 (LISTEN)
redis-ser 9085 systemd-network    8u  IPv4 15448709      0t0  TCP localhost:6379->localhost:32996 (ESTABLISHED)
python    9181            root    3u  IPv4 15448677      0t0  TCP *:http (LISTEN)
python    9181            root    5u  IPv4 15449632      0t0  TCP localhost:32996->localhost:6379 (ESTABLISHED)

