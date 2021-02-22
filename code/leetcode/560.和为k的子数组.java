/*
 * @lc app=leetcode.cn id=560 lang=java
 *
 * [560] 和为K的子数组
 */

// @lc code=start
class Solution {
    public int subarraySum(int[] nums, int k) {
        int count = 0;

        for (int index = 0; index < nums.length; index++) {
            int sum = 0;
            for (int end = index; end < nums.length; end++) {
                sum += nums[end];
                if (sum == k) {
                    count++;
                }
                // if (sum > k) {
                // break;
                // }
            }
        }
        return count;

    }
}
// @lc code=end
