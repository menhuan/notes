/*
 * @lc app=leetcode.cn id=152 lang=java
 *
 * [152] 乘积最大子数组
 */

// @lc code=start
class Solution {
    public int maxProduct(int[] nums) {
        int min = nums[0];
        int max = nums[0];
        int ans = nums[0];
        for (int index = 1; index < nums.length; index++) {
            int maxf = max, minf = min;
            max = Math.max(Math.max(minf * nums[index], maxf * nums[index]), nums[index]);
            min = Math.min(Math.min(minf * nums[index], maxf * nums[index]), nums[index]);
            ans = Math.max(ans, Math.max(max, min));
        }
        return ans;
    }
}
// @lc code=end
