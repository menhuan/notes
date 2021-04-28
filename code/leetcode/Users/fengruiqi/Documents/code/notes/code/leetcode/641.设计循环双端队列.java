import java.util.Deque;
import java.util.LinkedList;

/*
 * @lc app=leetcode.cn id=641 lang=java
 *
 * [641] 设计循环双端队列
 */

// @lc code=start
class MyCircularDeque {
    Deque<Integer> deque;
    int size;
    int index;

    /** Initialize your data structure here. Set the size of the deque to be k. */
    public MyCircularDeque(int k) {
        size = k;
        deque = new LinkedList<>();
    }

    /**
     * Adds an item at the front of Deque. Return true if the operation is
     * successful.
     */
    public boolean insertFront(int value) {
        if (index == size) {
            return false;
        }
        deque.addFirst(value);
        index++;
        return true;

    }

    /**
     * Adds an item at the rear of Deque. Return true if the operation is
     * successful.
     */
    public boolean insertLast(int value) {
        if (index == size) {
            return false;
        }
        deque.addLast(value);
        index++;
        return true;

    }

    /**
     * Deletes an item from the front of Deque. Return true if the operation is
     * successful.
     */
    public boolean deleteFront() {
        if (index == 0) {
            return false;
        }
        deque.removeFirst();
        index--;
        return true;

    }

    /**
     * Deletes an item from the rear of Deque. Return true if the operation is
     * successful.
     */
    public boolean deleteLast() {
        if (index == 0) {
            return false;
        }
        index--;
        deque.removeLast();
        return true;

    }

    /** Get the front item from the deque. */
    public int getFront() {
        if (index == 0) {
            return -1;
        }
        return deque.getFirst();
    }

    /** Get the last item from the deque. */
    public int getRear() {
        if (index == 0) {
            return -1;
        }
        return deque.getLast();

    }

    /** Checks whether the circular deque is empty or not. */
    public boolean isEmpty() {
        if (index == 0) {
            return true;
        } else {
            return false;
        }

    }

    /** Checks whether the circular deque is full or not. */
    public boolean isFull() {
        if (index == size) {
            return true;
        } else {
            return false;
        }
    }
}

/**
 * Your MyCircularDeque object will be instantiated and called as such:
 * MyCircularDeque obj = new MyCircularDeque(k); boolean param_1 =
 * obj.insertFront(value); boolean param_2 = obj.insertLast(value); boolean
 * param_3 = obj.deleteFront(); boolean param_4 = obj.deleteLast(); int param_5
 * = obj.getFront(); int param_6 = obj.getRear(); boolean param_7 =
 * obj.isEmpty(); boolean param_8 = obj.isFull();
 */
// @lc code=end
