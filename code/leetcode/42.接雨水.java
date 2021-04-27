import java.util.Deque;
import java.util.Stack;
import java.util.concurrent.locks.StampedLock;

/*
 * @lc app=leetcode.cn id=42 lang=java
 *
 * [42] 接雨水
 */

// @lc code=start
class Solution {
    public int trap(int[] height) {
        // 使用栈来解决这个问题
        // 循环遍历数组中的元素，将每个元素的下标放到栈中，方便后面计算面积使用
        // 栈不为空，并且当前的值大于栈顶元素，这样才会结合在一块。
        int ans = 0,current = 0;
        Deque<Integer> stack = new LinkedList<Integer>();
        while(current < height.length){
            while(!stack.isEmpty() && height[current] > height[stack.peek()]){
                // 取出栈顶元素
                int top = stack.pop();
                if(stack.isEmpty()){
                    break;
                }
                int distince = current - stack.peek() - 1 ;
                int boundHeight = Math.min(height[current],height[stack.peek()]) -  height[top];
                ans+= distince * boundHeight;

            }
            //每个元素的坐标都会放进去
            stack.push(current++);
        }
        return ans;

    }
}
// @lc code=end

