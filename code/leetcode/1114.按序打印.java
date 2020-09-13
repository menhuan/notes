import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 * @lc app=leetcode.cn id=1114 lang=java
 *
 * [1114] 按序打印
 */

// @lc code=start
class Foo {
    private volatile int method = 0;
    public Foo() {
        
    }

    public void first(Runnable printFirst) throws InterruptedException {
        // printFirst.run() outputs "first". Do not change or remove this line.
        printFirst.run();
        method++;
    }

    public void second(Runnable printSecond) throws InterruptedException {
        while(method !=1){
            continue;
        }
        // printSecond.run() outputs "second". Do not change or remove this line.
        printSecond.run();
        method++;
    }

    public void third(Runnable printThird) throws InterruptedException {
        while(method !=2){
            continue;
        }
        // printThird.run() outputs "third". Do not change or remove this line.
        printThird.run();
    }
}
// @lc code=end

