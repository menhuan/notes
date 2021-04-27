前面的两篇文章简单的介绍了下关于线程有关Api的介绍和锁的应用，这些都是我们在开发中经常用到的方法和手段。多多练习，我们就会掌握住，虽然我们了解了API的使用，但是只懂表面，不深入了解，对于作为开发的我们来说，只能止于表面了。那么让我们现在开始深入底部看看多线程是怎么运行的吧。
## 线程的状态
在线程中一般分为七种状态。但是其中等待阻塞睡眠可以当成一种状态 
1. New ：新建立的线程，线程对象创建完毕，但是还没调用start方法，或者没有放到线程池里面提交
2. Running ： 运行状态，获得线程需要的资源，这是线程进入运行的唯一状态
3. Terminated ： 线程死亡阶段，当线程运行完毕后状态。死亡后不可操作
4. Timed_waiting ： 睡眠状态，指定睡眠多长时间的操作，可相当于暂停。
5. Blocked ：阻塞状态，没有获得资源或者锁资源。处于待执行的状态
6. Waiting ： 等待状态，获得唤醒之后继续操作。
7. Ready ： 准备阶段，执行了start方法，或者提交到线程池中，也可从其他状态转为该阶段
从下面的线程运行图可以看出整个线程运行的流程。具体其中的方法可以看[java多线程集合文章列表](https://mp.weixin.qq.com/mp/homepage?__biz=MzU5MjQ3NzY4MQ==&hid=5&sn=7e170bbd07db62ab6efd44dd58f8e1cc&scene=18&uin=&key=&devicetype=Windows+10&version=6206021b&lang=zh_CN&ascene=7&winzoom=1)
![线程状态运行图](https://upload-images.jianshu.io/upload_images/4237685-ae905fac92a92b4f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
## 线程之间的通信机制
   线程在整体运行中只是一个单独的个体，那么如果我们有很多个线程，那么他们之间的线程通信就是一种十分重要的手段。我们Java是怎么样实现线程之间的通信的呢？
  * 等待与通信机制
    等待通信机制，我们在程序中执行wait()与notity()方法来实现的。线程处于等待的时候，我们需要 用通知的方式来告知线程可以获取资源继续执行。
```
  //  两个独立的线程 一个用来等待，一个用来通知。 就可以实现等待通知通信机制。
    lock.wait(); 
    lock.notify();   //只能通知一个线程 来唤醒 ，但是该方法具有随机性的唤醒。不能一对一的指定 如果想一对一的唤醒可以选择Lock的Condition 来实现。
    lock.notifyAll();  // 通知所有的等待线程  
```
## join 的使用
我们在程序中会在主程序中创建多个子程序，但是有时候主程序需要获取子程序中的执行结果，会需要等待子程序执行完成后才能获取。这样我们就可以用到join().该方法就是用来等待线程对象销毁，实际上内部使用的wait方法。
```
     Thread thread  = new Thread();
     thread.join();
     
 // join 原理实现
 public final synchronized void join(long millis)
    throws InterruptedException {
        long base = System.currentTimeMillis();
        long now = 0;

        if (millis < 0) {
            throw new IllegalArgumentException("timeout value is negative");
        }

        if (millis == 0) {
            while (isAlive()) {
                wait(0);
            }
        } else {
            while (isAlive()) {
                long delay = millis - now;
                if (delay <= 0) {
                    break;
                }
                wait(delay);  // 使用 wait来帮助 实现等待 无限循环
                now = System.currentTimeMillis() - base;
            }
        }
    }       
```
但是在使用join 可能会出现的是主线程执行的代码在join后面的代码提前执行了。我们本来是让其等待子线程执行完毕再执行，可还是出现后面的代码提前执行的情况。
这是因为join在获取锁资源时，发现锁资源获取的时间已经超时，就会自动的把锁进行释放，那么所代表的是wait方法，wait方法在执行之后，会把锁资源进行释放，当再次获得锁资源之后超时在进行释放。在其他方面进行同样操作的线程也会同步输出。导致的。

