import java.util.Stack;
/*
 * @lc app=leetcode.cn id=155 lang=java
 *
 * [155] 最小栈
 */

// @lc code=start
class MinStack {
    Stack<Integer> stack;
    Stack<Integer> stackMin;

    /** initialize your data structure here. */
    public MinStack() {
        stack = new Stack<Integer>();
        stackMin = new Stack<Integer>();
        stackMin.push(Integer.MAX_VALUE);
    }

    public void push(int val) {
        stack.push(val);
        // 每次都插入一个值都插入一个最小的值
        stackMin.push(Math.min(stackMin.peek(), val));

    }

    public void pop() {
        stack.pop();
        stackMin.pop();

    }

    public int top() {
        return stack.peek();

    }

    public int getMin() {
        return stackMin.peek();

    }
}

/**
 * Your MinStack object will be instantiated and called as such: MinStack obj =
 * new MinStack(); obj.push(val); obj.pop(); int param_3 = obj.top(); int
 * param_4 = obj.getMin();
 */
// @lc code=end
