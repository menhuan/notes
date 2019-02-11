#### 线程中断
1. 概念 ：让目标线程停止执行，但是是高知目标线程希望线退出，具体退出由目标线程自己决定。
2. 相关的方法，暂时只介绍Thread的方法
- Thread.interrupt()      //中断线程  也是告知目标线程中断，也就是设置中断标志位
- Thread.isInterrupted()  //判断是否被中断--通过上面方法设置的中断标志位来判断是否被中断
- Thread.interrupted   //判断是否被中断，并清楚中断状态，，， 用来判断当前线程的中断状态，同事清除中断标志位状态
3. 程序实现
```
public static void main(String[] args) throws InterruptedException {
		Thread t1=new Thread(){
			@Override
			public void run() {
				while(true){
					//在这里判断 是否有中断位  ，又中断位了就将线程终止
					if(Thread.currentThread().isInterrupted()){
						System.out.println("中断了");
						break;
						
					}
					
					Thread.yield();
				}
			}
		};
		t1.start();
		Thread.sleep(2000);
		t1.interrupt();  // 在这里进行通知中断
	}
```
#### 通知(notity)与等待(wait)
 1. 当一个线程执行wait方法的时候，那么当前线程就会停止继续执行，转为等待的状态，然后等其他线程执行notity 方法为止。执行如图所示，如果用notity是随机唤醒一个线程不是按照执行顺序唤醒线程


![notity唤醒等待的线程](http://upload-images.jianshu.io/upload_images/4237685-bbb92a7a12205184.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![wait和notity的工作流程](http://upload-images.jianshu.io/upload_images/4237685-ae792203fafdfb88.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
wait 执行后会释放对应的监视器，其他线程会获得该监视器，然后执行notity 之后 会释放监视器，wait 重新获得监视器，继续执行程序。
```
public class SimpleWN {
	
	final static Object object =new Object();
	
	public static class T1 extends Thread{
		@Override
		public void run() {
			
			synchronized(object) {
				System.out.println(System.currentTimeMillis()+ "T1 start!");
				try {
					System.out.println(System.currentTimeMillis() + "T1 wait for object");
					object.wait();   //wait等待 然后等到notity来唤醒 ,但是notity唤醒也是随机的  并且wait 方法会释放目标对象的锁而下面的sleep方法就不会释放
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println(System.currentTimeMillis() + ": T1 end!");
			}
			
		}
	}
	
	public static class T2 extends Thread{
		@Override
		public void run() {
			synchronized (object) {
				System.out.println(System.currentTimeMillis()+"T2 start ! notity one thread");
				object.notify();
				System.out.println(System.currentTimeMillis()+"T2 end!");
				try {
					Thread.sleep(2000);  //睡眠2秒
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			
		}
	}
	public static void main(String[] args) {
		Thread thread1=new T1();
		Thread thread2=new T2();
		thread1.start();
		thread2.start();
	}

}

```
#### 挂起（suspend）和继续执行（resume）
1. 被废弃的方法，说明下原理：suspend 在导致线程暂停的通识不把锁的资源释放，其他线程 如果访问没有被释放的锁，那么也会受到牵连导致无法 继续执行。除非在该暂停的线程上进行了resume操作那么该线程才会继续执行，阻塞的其他线程也会继续执行。如果resume 操作在suspend前执行了那么被执行suspend的线程很难有机会再被继续执行。这种情况下很可能出现死锁的情况

![死锁](http://upload-images.jianshu.io/upload_images/4237685-937503bbfdd7e8dd.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
#### 等待线程结束（join）和谦让（yield）
1. 我们在程序的执行中，很可能会出现多个线程之间有依赖关系的线程，只有当前一个线程执行完毕后才能继续执行以后的线程，那么我们可以使用join方法来实现 ，等待线程结束。看如下代码
```
public class JoinMain {

	/**
	 * volatile  变量的可见性 不可代替锁
	 */
	private volatile static int i=0;
	
	public static class AddThread extends Thread{
		@Override
		public void run() {
			for(i=0;i<10000000;i++);
		}
	}
	
	public static void main(String[] args) throws Exception {
		AddThread thread= new AddThread();
		thread.start();
		thread.join(); //等待的意思  本质是让调用线程wait 在当前线程实例上 调用的是wait(0) 这个方法
		System.out.println(i);
	}
	
}
```
代码执行结果如图

![运行结果](http://upload-images.jianshu.io/upload_images/4237685-2e4856b5c058f7e9.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![注释掉join](http://upload-images.jianshu.io/upload_images/4237685-3c6bc076719f90bb.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
从上面两个图可以看出来 如果不适用join 那么执行的结果会很小。执行join方法后会让该线程等待addThread线程执行完毕。
2. yield方法：谦让，谦让，意思 当该方法执行后会让当前线程把cpu 让出来，到那时有一点需要注意的是让出cpu后该线程还会参与到cpu的争夺中，会不会分配到 这就不一定了。
