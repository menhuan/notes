
在上篇文章中我们了解到了Java是怎么来建立自带的线程池的，虽然Java中提供了多种线程池，但是我们还是在某些场景下需要实现自己的线程池操作。
# 新建立线程
在阿里的指导手册里面，也是建议自己使用该方式进行创建线程池。
![新建线程池](https://upload-images.jianshu.io/upload_images/4237685-c391b3a24573c230.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
```
 ExecutorService   service =   ExecutorService   service = new ThreadPoolExecutor(1, 10, 0L,
                TimeUnit.MINUTES, new LinkedBlockingDeque<>(), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return null;
            }
        }, new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {

            }
        });
```
* corePoolSize：核心线程数，我们可以认为是当启动后一直能长期运行的线程数量。
* maximumPoolSize ： 最大线程数，线程不够时，在程序中最大的能扩展的线程数量。不同的线程池，该数值有不同的含义
* keepAliveTime与TimeUnit： 使用该参数来控制多余的线程空余的时间。
* 对列：最后就是线程池使用的队列类型了。可选的有很多种，但是必须是阻塞队列BlockingQueue。
* ThreadFactory ： 创建线程的 工厂方法
* RejectedExecutionHandler ：拒绝策略。拒绝策略有四种默认存在的，还有一种是可以自定义
  1. **AbortPolicy** ： 该策略是线程池的默认策略，在使用该方式的时候，如果线程池队列满了，那么就会碟调新的提交任务，并且抛出RejectedExecutionException异常。
```
 new ThreadPoolExecutor.AbortPolicy();
 public static class AbortPolicy implements RejectedExecutionHandler {
        /**
         * Creates an {@code AbortPolicy}.
         */
        public AbortPolicy() { }

        /**
         * Always throws RejectedExecutionException.
         *
         * @param r the runnable task requested to be executed
         * @param e the executor attempting to execute this task
         * @throws RejectedExecutionException always
         */
        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            //直接抛出异常
            throw new RejectedExecutionException("Task " + r.toString() +
                                                 " rejected from " +
                                                 e.toString());
        }
    }
```
2. **DiscardPolicy** ： 是AbortPolicy的slient版本，线程池队列满了，会丢掉任务，并且不会又异常抛出。
```
 public static class DiscardPolicy implements RejectedExecutionHandler {
        /**
         * Creates a {@code DiscardPolicy}.
         */
        public DiscardPolicy() { }

        /**
         * Does nothing, which has the effect of discarding task r.
         *
         * @param r the runnable task requested to be executed
         * @param e the executor attempting to execute this task
         */
        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
        }
    }
```
3. **DiscardOldestPolicy** : 丢弃最老的任务。队列满了会把最早进入的任务进行删除，在尝试进入队列。
```
 public static class DiscardOldestPolicy implements RejectedExecutionHandler {
        /**
         * Creates a {@code DiscardOldestPolicy} for the given executor.
         */
        public DiscardOldestPolicy() { }

        /**
         * Obtains and ignores the next task that the executor
         * would otherwise execute, if one is immediately available,
         * and then retries execution of task r, unless the executor
         * is shut down, in which case task r is instead discarded.
         *
         * @param r the runnable task requested to be executed
         * @param e the executor attempting to execute this task
         */
        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            if (!e.isShutdown()) {
                e.getQueue().poll(); //从头部移除。
                e.execute(r);
            }
        }
    }
```
4. **CallerRunsPolicy**:当添加线程池失败的时候，主线程会自己去执行该任务，不会等待线程池中的线程去执行。
```
 public static class CallerRunsPolicy implements RejectedExecutionHandler {
        /**
         * Creates a {@code CallerRunsPolicy}.
         */
        public CallerRunsPolicy() { }

        /**
         * Executes task r in the caller's thread, unless the executor
         * has been shut down, in which case the task is discarded.
         *
         * @param r the runnable task requested to be executed
         * @param e the executor attempting to execute this task
         */
        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            if (!e.isShutdown()) {
                r.run();
            }
        }
    }
```
5. **自定义线程池**：写实现类实现RejectedExecutionHandler 。来实现自己的拒绝策略
```
public class RejectHander implements RejectedExecutionHandler{
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
         //写自己的业务拒绝策略 
    }
}

```
整个线程池执行流程 。
1. 任务提交 小于corePoolSize就分配线程执行。 
2. 大于corePoolSize 提交到等待队列
3. 成功那么就等待执行。
4. 失败提交线程池进行判断是否大于maximumPoolSize ，提交失败那么拒绝执行。
5. 小于maximumPoolSize ，那么就分配线程进行任务的执行。
![执行流程](https://upload-images.jianshu.io/upload_images/4237685-aa6392e516ef448b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
