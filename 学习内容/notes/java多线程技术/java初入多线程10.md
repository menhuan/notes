#### 线程阻塞工具类 ：LockSupport
- LockSupport 是一个非常实用的线程阻塞工具， 可以在线程内任意位置，让线程阻塞。 这个类补充了  使用resume 导致线程无法继续执行的情况，和wait方法相比 ，不需要获得某个对象的锁 ，也不会抛出 中断异常。
```
public class LockSupportDemo {

	public static Object U = new Object() ;
	static ChangeObjectThread t1 = new ChangeObjectThread("t1");
	static ChangeObjectThread t2 = new ChangeObjectThread("t2");
	public static class ChangeObjectThread extends Thread{
		public ChangeObjectThread (String name){
			super.setName(name);
		}
		
		@Override
		public void run() {
			synchronized(U){
				System.out.println("in " + getName());
				LockSupport.park();
				
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		t1.start();
		Thread.sleep(100);
		t2.start();
		LockSupport.unpark(t1);
		LockSupport.unpark(t2);
		t1.join();
		t2.join();
	}
}
```
- 在该代码中，我们使用的是方法park(),和unpark(); 该类为每个线程准备了一个许可， 如果许可可用，那么park()函数就会立即返回，消费掉这个许可，。 如果许可不可用，就会发生阻塞。这个优点是即使 unpark ()发生在park上 ，也能顺利的保证park操作正确执行。
- 除了有定时阻塞的功能之外， park()还支持 中断影响,但是与其他中断函数不一样，park()函数 不会抛出中断异常， 只是默默返回。 但是我们可以用 Thread.interupted() 等方法获得中断标记。代码演示如下......
```
public class LockSupportIntDemo {

	public static Object U = new Object() ;
	static ChangeObjectThread t1 = new ChangeObjectThread("t1");
	static ChangeObjectThread t2 = new ChangeObjectThread("t2");
	
	public static class ChangeObjectThread extends Thread{
		public ChangeObjectThread (String name){
			super.setName(name);
		}
		
		@Override
		public void run() {
			synchronized(U){
				System.out.println("in " + getName());
				LockSupport.park();
				if(Thread.interrupted()){
					System.out.println(getName() + "被中断了");
				}
			}
			System.out.println(getName() + "执行结束了");
		}
	}

	public static void main(String[] args) throws Exception {
		t1.start();
		Thread.sleep(100);
		t2.start();
		t1.interrupt();
		LockSupport.unpark(t2);
	}
}
```
![临界](http://upload-images.jianshu.io/upload_images/4237685-4d38eba3f8caa90e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

#### 线程复用：线程池
- 线程池是为了方便我们对创建的线程进行复用。节约了创建和销毁对象的时间。

![线程池的使用](http://upload-images.jianshu.io/upload_images/4237685-5381aec5e028c3ff.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

##### jdk 对线程池的使用。
1. jdk 提供了一套Executor 框架， 本质就是线程池的使用。

![Executor框架](http://upload-images.jianshu.io/upload_images/4237685-c15b57ad32b13c51.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
- 从图中可以看到ThreadPoolExecutor 表示一个线程池， Executors 类则扮演者线程池工厂的角色。 通过Executors 可以获得拥有特定功能的线程池。 我们在uml图上 ，可以看到Executor 接口 ，我们通过该接口 让Runnable 的对象可以被ThreadPoolExecutor 线程池调度。
- Executor  框架提供了各种类型线程池。工厂方法如下：
1. newFixedThreadPool(int nThreads);  该方法返回的是一个固定线程数量的线程池，该线程池的线程数量始终不变。 当有新任务提交过来的时候 ，线程池中有空闲线程，则立即执行。若没有 ，则会把新的任务放到一个队列中。等待有空闲线程的时候就会执行队列中的任务。
2. newSingleThreadExecutor(): 方法会返回一个只有一个线程的线程池。若多余一个任务被提交到该线程池。任务同样会保存在任务队列中，等待线程空闲，按照先入先出的顺序 执行队列中的任务。
3. newCachedThreadPool() 方法： 方法会返回一个根据实际情况调整线程数量的线程池。线程池的里面的线程数量不确定。在任务提交过来的时候，若有空闲线程 则直接复用空闲线程，若所有线程都在使用，那么会创建新的线程处理任务。所有线程结束后，将返回线程池进行复用。
4. newSingThreadScheduleExecutor(): 该方法会返回一个ScheduledExecutorService对象，线程池大小为1， 这个线程池是在给定任务时间执行某任务的功能，或者是周期性执行任务
5. newSheduledThreadPool(): 方法会返回ScheduledExecutorService对象，该线程池可以指定线程数量。
- 对计划任务线程池的使用：newSheduledThreadPool():
1. 主要方法如下 对该线程里面的设置。
```
/**下面两种方法 会在给定时间进行一次任务调度。**/
    public ScheduledFuture<?> schedule(Runnable command,
       long delay, TimeUnit unit);

    public <V> ScheduledFuture<V> schedule(Callable<V> callable,    long delay, TimeUnit unit);
/*** 该方法是对任务周期性的调度  fixRate 是任务调度频率是一致的**/
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable command,   long initialDelay,  long period, TimeUnit unit);
/***同样 周期性的调度 FixedDelay 是在任务完成一周周期后 再经过某个时间长度 才会执行任务**/
    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command,long initialDelay,
 long delay,  TimeUnit unit);
//代码演示如下：
public class ScheduledExecutorServiceDemo {

	
	public static void main(String[] args) {
		ScheduledExecutorService  service = Executors.newScheduledThreadPool(10);
		service.scheduleAtFixedRate(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(1000);
					System.out.println(System.currentTimeMillis() / 1000);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, 0, 2, TimeUnit.SECONDS);
	}
}
```
![每隔两秒](http://upload-images.jianshu.io/upload_images/4237685-74261432b82118a2.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
2. 还有一种情况如果任务执行时间超过我们所规定的时间 ，会怎么样，是聚集还是？ 答案就是 会持续的当当前任务执行完成后立即执行下面的任务，。还有一个如果任务出现异常，那么该任务会被中断 后续的任务也会被中断，那么需要我们最好异常处理。
