#### Future 模式
- 该模式核心思想是异步调用。

![Future 异步调用模式](http://upload-images.jianshu.io/upload_images/4237685-cc16c7fb1a9da2f9.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
```
public class Main {
	public static void main(String[] args) {
		Client client  = new Client() ;
		
		Data data = client.request("name");
		System.out.println("请求完毕");
		try {
			Thread.sleep(2000);
		} catch (Exception e) {
		}
		
		System.out.println("数据  = " + data.getResult());
	}
}
```
```
public class Client {
	public Data request(final String queryStr){
		final FutureData future = new FutureData() ;
		new Thread(){
			public void run() {
				RealData realData = new RealData(queryStr);
				future.setRealData(realData);
			}	;
		}.start();	
		return future ;
	}	
}
```
```
public class RealData implements Data {
	protected  final String result ;
	public RealData(String result) {
		super();
		StringBuffer buffer =new StringBuffer();
		for(int i = 0 ; i < 10 ; i++){
			buffer.append(result+" ");
			try {
				Thread.sleep(100);
			} catch (Exception e) {		
			}
		}	
		this.result = buffer.toString();
	}
	@Override
	public String getResult() {
		return result;
	}
}
```
```
public interface Data {
	public String getResult();
}
```
```
public class FutureData implements Data {
	protected boolean isReady = false ;
	protected RealData  realData = null ;
	public synchronized  void setRealData(RealData realData) {
		if(isReady){
			return ;
		}
		this.realData = realData;
		isReady = true ;
		notifyAll();
	}
	@Override
	public synchronized String getResult() {
		while(!isReady){
			try {
				wait();
			} catch (Exception e) {
			}
		}
		return realData.result;	
	}
}
```
#### jdk 中的Future 模式

![内置实现Future](http://upload-images.jianshu.io/upload_images/4237685-76177af0a4d055bb.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
```
public class FurureMain {

	public static void main(String[] args)  throws Exception{
		//构造任务
		FutureTask<String> future = new FutureTask<String>( new RealDataCall("a"));
		ExecutorService executor =Executors.newFixedThreadPool(1);
		executor.submit(future);
		System.out.println("请求完毕");
		try {	
			Thread.sleep(2000);
		} catch (Exception e) {
			// TODO: handle exception
		}	
		System.out.println("数据= ： "+ future.get());
	}	
}
```
```
public class RealDataCall implements Callable<String> {
	private String para ;
	public RealDataCall(String para) {
		super();
		this.para = para;
	}
	@Override
	public String call() throws Exception {
		StringBuffer buffer =new StringBuffer();
		for(int i = 0 ; i < 10 ; i++){
			buffer.append(para+" ");
			try {
				Thread.sleep(100);
			} catch (Exception e) {			
			}
		}
		return buffer.toString();
	}
}
```
  1. 在这里面我们首先创建FutureTask对象实例， 表示该任务是有返回值的。
  2. 第二步 使用Callable 产生数据。
  3. 提交任务到线程池。 作为任务的简单提交。用get把结果查出来
