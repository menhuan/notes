####进程的概念
  进程：官方的语言是计算机中的程序，关于某数据集合上的一次运行活动。使系统进行资源分配和调度的基本单位单位，是操作系统的基本单位，是操作系统结构的基础。在现在的线程设计的计算机结构中，进程是线程的容器，程序是指令数据及组织形式的描述，进程是程序的实体，但是总的来说 进程是线程的容器。在平常我们也会说线程是轻量级的进程，是程序执行的最小单位。使用多线程而不是用多进程进行并发设计的原因 **线程间的切换与调度的成本远小于进程**
#### 线程的生命周期
线程 状态分为  NEW（新创建的）,RUNNABLE（运行中）,BLOCKED（同步块阻塞）,WAITING（等待无限时间的等待）,TIMED_WAITING（等待有限的时间 ）,TERMINATED（结束） 六种
![线程的生命周期](http://upload-images.jianshu.io/upload_images/4237685-33e75081eb804646.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
#### 线程的建立
```
    Thread thread =new Thread();
     thread.start(); 
     //在这里启动后 会找到我们的run方法 去执行 
```
#### 终止线程
使用Thread.stop 该方法停止线程。但是该方法会直接终止线程，并且将立即释放这个线程所持有的锁，而这些锁是用来维持对象的唯一性的。如果数据写入到一半 锁时候后，另外一个线程 就会读取到该对象，但是这就读取到了一个不一致的对象这样就会造成程序出现问题。

![stop终止线程导致数据不一致](http://upload-images.jianshu.io/upload_images/4237685-6faef852bbb31d2c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
从图中我们可知 对象有两个属性 分别为ID 和NAME , 条件为当ID和NAME 相等 时 对象是一致的，否则表示对象出错。 我们的写入线程总是把 ID和NAME 写成相同的值。 然后当我们在写线程把ID写入到对象中的时候 ，突然执行stop 操作，那么该对象的ID就会变成1 而NAME仍然为别的数值，这样就处于不一致的情况，写线程在这个时候把锁释放后，读线程争取到锁，开始读取数据，这样就读取到了错误数据。 代码如下
```
public class StopThreadUnsafe {

	public static User user=new User();
	public static class User{
		private int id ;
		
		private String name;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public User() {
			id=0;
			name="0";
		}
		
		public User(int id, String name) {
			this.id = id;
			this.name = name;
		}
		@Override
		public String toString() {
			return "User [id="+id+", name="+name+ "]";
		}
		
	}
	
	public static class ChangeObjectThread extends Thread{
		@Override
		public void run() {
			
			while(true) {
				synchronized (user) {
					int v=(int)  System.currentTimeMillis()/1000;
					user.setId(v);
					
					try {
						Thread.sleep(100);
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					user.setName(String.valueOf(v));
				}
				Thread.yield();  //相当于睡眠，不过 只是 让同优先级别的线程有执行的机会 。  并且不能指定 暂停多长时间
			}
			
		}
	}
	
	
	public static class ReadObjectThread extends Thread{
		@Override
		public void run() {
			while(true) {
				synchronized (user) {
					if(user.getId()!=Integer.parseInt(user.getName())) {
						System.out.println(user.toString());
					}
				}
				Thread.yield();
			}
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		new ReadObjectThread().start();
		while(true) {
			Thread thread=new ChangeObjectThread();
			thread.start();
			Thread.sleep(150);
			thread.stop();
		}
	}
	
}

```

![程序显示结果](http://upload-images.jianshu.io/upload_images/4237685-a8fcaf0d8f4073bf.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
显示的结果不一致，就是读锁 读到内容不一样。
