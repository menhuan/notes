#### volatile 与java内存模型（JMM）
1. java的内存模型都是围绕着原子性、有序性、还有可见性来展开的。
2. volatile 主要是用来告知虚拟机，被volatile 修饰的变量要注意，不要随意改动优化目标指令，使用该关键字是为了保证变量修改后会通知所有线程能看到该摆动。保证变量的可见性。 volatile  并不能代表锁，无法保证复合操作的原子性。但是volatile 能保证元素的可见性与在一定程度上保证有序性。保证修改的值会立即被更新到主存。当有其他线程需要读取时，它会去内存中读取新值。 volatitle 能保证一些的是禁止指令重排。
#### 线程组
在系统中我们可以把属于相同功能的线程放到一个线程组里面。
```
public class ThreadGroupName implements Runnable {

	@Override
	public void run() {
		String groupName = Thread.currentThread().getThreadGroup().getName()
				+"-"+Thread.currentThread().getName();
		while(true) {
			System.out.println("I am "+groupName);
			try {
				Thread.sleep(3000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		ThreadGroup  group= new ThreadGroup("PrintGroup");
		Thread  t1=new Thread(group,new ThreadGroupName(),"T1");
		Thread  t2 =new Thread(group,new ThreadGroupName(),"T2");
		t1.start();
		t2.start();
		
		System.out.println(group.activeCount());
		group.list();
	}
}

```

#### 守护线程
1. 守护线程是在后台默默运行的一些系统性的服务 ，常见的守护线程有垃圾回收线程，JIT线程。 与之对应的线程就是 用户线程也可以被认为是系统的工作线程。 用户线程执行完毕后就会结束。在java中 只有守护线程的时候  java虚拟机就会自然的退出。
```
public class DaemonDemo {
	public static class DaemonT extends Thread {
		@Override
		public void run() {
			while(true) {
				System.out.println("I am alive");
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}
		
	}

	public static void main(String[] args) {
		Thread thread= new DaemonT();
		thread.setDaemon(true);  //设置为守护线程
		thread.start();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
```
#### 线程优先级
线程中有自己的优先级，优先级别高的线程 在争夺资源 的时候 处于优势的状态，也有可能出现抢占资源失败的情况
```

public class PriorityDemo {

	public static class HightPriority extends Thread{
		static int count =0;
		
		@Override
		public void run() {
			while(true) {
				 synchronized (PriorityDemo.class) {
					count++;
					if(count>1000000) {
						System.out.println("HightPriority is complete");
						break;
					}
				}
			}
		}
	}
	
	public static class LowPriority extends Thread{
		static int count =0;
		
		@Override
		public void run() {
			while(true) {
				 synchronized (PriorityDemo.class) {
					count++;
					if(count>1000000) {
						System.out.println("LowPriority is complete");
						break;
					}
				}
			}
		}
	}
	
	public static void main(String[] args) {
		Thread high =new HightPriority();
		LowPriority lowPriority=new  LowPriority();
		high.setPriority(Thread.MAX_PRIORITY);   //在这里设置 线程的优先级
		lowPriority.setPriority(Thread.MIN_PRIORITY);  
		lowPriority.start();
		high.start();
	}
}
```
如上代码所示 。。。 线程中有优先级别设置，在java 中使用1到10 表示线程有限级别，我们一般用如图所示的变量来标识 线程优先级

![线程优先级设置](http://upload-images.jianshu.io/upload_images/4237685-f2e13f104247ed62.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
