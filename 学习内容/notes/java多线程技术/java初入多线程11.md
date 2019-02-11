#### 核心线程池的内部实现机制。
1. **阿里巴巴 code检验推荐自己实现线程池的创建。不是使用Executors的创建方法。**
2. **ThreadPoolExecutor 类的封装实现。**
```
  //参数最多的构造方法
  public ThreadPoolExecutor(int corePoolSize,
                              int maximumPoolSize,
                              long keepAliveTime,
                              TimeUnit unit,
                              BlockingQueue<Runnable> workQueue,
                              ThreadFactory threadFactory,
                              RejectedExecutionHandler handler)
```
- corePoolSize : 指定了线程池中的线程数量。
- maximumPoolSize：指定了线程池中最大的线程数量。
- keepAliveTime ： 当线程池数量超过corePoolSize时， 多余的空余线程的存活时间。即是 超过corePoolSize的 空闲线程，在多长时间内会被销毁。
- unit : keepAliveTime 的 单位。
- workQueue ： 任务队列，被提交但尚未被执行的任务。
- threadFactory : 线程工厂，用于创建线程 ，一般用默认的就好。
- handler ：拒绝策略， 当任务太多来不及处理，如何拒绝任务。
3. **详细介绍 队列的使用workQueue 。**
- 直接提交的队列：SynchronousQueue 是一个特殊的BlockQueue。该队列没有容量，一个插入就需要等待一个删除操作。删除操作也要等待插入的操作。使用该队列，那么任务不会被真实的保存，而是把任务提交给线程，没有空闲的线程，尝试创建新的进程，如果进场数量已经达到最大值，就执行拒绝策略。所以在使用该队列的时候，设置 很大的maximumPoolSize值，否则很容易出现拒绝策略。
- 有界的任务队列： ArrayBlockingQueue 实现。 使用时需要制定该队列的容量参数 。 如果有新 的任务执行。在线程池的实际线程数量小于 corePoolSize ，则优先创建新的线程若大于corePoolSize 则会将新任务加入到队列中。 若等待队列已满，在总线程数小于maximumPoolSize 的前提下，创建新的进程执行任务，若大于maximumPoolSize，那么会执行拒绝策略。有界队列是在该队列满的时候才会去增加线程数量超过corePoolSize。
- 无界的任务队列：使用的是LindedBlockingQueue实现，因为是无界的任务队列，所以除非资源被耗尽，否则无界队列不存在任务入队失败的情况。以下情况需要注意：新任务过来时，当线程数量小于corePoolSize,线程救回生成新的线程执行任务，但是当系统的线程数达到corePoolSize 后，就不会继续增加。若后续有新任务加入，又没有空闲的线程资源。则任务进入队列等待。
- 优先任务队列： 优先任务队列带有执行优先级的队列。他通过PriorityBlockingQueue 实现。 可以控制任务的执行的先后顺序。它是一个特殊的无界队列。我们前面所说的ArrayBlockingQueue 还有LinkedBlockingQueue 都是按照先进先出的算法处理任务的。而PriorityBlockingQueue 则可以根据任务自身的优先级顺序先后执行，在确保系统性能的同时，很好的保证了质量保证。

![线程池的任务调度逻辑](http://upload-images.jianshu.io/upload_images/4237685-92c9020bf1d0d591.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
#### 超负载了怎么办：拒绝策略
1. **ThreadPoolExecutor 的最后一个参数指定了拒绝策略，系统中有四种拒绝策略**
![拒绝策略](http://upload-images.jianshu.io/upload_images/4237685-2b85ae5cb410d02f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
- AbortPolicy ： 该策略会直接抛出异常，组织系统正常工作。
- CallerRunsPolicy ： 只要线程池没有关闭，该策略直接在调用者线程中，运行当前被丢弃的任务。显然这样做不会丢弃任务，但是任务提交线程的性能极有可能急速下降。
- DiscardOldersPlocy ： 该策略会丢弃最老的一个请求，也就是即将执行 的一个 任务，并尝试在此提交当前任务。
- DiscardPolicy ： 该策略会默默的丢弃无法处理的任务，不在处理。如果允许任务丢失 该策略应该是最好的。
- 以上4个策略都是事先了RejectExecutionHandler 接口 ,可以自己去扩展该接口 实现自定义的拒绝策略。 代码演示如下
```
public class RejectedThreadPoolDemo {

		public static class MyTask implements Runnable{

			@Override
			public void run() {
				System.out.println(System.currentTimeMillis() + " :Thread ID : " + Thread.currentThread().getId());
				
				try {
					Thread.sleep(100);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			
			
		}
		public static void main(String[] args) throws Exception {
			MyTask task = new MyTask() ;
			// 定了一个线程池  自定义的  并且自定了拒绝策略
			ExecutorService es  =new ThreadPoolExecutor(5, 5, 0l, TimeUnit.MICROSECONDS, new LinkedBlockingQueue<>(),
					Executors.defaultThreadFactory(),new RejectedExecutionHandler() {
						
						@Override
						public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
							System.out.println(r.toString() + " is discard ");
						}
					});
			
			for(int i = 0 ; i < 5 ; i++) {
				es.submit(task);
				Thread.sleep(10);
			}
			
	  }
	
}
```
