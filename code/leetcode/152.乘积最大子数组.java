/*
 * @lc app=leetcode.cn id=152 lang=java
 *
 * [152] 乘积最大子数组
 */

// @lc code=start
class Solution {
    public int maxProduct(int[] nums) {
        int length = nums.length;
        int maxF = nums[0], minF = nums[0], ans = nums[0];
        for (int index = 1; index < length; index++) {
            int mn = maxF, mx = minF;
            maxF = Math.max(mn * nums[index], Math.max(mx * nums[index], nums[index]));
            minF = Math.min(mx * nums[index], Math.min(mn * nums[index], nums[index]));
            ans = Math.max(ans, maxF);

        }
        return ans;
    }
}
// @lc code=end
