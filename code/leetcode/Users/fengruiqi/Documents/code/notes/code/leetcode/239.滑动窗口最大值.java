import java.util.Deque;
import java.util.LinkedList;

/*
 * @lc app=leetcode.cn id=239 lang=java
 *
 * [239] 滑动窗口最大值
 */

// @lc code=start
class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        // 这个使用的是单调递减的栈计算，最开始保证的是最大值。后面是递减的。
        // 如果发现中间的值比上一个大，那么移走上一个位置的数据
        Deque<Integer> deque = new LinkedList<>();
        // 最大数组的长度是 数组的长度- k +1
        int[] maxNums = new int[nums.length - k + 1];
        for (int index = 0; index < nums.length; index++) {
            // 数据放入到队列中时，要判断最后面的是否是比当前小的
            while (!deque.isEmpty() && nums[deque.peekLast()] <= nums[index]) {
                // 不为空 并且当前的值比队列中的大 那么就需要移除队列中的最小值。
                // 用来保证在范围内是递减的最大值序列。[1,3,-1,-3,5,3,6,7]
                // 比如现在是 5 3 这时候6 来了判断比 6 这时候前面的值就都移除了。这样插入
                // 数据的时候6 就是最大的
                deque.removeLast();
            }
            // 放入的是下标
            deque.addLast(index);

            // 计算左边界
            int left = index - k + 1;

            if (deque.peekFirst() < left) {
                // 如果不在左边界了 那么把最大值移走。
                deque.removeFirst();
            }

            // 代表在k范围内了
            if (index + 1 >= k) {
                maxNums[left] = nums[deque.peekFirst()];
            }

        }
        return maxNums;
    }
}
// @lc code=end
