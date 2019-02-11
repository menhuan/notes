最近开始自己找工作了。虽然一直在写这方面的文章，还是有些知识掌握的不牢固，希望看文章的朋友也有自己的总结认真的学习到这些内容。一起共勉。
做java中经常问道的一个问题就是线程池用过吗？通用的有哪些？如果不用通用的我们应该怎么创建线程池？这些问题。今天自己也做下总结。虽然原先也写过。现在再总结下，让自己能弄明白。
# 静态创建线程池
我们平常使用的大部分还是依靠java中自带的静态工厂产生的线程池。先了解下自带的线程池。
## newFixedThreadPool（int numThreads）
 该线程池会在初始化的指定线程数量。具有以下的特点
 1. 在任何时刻都是最多有numThreads的线程数量活动。如果超过限制会在LinkedBlockingQueue中等待。
 2. 当达到核心线程数的时候，线程数不在继续增加。
3. 有工作线程退出，新的工作线程被创建来达到设置的线程数。
```
    Executors.newFixedThreadPool(1);
    //源码实现
    public static ExecutorService newFixedThreadPool(int nThreads) {
        return new ThreadPoolExecutor(nThreads, nThreads,
                                      0L, TimeUnit.MILLISECONDS,
                                      new LinkedBlockingQueue<Runnable>());
    }
```
## newCachedThreadPool（）
该线程池是一个可以用来缓存的线程池。
1. 尝试缓存使用过的线程。
2. 没有缓存的线程池时，就会使用工厂方法创建线程池，最大的容量理论上是Integer.MAX_VALUE。这个跟服务器的配置有关。所以如果过来大量的线程任务，那么该线程池会在瞬间创建多个线程来执行工作。
3. 时间上多余的线程超过60秒会被终止移除缓存中。
4. 内部使用的SynchronousQueue实现的队列，该队列在实现的时候没有容量。适合来做交换的工作。
```
 Executors.newCachedThreadPool();
//源码实现
  public static ExecutorService newCachedThreadPool() {
        return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                                      60L, TimeUnit.SECONDS,
                                      new SynchronousQueue<Runnable>());
    }
```
## newSingleThreadExecutor
该线程池是用来设置单个的线程池，使用的队列是无界队列。
1. 因为提交的任务只要一个是能活动的，剩下的是放到无界队列中。
2. 队列是先进先出的。那么执行顺序是有序的。
```
    Executors.newSingleThreadExecutor();
   //源码
   public static ExecutorService newSingleThreadExecutor() {
        return new FinalizableDelegatedExecutorService
            (new ThreadPoolExecutor(1, 1,
                                    0L, TimeUnit.MILLISECONDS,
                                    new LinkedBlockingQueue<Runnable>()));
    }

```
## newWorkStealingPool
工作线程，该线程是在jdk1.8以后新增加的.使用的是ForkJoinPool创建线程池。该任务执行没有顺序。需要考虑到硬件的cpu核心数。
```
Executors.newWorkStealingPool();
 public static ExecutorService newWorkStealingPool() {
        return new ForkJoinPool
            (Runtime.getRuntime().availableProcessors(),  //默认使用的是硬件的cpu数目
             ForkJoinPool.defaultForkJoinWorkerThreadFactory,
             null, true);
    }
Executors.newWorkStealingPool(10);
  public static ExecutorService newWorkStealingPool(int parallelism) {
        return new ForkJoinPool
            (parallelism,   // 使用的是自定义的核心数
             ForkJoinPool.defaultForkJoinWorkerThreadFactory,
             null, true);
    }
```
## newSingleThreadScheduledExecutor（）与newScheduledThreadPool（int poolSize）

周期性的调度执行任务的线程池。 单个执行。
```
    Executors.newSingleThreadScheduledExecutor();
    public static ScheduledExecutorService newSingleThreadScheduledExecutor() {
        return new DelegatedScheduledExecutorService
            (new ScheduledThreadPoolExecutor(1));
    }
//核心周期执行的线程数
 Executors.newScheduledThreadPool(10);
public static ScheduledExecutorService newScheduledThreadPool(int corePoolSize) {
        return new ScheduledThreadPoolExecutor(corePoolSize);
    }
```
了解其java内部的静态线程池，并且在程序中使用其策略。当然在这种我们没有看到的是线程池的拒绝策略。无界队列和有界队列，核心数的与最大的核心数的设置，这些不同。那么我们的执行拒绝策略也是不同的。在接下里的文章我们会具体了解拒绝策略。
