#### 无锁的线程安全整数： AtomicInteger
- 方法介绍
  1.  public final int get(); 取得当前值
  2.  public final void set(int newValue)； 设置当前值
  3.  public final int getAndSet(int newValue)； 设置新值 返回旧值
  4. public final boolean compareAndSet(int expect, int update)； 如果当前值为expect  ，则设置为update.
  5.  public final int getAndIncrement(); 当前值加1 ，返回旧值
  6.  public final int getAndDecrement(); 当前值减1 ，返回旧值
  7.   public final int getAndAdd(int delta)；当前值增加data, 返回旧值
  8.  public final int incrementAndGet()； 当前值增加1 ，返回新值。
- 代码实现
```
public class AtomicIntegerDemo {

	static AtomicInteger i = new  AtomicInteger() ;
	
	public static class AddThread implements Runnable{
		@Override
		public void run() {
			for(int k = 0 ; k < 10000 ; k++){
				i.incrementAndGet() ;
			}
		}
	}
	public static void main(String[] args) throws Exception {
		Thread[] ts = new  Thread[10];
		for(int k = 0 ; k < 10 ; k++){
			ts[k] = new  Thread(new AddThread()); 
		}
		for(int k = 0 ; k<10 ;k++){
			ts[k].start();
		}
		for(int k = 0; k < 10 ; k++ ){
			ts[k].join();
		}
		System.out.println(i);
	}
}
```
#### Unsafe 类：
- 是封装了一些类似指针的操作。还有内部实现也是依赖CAS原子指令来完成的。这是一个不让我们在应用上使用的类型
#### 无锁对象的引用：AtomicReference
- 与AtomicInteger 类似 不过 该类是用来操作对象的。 根据当前值和期望值进行比较设置。当一致时候采用该类中的方法进行修改。
- 但是有种情况就是 如果我们修改 值得时候出现 1-2-1 这个中情况我们最后拿到的还是1 那么程序还是认为是1 。只认为结果是正确的再进行修改。改为2 或者其他的。代码实现如下：
```
public class AtomicStampedDemo {
	static AtomicReference< Integer> money = new AtomicReference<>() ;
	
	 
	public static void main(String[] args) {
		money.set(20);
		for(int i = 0 ;i < 3 ; i++ ){
			new Thread(){

				public void run() {
					while(true){
						Integer m = money.get(); 
						if (m < 20 ){
							if(money.compareAndSet(m, m+20 )){
								System.out.println("余额小于20元,充值成功，余额: " + money.get() 
								+ " 元 ");
							}
						}else{
							System.out.println("余额大于20元, 无须充值 ");
							break;
						}
					}
				};
			}.start();
		}
		//用户消费线程， 模拟消费行为
		new Thread(){
			public void run() {
				for( int i = 0 ; i < 100 ; i++){
					 while(true){
						 Integer m =money.get() ;
						 if(m > 10){
							 System.out.println("大于10元");
						 if(money.compareAndSet(m, m-10)){
								 System.out.println("成功消费10元，余额:" + money.get());
								 break ;
							 };
						 }else{
							 System.out.println("没有足够的金额");
							 break ;
						 }
					 }
					 try {
						Thread.sleep(100);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}			};
		}.start(); 	 
	}	
}
```
#### AtomicStampedReference
- 为了防止出现多次提交的问题。比如我们限制 一个用户只参与一次活动，那上面的实现方式就不行了，因为只记录了每次的金额，没有显示出来变动的情况。我们改用AtomicStampedReference来实现 ，该类在原先的基础上增加了时间戳的记录 ，记录了过程的实现。
```
public class AtomicStampedReferenceDemo {

	 static AtomicStampedReference<Integer> money = new AtomicStampedReference<Integer>(19, 0);	 
	 public static void main(String[] args) {
		for(int i = 0 ;i < 3 ; i++ ){
			final int timestamp = money.getStamp() ;
			new Thread(){
				public void run() {
					while(true){
						Integer m = money.getReference() ; 
						if (m < 20 ){
							if(money.compareAndSet(m, m+20,timestamp, timestamp + 1 )){
								System.out.println("余额小于20元,充值成功，余额: " + money.getReference() 
								+ " 元 ");
							}
						}else{
							System.out.println("余额大于20元, 无须充值 ");
							break;
						}
					}
				};
			}.start();
		}
		
		//用户消费线程， 模拟消费行为
		new Thread(){
			public void run() {
				for( int i = 0 ; i < 100 ; i++){
					 while(true){
						 int timestamp = money.getStamp() ;
						 Integer m =money.getReference() ;
						 if(m > 10){
							 System.out.println("大于10元");
							 if(money.compareAndSet(m, m-10, timestamp, timestamp+1)){
								 System.out.println("成功消费10元，余额:" + money.getReference());
								 break ;
							 };
						 }else{
							 System.out.println("没有足够的金额");
							 break ;
						 }		 
					 }
					 try {
						Thread.sleep(100);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
		}.start(); 		 
	}
}
```
#### 数组也能无锁： AtomicIntegerArray
- 使用方式跟上面的AtomicInteger 类似 ，都采用的CAS 操作。
