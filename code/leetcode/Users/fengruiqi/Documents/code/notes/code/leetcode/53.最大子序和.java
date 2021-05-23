/*
 * @lc app=leetcode.cn id=53 lang=java
 *
 * [53] 最大子序和
 */

// @lc code=start
class Solution {
    public int maxSubArray(int[] nums) {
        int[] dp = new int[nums.length];
        int max = nums[0];
        int pre = nums[0];
        for (int index = 1; index < nums.length; index++) {
            pre = Math.max(nums[index], pre + nums[index]);
            max = Math.max(pre, max);
        }
        return max;
    }
}
// @lc code=end
