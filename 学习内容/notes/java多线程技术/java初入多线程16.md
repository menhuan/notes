#### 并行模式与算法
1. 单例模式 ： 保证在系统中只生产一个实例。下面是几种单例模式。
```
private Single(){
		System.out.println("Single  is  create");
	}
	private static Single single =new Single() ;
	public static Single getInstance(){
		return single ;
	}
```
  - 这个容易出现的问题就是单例什么时候创建的不受控制。 我们可以改造一下 :
```
public static int STATUS = 1 ;
	private Single(){
		System.out.println("Single  is  create");
	}
	
	private static Single single =new Single() ;
	public static Single getInstance(){
		return single ;
	}
```
- 在原先的基础上增加静态成员。这样在任何地方使用STATUS 都会导致instance 实例被创建。 不过 如果在我们new 对象的时候 没有引用 STATUS  那么我们就会创建instance 实例。
- 接下来是一个懒加载的策略  。核心思想是当我们需要的时候才创建对象。不过，为了方式多次创建对象我们不得不加锁，这样就会导致在高并发的情况下 ，引起锁竞争。
```
        private Single(){
		System.out.println("Single  is  create");
	}
	private static Single single = null ;
	
	public static Single getInstance(){
		if(single == null)
			 single = new Single() ;
		
		return single ;
	}
```
- 上面两者 结合的单例模式 ： 思想是创建了一个内部类，并且我们利用了虚拟机的类初始化机制创建单例。
```
private Single(){
		System.out.println("Single  is  create");
	}
	private static class SingleTonHolder{
		private static Single  instance = new  Single();
	}
	public static Single getInstance(){
		return  SingleTonHolder.instance ;
	}
```
#### 不变模式：
- 主要使用场景需要满足的条件：
1. 当对象创建后，其内部状态和数据不再发生任何变化。
2. 对象需要被共享，被多线程频繁访问。

- 为保证不变模式正常工作，需要注意一下四点：
1. 去除setter方法以及所有修改自身属性的方法。
2. 将所有属性设置为私有，并用final 标记，确保不可修改。
3. 确保没有子类可以重载修改他的行为。
4. 有一个可以构造完整对象的构造函数。
- java 中有很多元数据包装类 都是使用的不变模式实现的。
1. String .
2. Boolean.
3. Byte .
4. Character
5. Double .
#### 生产者- 消费者模式

![生产者-消费者模式](http://upload-images.jianshu.io/upload_images/4237685-de35fdcb7f860031.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
```
  //消费者
private BlockingQueue<PCdata> queue ;
	private static final int SLEEPTIME = 1000 ;
	public Consumer(BlockingQueue<PCdata> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		System.out.println("start Consumer id = "+ Thread.currentThread().getId());
		Random random = new  Random() ;
		
		try {
			while(true){
				PCdata  data = queue.take() ; // 会阻塞 如果没有结果的话
				if( null != data){
					int re = data.getIntData() * data.getIntData();
					System.out.println(MessageFormat.format("{0} * {1} ={2}",data.getIntData(), 
							data.getIntData(),re));
					Thread.sleep(random.nextInt(SLEEPTIME));
					
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}
	}
```

```
//生产者
private volatile boolean isRunning = true ;
	private BlockingQueue<PCdata>  queue ;
	private static AtomicInteger count = new AtomicInteger() ; //总数 原子操作类
	private static final int SLEEPTIME = 100 ;
	
	public Producer(BlockingQueue<PCdata> queue) {
		super();
		this.queue = queue;
	}
	
	@Override
	public void run() {
		PCdata data = null ;
		Random  random = new  Random();
		System.out.println(" start produceted id = "+ Thread.currentThread().getId());
		
		try {
			while(isRunning){
				Thread.sleep(random.nextInt(SLEEPTIME));
				data = new PCdata(count.incrementAndGet());   //构造任务数据
				System.out.println(data + "is put into queue");
				if(!queue.offer(data,2,TimeUnit.SECONDS)){
					System.err.println("filed to put data :"+ data);
				}
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace(); 
			Thread.currentThread().interrupt(); 
		}
			
	}

	public void stop(){
		isRunning = false ;
		
	}
}	
```
```
        //共享数据
	private final int intData ;

	public int getIntData() {
		return intData;
	}

	public PCdata(int intData) {
		super();
		this.intData = intData;
	}
	
	@Override
	public String toString() {
		return "data:"+intData;
	}
```
