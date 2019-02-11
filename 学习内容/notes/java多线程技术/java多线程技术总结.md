在我们使用的技术中，Java的基础知识多线程也是一门十分重要的技术，但是很多做java的开发者并不是接触到这些知识，自己也是专门做后端研发后，才开始使用，也总结过一些相关知识，但是还是感觉不够透彻，今天又把java多线程的书过了一部分，顺便再记录下知识点，帮助自己记忆，但是还是多多在代码中使用，才能加深理解。
### 多线程Thread，Runnable ，Callable
   1. 实现线程的三种方式
   *  继承Thread 类
```
public class RunThread extends  Thread {
    @Override
    public void run() {
        super.run();
    }
}
```
* 实现Runnable 接口
```
public class RunThread   implements Runnable {
    public void run() {
    }
}
```
* 实现Callable接口
```
public class RunThread   implements Callable<Boolean> {
    public Boolean call() throws Exception {
        return true;
    }
}
```
* 三种不同的方式实现线程 三者又有什么区别呢？
   1. Thread 是类只能被继承 ，无法实现多继承的方式。而 Runnable 和Callable是接口，方便使用者使用多继承的方式。
   2. Thread 和Runnable 实现的接口是没有返回值的，而Callable接口实现的线程具有返回值，如果我们在别的线程中需要使用该方式实现需要返回结果的话，那么选择Callable的方式是最好的。
   3. 提交方式上，Thread和Runnable 可以使用Thread的start方法来启动，但是Callable就不行，只能采用线程池的方式提交
  4. 有些Thread类的方法 无法在Runnable 或者Callable 下面执行。
### Thread中常用的方法
1. currentThread()方法：用来返回线程中被哪个线程调用的信息。 常用的方法有
   * getName()： 得到线程的名字，方便在测试开发的时候 查看线程执行的问题。
   * getId(); 得到线程号，同样也是方便执行问题。
   * isAlive();判断线程是否还处于存活状态。
   * sleep() ;让线程睡眠。但是注意该方法的睡眠，不会释放锁。
 2. 停止线程：
        1.  stop (),暴力停止，如果线程处于正在运行的状态，容易发生数据混乱的问题。
        2. interrupt()，告知线程停止，在运行的线程上打个标签，告知线程运行完该任务后结束。
        3.  this.interrupted(),测试当前线程是否已经中断。 
        4. this.isInterrupted()，测试线程是否已经中断。  3与4的区别在于 3的作用带有清楚的作用，把线程的中断状态清除，调用3的时候做好调用一次，如果真的判断一个线程是否中断 选择4的方式。 
3.  暂停线程：
       1. suspend与resume,前者是让线程暂停，后者是让线程继续运行，但是缺点是会独占锁。容易造成死锁的问题。别的线程抢不到资源的问题。并且还容易出现数据不同步的情况。
 4. yield方法(): 放弃cpu资源，让别的线程获取资源，但是可能会刚放弃又马上获得资源。

### 线程中的优先级
   在线程的执行过程中 ，具有优先级的策略，并且优先级还可以继承。在线程中我们可以使用下面的方式设置线程的优先级  Thread.currentThread().setPriority(10); 线程优先级设置范围在1~10之间。虽然线程可以执行设置优先级，但是在执行的时候不一定是优先级别高的就会先执行，具有随机性。
### 线程的种类
   线程中分为用户线程，和守护线程。守护线程当没有用户线程执行的时候，会自动销毁。其中垃圾回收就是一个特别的守护线程。通俗的说守护线程就是相当于保姆，照顾着用户线程的执行，当所有用户线程执行完毕，守护线程也退出执行。

