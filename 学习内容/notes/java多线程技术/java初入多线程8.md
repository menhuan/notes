#### 重入锁的好搭档：Condition条件
1. Condition 条件与Object 的wait 和Object.notify 方法类似。
2. Condition 有如下基本方法：
- await() 方法会使当前献策好难过等待，同时释放当前锁，当其他线程中使用signal() 或者使用signalAll()方法时，线程会重新获得锁并继续执行。或者当被中断的时候也能跳出等待。
- awaitUninterruptibly() 与await类似 但是该方法不会再等待的过程中响应中断。
- singal() 方法用于唤醒 一个等待中的线程。singalAll唤醒所有在等待中的线程。
演示代码如下：
```
public class ReenterLockCondition implements Runnable {

	public static ReentrantLock lock =new ReentrantLock();
	
	public static Condition condtionCondition =lock.newCondition(); //生成一个Condition对象
	
	public void run() {
			
		try {
			lock.lock();
			condtionCondition.await();  //执行这里的时候要求有相关的重入锁，在调用之后会释放锁
			System.out.println("Thread is going on");
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			lock.unlock();
		}
		
	}
	
	public static void main(String[] args) throws InterruptedException {
		ReenterLockCondition t2=new ReenterLockCondition();
		Thread t1=new Thread(t2);
		t1.start();
		
		Thread.sleep(2000);
		lock.lock();
		condtionCondition.signal();  //发出通告  ，并且要求其先获得相关锁。 
		lock.unlock();  //释放重入锁
	}

}
//相关具体更好的代码操作可以看ArrayBlockingQueuel例子中的put方法jdk1.7的
```
代码执行结果

![执行结果](http://upload-images.jianshu.io/upload_images/4237685-8cbe8a31002879b4.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


#### 允许多个线程同时访问：信号量
1. 信号量为多线程协作提供了更为强大的控制方法，广义的说是对锁的扩展。无论是内部锁synchronized还是重入锁ReentrantLock 。一次都只允许一个线程访问一个资源。而信号量可以指定多个线程同时访问某一个资源。
2. 构造方法如下：
- Semaphore(int permits);  
- Semaphore(int permits,boolean fair) ; // 第二个参数可以指定时候公平
- 在使用构造信号量的时候要指定准入的信号量的数量。同时可以申请多个许可
```
public class SemapDemo implements Runnable {

	final Semaphore semp =new Semaphore(5);
	
	public void run() {
		try {
			semp.acquire();  //获取信号量 每次能进来5个线程
			Thread.sleep(2000);
			System.out.println(Thread.currentThread().getId() + ":done!");
			semp.release();  // 如果这里信号量泄露 没有释放 那么会导致进入临界区的线程数量越来越少。直到所有的线程不能再访问
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	public static void main(String[] args) {
                //开启20个线程 去访问，会发现是5个线程一组数据
		ExecutorService  executionn= Executors.newFixedThreadPool(20);
		final SemapDemo demo =new SemapDemo();
		for(int i=0;i<20;i++) {
			executionn.submit(demo);
		}
	}
}

```

![线程结果](http://upload-images.jianshu.io/upload_images/4237685-c9bbdf1345c2c6b4.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
