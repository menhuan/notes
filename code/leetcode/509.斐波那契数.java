/*
 * @lc app=leetcode.cn id=509 lang=java
 *
 * [509] 斐波那契数
 */

// @lc code=start
class Solution {
    public int fib(int n) {
        int[] nums = new int[n + 1];
        nums[0] = 0;
        if (n >= 1) {
            nums[1] = 1;
            for (int index = 2; index <= n; index++) {
                nums[index] = nums[index - 1] + nums[index - 2];
            }
        }

        return nums[n];
    }
}
// @lc code=end
