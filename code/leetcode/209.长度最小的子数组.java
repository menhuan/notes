/*
 * @lc app=leetcode.cn id=209 lang=java
 *
 * [209] 长度最小的子数组
 */

// @lc code=start
class Solution {
    public int minSubArrayLen(int s, int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        // 快慢指针
        int start = 0, end = 0, ans = Integer.MAX_VALUE, sum = 0;
        while (end < nums.length) {
            sum += nums[end];
            while (sum >= s) {
                // 计算当前最小的长度
                ans = Math.min(ans, end - start + 1);
                // 注意放的位置
                sum -= nums[start];
                start++;
            }
            end++;
        }
        return ans == Integer.MAX_VALUE ? 0 : ans;
    }
}
// @lc code=end
