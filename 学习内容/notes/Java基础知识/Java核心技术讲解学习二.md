继续我们的Java核心知识点的学习。上面一篇说过虚拟机还有Java世界平台的简单知识，还顺便总结了Error和Exception相关内容看相关内容，请阅读[Java核心技术讲解学习一](https://www.jianshu.com/p/dfbdd2074c74)。我们今天说点别的引用还有final，finally ,
finalize三者相关的内容。
## final.finally,finalize 
   我们平常在使用的时候一般在变量中使用final，但是他不仅仅可以用在变量上，还可以用在方法上还有类上。那么final的作用是什么呢？
  - 使用在类上 代表该类不可继承扩展。
  - 使用在变量上，代表变量不可修改，如果是对象那么不可变的是引用问题。可以避免意外复制导致的变成错误。更加方便的是在并发编程上，我们可以避免额外的同步 ，省去了一部分的拷贝消耗
  - 使用在方法上，表示该方法不可重写。


finally我们通常用来与try -catch 一块使用。 比如下面锁的引用，我们一般会在使用的时候最终将锁进行释放
```
   try{
            lock.lock();
            lock.getHoldCount();
            lock.getQueueLength();
            lock.getWaitQueueLength(condition);
            lock.hasQueuedThread(Thread threads) ; //作用查询指定的线程是否在等待获取此锁定
            lock.hasQueuedThreads(); //查询 是否有线程正在等待获取此锁定。
            lock.hasWaiters(condition); // 查询是否有线程正在等待此锁定有关的condition条件。
            lock.isFair(); // 判断是不是公平锁
            lock.isHeldByCurrentThread(); // 查询当前线程是否保持此锁定
            lock.isLocked(); //作用是查询此锁定是否由任意线程保持。
            lock.lockInterruptibly(); // 如果当前线程未被中断，则获取锁定。如果已经被中断则出现异常
            lock.tryLock();  //仅在 调用时锁定未被另一个线程保持的情况下，才获取该锁定
            condition.await();
            condition.signal();
            condition.signalAll();
            System.out.println("打印内容: " + Instant.now());
            Object object = new Object();
            Thread thread  = new Thread();
            thread.join();

            lock.wait();
            lock.notify();
            lock.notifyAll();

            ReentrantReadWriteLock  readWriteLock = new ReentrantReadWriteLock();
            readWriteLock.readLock().lock();
            readWriteLock.writeLock().lock();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();

        }
```
但是在Java7以后我们可以使用try -with-resources ,这样能减少我们的编码量。让代码更加整洁
```
 try(BufferedInputStream  iso = new BufferedInputStream(new InputStream() {
            @Override
            public int read() throws IOException {
                return 0;
            }
        });
        ){
            System.out.println("打印内容");
        }catch (Exception e){
        }
```
还有一点就是finally中代码执行的问题。
如果try中有return finally中也有return 那么结果会是哪个呢？ 答案是我们会的得到return 中返回的结果。因为在try中放回的结果因为是在方法内那么结果内容是放在栈中的，finally中的结果会把该结果覆盖掉。但是这个是说的引用对象。对于值的返回是没有影响的。
我们常说finally中的代码如果不出错的情况下一定会执行。那finally不会别执行的情况是什么呢？
```
 try{
            System.exit(1);  //程序在这里执行退出 finally就不执行了
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            System.out.println("输出");
        }
 int  i = 1/0 ;   //在这里发生异常 程序直接 执行退出。
 try{
            System.exit(1);  //程序在这里执行退出 finally就不执行了
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            System.out.println("输出");
        }
```
finalize  是被设计成在对象被垃圾回收前调用，该方法作用是清理一些垃圾回收不能处理的特殊情况内存空间。虽然会在垃圾回收前调用，但是在下一次垃圾回收动作的时候才会真正的执行清理空间。每次在垃圾回收前使用，JVM每次进行额外处理。就会每次增加JVM的压力。所以不建议使用该方式去释放特殊空间的内存。
如今的Java平台，开始采用java.lang.ref.Cleaner 代替finalize.Cleaner 的实现使用了幻象引用。这是一种常见的post-mortem清理机制。这个Cleaner 的操作都是独立的，有自己的运行线程，避免意外死锁的问题。
## 引用的几种分类
  在平常代码中，我们基本上没有注意过引用的使用，那么引用究竟是什么呢。或者有几种类型呢？
引用我们一般分为 **强引用**，**软引用**，**弱引用**，  **幻象引用**四类型
 我们使用不同的引用类型，一主要是体现在对象的不同的可达性状态和对垃圾收集的影响。
### 强引用  
   **强引用**是指我们平常普通对象的引用，只要还有一个强引用指向对象，那么代表对象还是存活的状态，这样垃圾收集器在进行回收的时候就不会进行操作。如果没有其他对象对其引用了或者超过了作用域的范围会显示的将其强引用赋值为null ,进行垃圾回收。如果一直保持着强引用状态。垃圾回收器是不会进行回收的。
### 软引用
   **软引用**是相对于强引用弱化的一种引用，他可以让对象豁免一些垃圾回收，只有当JVM认为内存不组的时候才会进行回收。软引用是实现内存敏感的缓存，如果有空间内存，就可以暂时保留缓存。当内存不足才清理掉。但是垃圾回收的时候会进行二次群人是否保持的**软引用**的情况。保持这个状态才会进行清理掉
### 弱引用
   **弱引用**并不能是对象豁免垃圾回收进行收集，仅仅是提供一种访问在弱引用状态下对象的途径。这就可以构建一种没有特定约束的关系。当试图获取时对象还在，那么就使用，否则重新实例化，这种方式在缓存中使用比较多。但是垃圾回收的时候会进行二次群人是否保持的**弱引用**的情况。保持这个状态才会进行清理掉
### 幻象引用
   **幻象引用**，我们没有手段去进行访问幻象引用，这种引用只是用来提供对象被finallze以后，做某些事情的机制。比如利用幻象引用进行对象的创建和销毁。

