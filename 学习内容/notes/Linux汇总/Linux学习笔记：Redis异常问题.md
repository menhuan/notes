# Redis 异常问题

IO执行步骤流程如下：

1. 程序启动后，使用top命令检查内存,CPU，使用情况。
2. 如果观察wa 出现异常情况超过60%，那么代表IO出现问题。
3. 使用iostat -d -x 1 查看哪个硬盘IO占用大，查看写入与读取内容的大小，还有IO的使用率。
4. 判断系统是读需求还是写需求，检查是否与iostat上面显示的是一样的。
5. 使用pidstat -d 1 查看进程io情况。 找到写文件的进程或者读文件的进程与上面的对比。
6. 使用 strace 命令查看 进程在写什么。

    ```Linux
    strace -f -T -tt -p 进程号
    -f 表示跟踪子进程与子线程。
    -T 表示显示系统调用的时长
    -tt 表示显示跟踪事件
    -p 进程号
    -e 表示查看某个内容的调用情况。
    ```

7. 使用 lsof - p 端口号 命令 查看进程调用的对象内容。
8. 很多程序都是在容器中使用那么我们需要用到 nsenter工具。

   ```Linux
   docker run --rm -v /user/local/bin:/target jpetazzo/nsenter
   终端使用：
   找出PID PID=$(docker inspect --format {{.State.Pid}} app(容器的名字))
   nsenter --target $PID --net -- lsof -i 可以找出来 TCP链接对应的链接信息。
    出现错误可以执行 LANG=/usr/lib/locale/en_US 解决问题
   ```