我们前篇介绍了点关于线程创建的方式以及Thread相关api的介绍。这次我们说说线程中锁的应用。多线程中，数据同步是一个很让人头疼的事情，并且写代码中我们很容易写出线程不安全的代码，在查问题的时候也是特别不容易的查出来。java中在线程同步中采取了锁的方式来让数据同步。
### synchronized 关键字 使用
1. 把非线程安全的变成线程安全的。在方法名前面加上该关键字即可。
```
 public synchronized String  getContent(){
        return null ;
    }
```
虽然这两种方法中都可以形成线程安全，但是如果在操作中不当，也会出现线程不安全的结果。我们在方法中有锁，如果我们在该类上创建多个实例，那么锁的作用就会消失。还会出现线程不安全的状况。在项目中如果方法中加锁，最好采用的是同一个类或者是静态方法,这样保证使用的是同一个锁，不会是多个锁。保证了线程安全的问题。方法上加锁，如果在方法中有很多代码其实不需要执行同步操作，只是一部分需要同步操作，在整个方法上加锁，就容易导致该方法区的内容运行过慢，效率降低。
```
public  static synchronized String  getContent(){
        return null ;
    }
```
2. 使用该关键字，形成同步代码块。
同步代码块可以帮助解决上面的问题。将一部分同步，一部分异步.这样同步的地方就可以并行执行，增大效率。
```
public  String getContent(Object object){

         Stirng result ;
        synchronized (Object.class){
            return null ;
        }
        return result;
    }
```
3. 同步代码块与在方法区上使用synchronized关键字的作用
   1. 在调用时，呈现阻塞的状态。
   2. 同一时间内，只有一个线程可以调用代码块中的代码
4. 同步代码块还支持其他对象来实现同步的功能
    * 代码块要求的是（）中的对象监视器，并且在同步代码块中我们使用的同一个对象，那么在代码块中的监视器就可以是this或者其他的非this对象，但是对于类似Stirng或者Integer这种不变的对象不可用。无法锁定一个对象，在多线程时刻容易出现线程安全问题。
### volatile 关键字
   * 使变量在多个线程之间可见。我们都是到在线程中会有私有堆栈和共有堆栈，那么volatile 的作用就是让其在线程访问变量时，强制性的从共有堆栈中取值。也就是我们平常所说的主内存而不是线程工作内容中读取，保证了线程安全的可见性。但是只是保证可见性，不保证原子性。没有同步特性。
### Lock的使用
1. ReentrantLock 介绍
    ReentrantLock 可以达到我们使用synchronized  锁达到的同步效果 ，并且还能扩展更强大。在使用中我们一般使用的是以下一些方法。并且该锁是一个完全互斥拍他的锁。读写不分离
    - lock(): 获得锁。
    - unlock(): 释放锁。该方法使用一般都会放在finally中 ，如果线程出现异常退出，应该把锁也释放掉，让其他新城也获得该锁。程序能继续执行.
   - Condition 实现等待/通知 。如果我们想实现单个对单个的通知，那么我们可以创建多个Condition来帮助我们实现一对一的方式。Condition中有await和signal 还有signalAll();await实现的是等待的方式，signal
实现的是通知等待的线程继续执行。我们在使用该等待方式之前必须，先获得锁才能使用，不然获得就是异常.
   * 公平锁与非公平锁：公平锁是在先进来的线程会先获得资源。非公平锁先进来的线程不一定会先获得锁，可能有的线程一直拿着锁不放，容易造成线程锁之间的不公平。
   * 简单Api介绍       
    ```
     public  void  getContent(Integer value ){
        try{
            lock.lock();
             lock.getHoldCount(); //查询当前线程保持的此锁定的个数。也就是调用lock()方法的次数。
            lock.getQueueLength(); // 返回挣等待获取此锁定 的线程估计数。
            lock.getWaitQueueLength(condition); //返回等待与此锁定相关条件Condition估计数。
            lock.hasQueuedThread(Thread threads) ; //作用查询指定的线程是否在等待获取此锁定
            lock.hasQueuedThreads(); //查询 是否有线程正在等待获取此锁定。
            lock.hasWaiters(condition); // 查询是否有线程正在等待此锁定有关的condition条件。
            lock.isFair(); // 判断是不是公平锁
            lock.isHeldByCurrentThread(); // 查询当前线程是否保持此锁定
            lock.isLocked(); //作用是查询此锁定是否由任意线程保持。
            lock.lockInterruptibly(); // 如果当前线程未被中断，则获取锁定。如果已经被中断则出现异常
            lock.tryLock();  //仅在 调用时锁定未被另一个线程保持的情况下，才获取该锁定
            System.out.println("打印内容: " + Instant.now());
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
    ```  
2. ReentrantReadWriteLock 读写锁
  * 该锁方便的在于读写锁进行分离，我们在操作时读锁与读锁互不影响，那么就可以提高我们在程序中的效率。
 * 读锁与读锁之间互不影响，能共同存在，不排斥。
 * 读锁与写锁之间，读锁与写锁互斥，读取数据时写锁不能执行。一锁执行
 * 写锁与写锁之间，也互斥，同时只能一个锁在执行。
```
       ReentrantReadWriteLock  readWriteLock = new ReentrantReadWriteLock();
       readWriteLock.readLock().lock();
      readWriteLock.writeLock().lock();
```

![TIM截图20180405235154.jpg](https://upload-images.jianshu.io/upload_images/4237685-89dd6f31b14e20ae.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

 



      
