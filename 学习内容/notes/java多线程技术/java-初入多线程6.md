#### 线程安全的概念 与synchronized
1. 多线程在处理的过程中如果我们没有给程序进行过任何处理的话，在执行的过程中可能会出现多个线程对同一个数据同时进行修改，那么就可能会出现就修改一次的情况。比如程序中有i++，这个操作那么在执行过程中可能就会出现修改一次的情况。

![多线程冲突](http://upload-images.jianshu.io/upload_images/4237685-36d2cc1238cf4d29.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
为了防止多线程操作出现问题，我们必须保证多个线程对线程不安全的数据访问完全同步。在Java 中提供了一个关键字synchronized来实现这个功能。
2. 关键字synchronized 的作用就是实现线程之间的同步，对需要同步的代码加锁，实现 只有一个线程进入同步代码块中。来保证线程间的安全性。大概有以下几种用法
- 指定加锁对象，就是利用指定的对象在进入代码块之前获得 该对象。
- 直接作用于实例方法上：利用当前的实例进行加锁，new 之后的对象
- 直接作用于静态方法：对当前类加锁。 类变量。
3. 如果程序中本来数据进行的都是正数操作，预期都是正数的话，那么如果出现负数，那么可能出现这个情况，可能就是内存溢出导致的问题。比如long 型 如果超过最大值那么就会变成负数。 这就是内存溢出的问题。线上有时候就会碰到该问题。。需要注意。
#### 并发下的ArrayList
```
	static ArrayList<Integer> al=new ArrayList<Integer>(10); 
	
	public static class AddThread implements Runnable{

		public void run() {
			for(int i= 0; i<1000000; i++) {
				al.add(i);
			}
			
		}
	}
	
	public static void main(String[] args) throws Exception {
		Thread t1 =new Thread(new AddThread());
		Thread t2 =new Thread(new AddThread());
		t1.start();
		t2.start();
		t1.join();
		t2.join();
		System.out.println(al.size());
	}
}
```
- 这个代码执行完毕后，可能出现三种结果。
1. 正常结束，大小为200000.。
2. 出现异常的情况。下标越界情况

![下标越界](http://upload-images.jianshu.io/upload_images/4237685-eca0f7eeac740e0f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
这是因为ArrayList 在扩容的时候 ，内部的一致性被破坏掉。因为ArrayList 是线程不安全的，没有锁机制，导致在多个线程访问的时候 出现了不一致的内部状态，导致出现下标越界
3. 出现一个隐蔽的错误就是没有出现下标的越界的情况 ，只给了一个大小的值。出现这个情况的原因是多个线程在进行赋值的时候，对同一个位置进行赋值，在这种情况下 就没有错误提示。我们可以使用Vector 带起ArrayList.
#### hashMap 诡异的错误
1. hashMap 同样也不是线程安全的 。会出现如下结果
- 程序正常运行结果也符合预期。
- 程序正常结束，但是不符合预期，小于我们需要的数据大小
- 程序永远不会结束
2. 在验证过程中我们可以使用jps  命令来查看当前系统中java 的进程。如果想看具体 使用 可以使用 jps -help 有命令介绍。 这个是在linux上使用 一般会用jps -ml  显示内容和名称
3. 在使用jps 后我们可以看到端口号 。然后使用jstack  +端口号来看我们的运行的线程。
4. java7线面的源码hashMap ,java8现在已经不是该循环
![java7HashMap.png](http://upload-images.jianshu.io/upload_images/4237685-1f51cd223d3b7b39.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
解决方案可以使用ConCurrentHashMap代替
#### 错误的加锁问题
1. 在使用加锁的时候。我们不要使用锁对象是Integer ,String 这中对象去作为一个锁，可能造成加锁没有加成功的时刻，因为如果我们在其他地方对其修改后，该对象的地址就会变成新的对象。那么锁就会失去作用。
