/*
 * @lc app=leetcode.cn id=1480 lang=java
 *
 * [1480] 一维数组的动态和
 */

// @lc code=start
class Solution {
    public int[] runningSum(int[] nums) {
        int[] ans = new int[nums.length];
        int sum = 0;
        for (int index = 0; index < nums.length; index++) {
            sum += nums[index];
            ans[index] = sum;
        }
        return ans;

    }
}
// @lc code=end
