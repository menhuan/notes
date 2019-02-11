####  基于Socket的服务端多线程模式
```
public class HeavySocketClient {

	private static ExecutorService  tp =Executors.newCachedThreadPool();
	
	private static final int sleep_time =1000 * 1000 * 1000 ;
	
	public static class EchoClient implements Runnable{
		@Override
		public void run() {
			Socket client =null ;
			PrintWriter writer = null ;
			BufferedReader reader = null ;
			
			try {
				client =new Socket ();
				client.connect(new InetSocketAddress("localhost", 8000));
				writer = new PrintWriter(client.getOutputStream() , true );
				writer.print("H");
				LockSupport.parkNanos(sleep_time);
				writer.println("e");
				LockSupport.parkNanos(sleep_time);
				writer.println("e");
				
				LockSupport.parkNanos(sleep_time);
				writer.println("e");
				
				LockSupport.parkNanos(sleep_time);
				writer.println("e");
				
				LockSupport.parkNanos(sleep_time);
				writer.println("e");
				
				LockSupport.parkNanos(sleep_time);
				writer.println("e");
				writer.flush();
				
				reader =new BufferedReader(new InputStreamReader(client.getInputStream()));
				System.out.println("from server : " +reader.readLine());
				
			} catch (Exception e) {
				e.printStackTrace();
			}		
		}
	}	
}
```
```
public class MultiThreadEchoServer {

	private static ExecutorService tp =Executors.newCachedThreadPool();
	
	static class HandleMsg implements Runnable{

		Socket clienSocket ;
		
		
		public HandleMsg(Socket clienSocket) {
			super();
			this.clienSocket = clienSocket;
		}


		@Override
		public void run() {
			BufferedReader is = null ;
			PrintWriter os = null ;
			try {
				is = new BufferedReader( new InputStreamReader(clienSocket.getInputStream()));
				os =new PrintWriter(clienSocket.getOutputStream(),true);
				
				String inputLine = null;
				long b =System.currentTimeMillis() ;
				
				while ((inputLine = is.readLine()) != null){
					os.println(inputLine);
				}
				long e =System.currentTimeMillis() ;
				System.out.println("spend:"+ (e - b) + "ms");
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				try {
					if(is != null){
						is.close();
					}
					if(os != null){
						os.close();
					}
					clienSocket.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) {
		ServerSocket echoServer = null ;
		Socket clientSocket = null ;
		try {
			echoServer =new ServerSocket(8000);
		} catch (Exception e) {
			System.out.println(e);
		}
		
		while(true){
			try {
				clientSocket = echoServer.accept() ;
				System.out.println(clientSocket.getRemoteSocketAddress() + " connect !");
				tp.execute(new HandleMsg(clientSocket));
				
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
	
}
```
- 客户端与服务端的代码实现 ，在客户端，我们使用线程池实现10次请求，因为会在输出有个字符之后。进行1秒的等待。所以会延迟很长时间。
