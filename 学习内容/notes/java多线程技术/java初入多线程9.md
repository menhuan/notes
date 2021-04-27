#### ReadWriteLock 多写锁
1. ReadWriteLock 是JDK5中提供的读写分离锁，读写分离可以有效的帮助减少锁竞争。用来提高系统性能。
读写锁的访问约束情况

|  |  读  | 写  |  
- | :-: | :-:
读 | 非阻塞 | 阻塞
写 | 堵塞    |  阻塞 
- 读读 之间不互斥：读读之间不阻塞
- 读-写互斥：读阻塞写，写也会阻塞读
- 写- 写 互斥： 写写堵塞。
```
public class ReadWriteLockDemo {
	private static Lock lock = new ReentrantLock();
	private static ReentrantReadWriteLock readWriteLock = new  ReentrantReadWriteLock();
	
	private static Lock readLock = readWriteLock.readLock();
	private static Lock writeLock =readWriteLock.writeLock();
	
	private int value ;
	
	
	public Object handleRead(Lock lock) throws InterruptedException {
		try {
			lock.lock();
			Thread.sleep(1000);
			return value;
			
		}finally {
			lock.unlock();
		}
	}
	
	public void handleWrite(Lock lock,int index) throws InterruptedException{
		try {
			lock.lock();  //模拟写的操作
			Thread.sleep(1000);
			value=index;
		} finally {
			lock.unlock();
		}
	}
	
	public static void main(String[] args) {
		final ReadWriteLockDemo  demo= new ReadWriteLockDemo();
		Runnable readRunnable =new Runnable() {
			
			public void run() {
				try {
//					demo.handleRead(readLock);
					demo.handleRead(lock);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		};
		
		Runnable writerRunnable =new Runnable() {
			
			public void run() {
				try {
//					demo.handleWrite(writeLock, new Random().nextInt());
					demo.handleWrite(lock, new Random().nextInt());
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		};
		
		for (int i = 18; i < 20; i++) {
			new Thread(writerRunnable).start();
		}
	}
	
}

```
- 在代码中我们使用了两种方式 如果使用读写锁的该程序执行可以在2秒内执行完毕，但是如果是lock锁的没有进行多写分离，那么会在将近20秒的时间内完成。为什么会这么长时间 ，是因为所有的读线程与写线程之间 必须都互相等待 ，导致的。读写分离之后 读读之间不用互相等待 ，大大减少时间。

### 倒计时器：CountDownLatch
- CountDownLatch 是一个非常实用的多线程控制工具类。主要是用来控制线程等待，它可以让某一个线程等待到知道倒计时结束再开始执行。代码演示如下：
```
public class CountDownLatchDemo implements Runnable {

	static final CountDownLatch end = new  CountDownLatch(10);
	static final CountDownLatchDemo  demo = new CountDownLatchDemo();
	
	
	@Override
	public void run() {
		
		try {
			
			//模拟检查任务 
			Thread.sleep(new Random().nextInt(10)*1000);
			System.out.println("check complete");
			end.countDown();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public static void main(String[] args) throws Exception {
		ExecutorService exec = new  ThreadPoolExecutor(10, 10,
				  0L, TimeUnit.MILLISECONDS,
			        new LinkedBlockingQueue<Runnable>());
		for(int i = 0 ; i < 10 ; i++){
			exec.submit(demo);
		}
		
		//等待检查
		end.await();
		
		// 发射  相当于开始执行任务
		System.out.println("fire");
		
		exec.shutdown();
	}
}

```
![结果图](http://upload-images.jianshu.io/upload_images/4237685-8f0c4f0ee8ff15b0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
- 在上述代码中 计数器数量为10 ，那么就是需要有10个线程完成任务，在CountDownLatch 上的线程才能继续执行。 在代码中我们能看到有个countdown()方法， 就是用来通知CountDownLatch， 如果一个线程完成任务，那么倒计时器就可以减1.。 我们在await 方法，要求主线程等待所有10个检查任务全部完成，那么主线程才会继续执行。

![示意图](http://upload-images.jianshu.io/upload_images/4237685-69a46d9159fc613f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

#### 循环栅栏 ：CyclicBarrier
- 这是一种不同于 CountDOwnLatch 的多线程并发控制工具，但是也可以实现线程间的计数等待。它比CountDownLatch 功能更加强大且复杂。
- 循环栅栏 意思就是循环，如果我们使用的是其计数器的功能那么如果是20个，等到计数器归0 之后还会凑齐下一批20个线程，再次形成栅栏。代码如下：
```
public class CyclicBarrierDemo {
		
	
	public static class Soldier implements Runnable{
		private String soldier;
		private final  CyclicBarrier cyclic ;
		
		public Soldier( CyclicBarrier cyclic , String soldier) {
			this.cyclic = cyclic ;
			this.soldier = soldier ;
		}
		
		@Override
		public void run() {
			try {
				//等到所有任务的到来
				cyclic.await() ;
				doWork();
				
				//等待所有任务完成
				cyclic.await();
				
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		
		}
		
		void doWork(){
			try {
				Thread.sleep(Math.abs(new Random().nextInt()%10_000));
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static class BarrierRun implements Runnable {
		boolean flag ;
		int  N ;
		public BarrierRun(boolean flag, int n) {
			this.flag = flag;
			N = n;
		} 
		
		@Override
		public void run() {
			if(flag){
				System.out.println("司令 ： 士兵"+ N + "个,任务完成");
			}else{
				System.out.println("司令 ： 士兵"+ N + "个,集合完毕");
			}
			
		}
		
		
	}
	public static void main(String[] args) {
		final int N = 10 ;
		Thread[] allSoldier = new Thread[N];
		boolean flag = false ; 
		CyclicBarrier cyclic =new CyclicBarrier(N, new BarrierRun(flag, N));
		
		//设置屏障点,主要是为了执行这个方法
		System.out.println("集合队伍！");
		
		for(int i = 0 ; i < N ; i++ ){
			System.out.println("士兵 "+ i + " 报道 ");
			allSoldier[i] = new  Thread(new Soldier(cyclic, " 士兵 " + i));
			allSoldier[i].start();
		}
		
	}
}
```
- 根据代码中，我们可以发现 执行await 方法的时候 可能会抛出异常， 一个是等待异常InterruptedException，这个是方便相应 外部紧急事件，另外一个异常是BrokenBarrierException ，这个表示 CyclicBarrier  已经损坏， 系统没法等到所有线程然后再开始执行，处理是把所有线程中断 。就避免 线程的永久的等待了
![执行结果](http://upload-images.jianshu.io/upload_images/4237685-2514c696c198dd50.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
- 整个过程的流程图如下：
![示意图](http://upload-images.jianshu.io/upload_images/4237685-04b2d8ce54e9c66b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
