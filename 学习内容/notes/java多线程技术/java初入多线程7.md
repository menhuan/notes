#### 同步控制
1. synchronized 扩展：重入锁
- 重入锁来代替synchronized，在Jdk1.6以后 synchronized的性能与重入锁性能差不多。
- 重入锁的实现
```
public static ReentrantLock lookLock= new ReentrantLock();
	
	public static int i=0;
	
	public void run() {
		
		for(int j=0; j<100000;j++) {
			lookLock.lock();  //加锁
			lookLock.lock();
			try {
				i++;
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				lookLock.unlock();
				lookLock.unlock();
			}
		}
	}

	
	public static void main(String[] args) throws InterruptedException {
		ReenterLock  reenterLock=new ReenterLock();
		Thread t1=new Thread(reenterLock);
		Thread t2=new Thread(reenterLock);
		
		t1.start(); t2.start();
		t1.join();t2.join();
		System.out.println(i);
	
	}
```
- 在上述代码中我们使用了重入锁来保护临界区资源i,确保程序的操作的安全性。我们在使用重入锁的时候需要显示的指定何时加锁，何时释放锁。必须释放锁不然其他线程没有机会访问临界区了。
- 我们在代码上还实现了多次加锁的控制，同一个线程可以加入多个锁来控制 ，但是释放的时候加了 几个锁就要释放几个锁。不然其他线程也无法进入临界区。
#### 中断响应
1. 我们在使用synchronized 来加锁的话，那么结果只有两种可能 一是获得锁继续执行。二是 继续等待。但是我们使用重入锁就可以使其中断。
2. 锁申请等待限时
- 除了我们在等待外部通知之外，避免死锁还有另外一种方法。就是限时等待。我们可以给定一个等待的时候后。如果线程很长时间拿不到锁，等待时间到了那么让其自动放弃。

我们使用重入锁的tryLock()方法接受两个参数，一个表示等待时长，另外一个表示计时单位。并且 该方法也可以不带参数直接运行，在尝试的时候能获得到锁，就会立即返回，当锁被其他线程占用的时候当前线程不会进行等待，立即返回false. 不会产生等待。因此不会产生死锁。代码如下
```
public class TryLock implements Runnable {

	public static ReentrantLock  lock1 =new ReentrantLock();
	public static ReentrantLock  lock2 =new ReentrantLock();
	int lock;
	
	public TryLock(int lock) {
		this.lock=lock;
	}
	
	public void run() {
		
		if(lock==1) {
			while(true) {
				if(lock1.tryLock()) {
					 try {
						try {
							Thread.sleep(500);
						} catch (Exception e) {
							
						}
						if(lock2.tryLock()) {
							try {
								System.out.println(Thread.currentThread().getId() +":My Job done");
								return ;
							} finally {
								lock2.unlock();
							}
						}
						
					} finally {
						lock1.unlock();
					}
				}
			}
		}
		else {
			while(true) {
				if(lock2.tryLock()) {	
				try {
					try {
						Thread.sleep(500);
					} catch (Exception e) {
					}
					
					if(lock1.tryLock()) {
						try {
							System.out.println(Thread.currentThread().getId() +"My Job done");
							return ;
						} finally {
							lock1.unlock();
						}
					}
				}	finally {
					lock2.unlock();
				}
				}
			}
		}

	}	
	
	public static void main(String[] args) {
		TryLock r1=new TryLock(1);
		TryLock r2=new TryLock(2);
		Thread t1 =new Thread(r1);
		Thread t2 =new Thread(r2);
		t1.start();
		t2.start();
		
	}
		
}
```
![死锁产生了结果](http://upload-images.jianshu.io/upload_images/4237685-a7de5d8eb7a5c6ac.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

3. 公平锁
- 大多数情况下，锁的申请都是非公平的。如果我们使用的是synchronized 来加锁，产生的锁就是非公平的。但是我们可以使用重入锁允许我们对其公平性进行设置。 方法为ReentrantLock(boolean fair)；  当 fair  为true 时，代表锁是公平的，但是使用公平锁 需要维护一个有序的队列，就会造成性能降低。因此我们在默认情况下不使用公平锁。
- 整理ReentrantLock 的几个方法如下：
- lock():获得锁 ，吐过锁被占用 ，则等待；
- lockInterruptibly(): 获得锁，单会优先响应中断；
- tryLock(); 尝试获得锁，成功返回true,失败返回false；不等待立即返回。
- tryLock(long time,TimeUnit unit): 给定时间内获得锁。
- unlock(); 释放锁。
4.
在重入锁实现中 主要包含三个元素：
- 使用原子状态，原子操作使用CAS操作来存储当前锁的状态，判断锁是否被别的线程持有。
- 等待队列。所有没有请求到锁的线程，会被放入等待队列中进行等待，待有线程释放后 系统就能从队列中唤醒一个线程，继续工作。
- 阻塞park()和unpark，用来挂起和恢复线程。没有得到锁的线程将会被挂起。
