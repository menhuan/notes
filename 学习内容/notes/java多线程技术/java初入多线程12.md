#### 自定义线程创建：ThreadFactory
1. 我们原先用的线程池ThreadPoolExecutor  里面的线程都是从ThreadFactory 创建的。
2.作用：我们可以根据自定义线程池，帮助我们跟踪线程池创建了多少个线程，也可以自定义线程的名称，组以及优先级等信息。甚至可以将所有线程设置为守护线程。代码演示如下：
```
public class ThreadFactoryTask {
	
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
		MyTask  task =  new MyTask() ;
		ExecutorService es = new ThreadPoolExecutor(5,5,0l,
				TimeUnit.MILLISECONDS,new SynchronousQueue<>(),
				new ThreadFactory() {
					
					@Override
					public Thread newThread(Runnable r) {
						Thread thread = new Thread(r);
						thread.setDaemon(true);
						System.out.println("create "+ thread);
						
						return thread;
					}
				});
		for(int i = 0 ; i < 5 ; i++) {
			es.submit(task);
		}
		Thread.sleep(2000);
	}
}
```
#### 扩展线程池
1. 使用ThreadPoolExecutor 扩展，使用beforeExecute(),afterExecute(), terminated()三个接口对线程池进行控制。代码演示如下：
```
public class ExtThreadPool {
	
	public static class MyTask implements Runnable{
		public String name;
		
		
		
		public MyTask(String name) {
			super();
			this.name = name;
		}



		@Override
		public void run() {
			System.out.println(System.currentTimeMillis() + " :Thread ID : " + Thread.currentThread().getId()
						+", Task Name= " + name);
			
			try {
				Thread.sleep(100);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	
	public static void main(String[] args) throws Exception {
		
		ExecutorService es = new ThreadPoolExecutor(5,5,0l,
				TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>()
				) {
				@Override
				protected void beforeExecute(Thread t, Runnable r) {
					System.out.println(" 准备执行： "+ ((MyTask)r).name);
				}
				
				@Override
				protected void afterExecute(Runnable r, Throwable t) {
					System.out.println(" 执行完成： "+ ((MyTask)r).name);
				}
				@Override
				protected void terminated() {
					System.out.println(" 线程池推出： ");
				}
		};
		for(int i = 0 ; i < 5 ; i++) {
			MyTask task = new MyTask("TASK-GEYM-" + i);
			es.execute(task);
			Thread.sleep(10);
		}	
		es.shutdown();
	}
}
```
![执行结果](http://upload-images.jianshu.io/upload_images/4237685-b74bddac12ec82ba.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

shutdown 关闭线程池，是一个比较安全的关闭线程池，有任务执行执行，不会暴力的终止任务。而是等待任务完成在关闭，再次之后不再接收新的任务过来。
#### 优化线程池的线程数量

![使用优化的公式](http://upload-images.jianshu.io/upload_images/4237685-7949b42b2e5db889.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

#### Fork/Join 框架
1. 思想是“分而治之”，采用分化的方式来提高程序执行的效率。执行效果逻辑如图所示
![逻辑图所示](http://upload-images.jianshu.io/upload_images/4237685-bc668b5acac2aa2f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![互助线程](http://upload-images.jianshu.io/upload_images/4237685-dbedee72fdb17e1c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
互助线程的出现是因为线程池的优化，在实际的执行过程中，如果有两个线程A,B .A把任务执行完成后如果B任务还没有执行完成，A就会去帮助B完成任务，当线程打算去帮助另外的线程的时候，两个线程取任务的时候也是从相反的方向取任务，这也减少 了线程之间的任务争夺情况的出现。
2. ForkJoinPool的接口介绍
```
  public <T> ForkJoinTask<T> submit(ForkJoinTask<T> task)；
```
- ForkJoinTask 任务就是支持fork 分解和join 等待的任务。 我们主要使用ForkJoinTask的两个子类 ，分别为RecursiveAction 和 RecursveTask  ,分别为没有返回值的任务，和可以携带返回值的任务。代码演示如下。
```
public class CountTask extends RecursiveTask<Long> {

	private static final int THRESHOLD = 10_000 ;
	
	private long start ;

	private long end ;
	
	
	public CountTask(long start, long end) {
		super();
		this.start = start;
		this.end = end;
	}


	@Override
	protected Long compute() {
	
		long sum = 0 ;
		boolean canCompute = (end - start ) <THRESHOLD ;
		
		if(canCompute) {
			for(long i = start ; i <= end ;i++ ) {
				sum +=i;
			}
		}else {
			//分成多个任务
			long step = (start + end ) /100;
			ArrayList<CountTask> subTasks = new ArrayList<CountTask>();
			long pos = start ;
			int index = 100;
			for ( int i = 0 ; i < index ; i++) {
				long  lastOne = pos + step ;
				if(lastOne > end ) {
					lastOne = end;
				}
				
				CountTask subTask = new CountTask(pos, lastOne);
				pos += step+1 ;
				subTasks.add(subTask);
				subTask.fork();//提交子任务用来
			}
			
			for (CountTask countTask : subTasks) {
					sum += countTask.join();
				
			}
		}
		
		return sum ;
	}

	
	public static void main(String[] args) {
		ForkJoinPool forkJoinPool = new ForkJoinPool() ;
		
		CountTask  task = new CountTask(0, 200_000l);
		
		ForkJoinTask<Long> result = forkJoinPool.submit(task);
		try {
			long res = result.get();
			System.out.println(" sum= "+res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
```
- 此外，ForkJoin 是用一个无锁的栈来管理空闲线程，如果一个工作线程取不到可用的任务，则可能被挂起，挂起的线程会被压如由线程池维护的栈中。有需要再唤醒线程。并且如果形成的任务过多容易出现内容溢出的情况。
