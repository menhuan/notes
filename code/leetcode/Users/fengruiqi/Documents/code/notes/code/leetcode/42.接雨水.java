import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/*
 * @lc app=leetcode.cn id=42 lang=java
 *
 * [42] 接雨水
 */

// @lc code=start
class Solution {
    public int trap(int[] height) {
        // 单调栈问题。将数组中的元素放入到队列汇总。 单调递减的队列
        // 第一个放入到第一个里面然后比他小的也都放进入，遇到最后一个比他高的就是其右边解。
        // 这时候计算面积。
        // 递减的单调队列
        Deque<Integer> deque = new LinkedList<>();
        int area = 0;
        for (int index = 0; index < height.length; index++) {
            // 找到右边比当前值大的
            while (!deque.isEmpty() && height[index] > height[deque.peekFirst()]) {
                // 进行面积的计算
                int top = deque.pop();
                // 过来掉里面只有一个值的情况
                if (deque.isEmpty()) {
                    break;
                }
                // 计算当前元素与栈顶元素的距离
                int distance = index - deque.peekFirst() - 1;

                int dounded_height = Math.min(height[index], height[deque.peekFirst()]) - height[top];

                area += distance * dounded_height;
            }
            // 这个是往头部添加 所以保证的是头部是肯定 实际值比左边的小。
            deque.addFirst(index);
        }
        return area;
    }
}
// @lc code=end
