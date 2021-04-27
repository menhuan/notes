#### 使用Disruptor 实现消费者和生产者
```
<！--引入需要的jar包 -->
<dependency>
    <groupId>com.lmax</groupId>
    <artifactId>disruptor</artifactId>
    <version>3.3.7</version>
</dependency>
// 主方法
public class CusProMain {
	
	public static void main(String[] args) throws Exception {
		ExecutorService  exector = Executors.newCachedThreadPool() ;
		int bufferSize = 1024 ;
		PCDataFactory  eventFactory = new PCDataFactory() ;
		
		Disruptor<PCdataTwo> disruptor =  new Disruptor<>(eventFactory, 
				bufferSize,
				exector,
				ProducerType.MULTI,
				new BlockingWaitStrategy()
				);
		disruptor.handleEventsWithWorkerPool(
				new ConsumerTwo(),
				new ConsumerTwo(),
				new ConsumerTwo(),
				new ConsumerTwo()
				);

		disruptor.start(); 
		
		RingBuffer<PCdataTwo> ringBuffer = disruptor.getRingBuffer() ;
		ProducerTwo producer = new ProducerTwo(ringBuffer) ;
		ByteBuffer bb =ByteBuffer.allocate(8);
		for(long l = 0 ; true ; l++){
			bb.putLong(0 , l);
			producer.pushDate(bb);
			Thread.sleep(100);
			System.out.println("add Data "+ l);
		}
		
	}

}

// 消费者 
public class ConsumerTwo implements WorkHandler<PCdataTwo> {

	@Override
	public void onEvent(PCdataTwo event) throws Exception {
		System.out.println(Thread.currentThread().getId() + ":Event: --"
			+event.getValue() * event.getValue() + "--");
	}
}

//生产者
private final RingBuffer<PCdataTwo> ringBuffer ;
	
	public ProducerTwo(RingBuffer<PCdataTwo> ringBuffer){
		this.ringBuffer = ringBuffer ;
	}
	
	
	public void pushDate (ByteBuffer bb){
		long sequence = ringBuffer.next();  
		try {
			PCdataTwo event = ringBuffer.get(sequence);
			event.setValue(bb.getLong());
		} catch (Exception e) {
		}finally{		
			ringBuffer.publish(sequence);
		}
	}
```
- 我们在主方法操作中将缓冲区设置成1024 ， 在这里有四个消费者， 有一个生产者不断向缓冲区存入数据。结果如图所示

![结果](http://upload-images.jianshu.io/upload_images/4237685-47b153b0044fd103.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
- 网站有个博客写的挺好，可以参考学习http://www.cnblogs.com/haiq/p/4112689.html
#### 提高消费者的相应时间：选择合适的策略
- 在Disruptor 环形缓冲区内，提供了几种策略 。策略如下
  1. BlockingWaitStrategy : 这是默认的策略。 该策略是使用锁和条件进行数据的监控和线程的唤醒 ，因为涉及到线程的切换， 所以该策略是最节省CPU的，但是高并发性能上时最糟糕的。
  2. SleepingWaitStrategy :该策略 回来循环中不断的等待数据，先自旋等待，如果不成功，则使用yield () 让出cpu ,并且会进行休眠。 保证不占用太多的cpu资源。 这个策略适用于 延时 不是特别高的场合，好处是他对生产者的线程影响 最小， 适合场景是异步日志。
  3. YieldWaitStrategy ： 适用于低延时的场合。 消费者线程不断的循环将空缓冲的变化。在循环内部，使用yield 让出cpu 给别的线程执行时间， 如果需要一个高性能的系统，并且对延时有较高的要求，那么适合该策略。
  4. BusySpinWaitStrategy : 死循环策略。 消费者线程区域会疯狂的监控缓冲区的变化。 并且 cpu数量必须大于消费者的线程数量。
