/*
 * @lc app=leetcode.cn id=300 lang=java
 *
 * [300] 最长递增子序列
 */

// @lc code=start
class Solution {
    public int lengthOfLIS(int[] nums) {
        // 最长递增子序列
        // 设置动态规划方程数组 dp[i] 代表的是存储 0...i 存储的长递增子序列。
        // 那么我们就需要知道前面最的最长子序列是哪个。 比如是j、
        // 然后拿nums[j] 与nums[i] 进行比较，如果nums[i] 比nums[j]大，那么就是递增的、
        // 需要两层循环
        if (nums.length == 0) {
            return 0;
        }
        int[] dp = new int[nums.length];
        // 代表自己
        dp[0] = 1;
        int maxlans = 1;
        for (int index = 1; index < nums.length; index++) {
            dp[index] = 1;
            // 这里是拿从头开始的数据进行比较
            for (int j = 0; j < index; j++) {
                if (nums[index] > nums[j]) {
                    // 这里计算下现在的index存储的递增序列值是否比前面那个位置的序列大。
                    dp[index] = Math.max(dp[index], dp[j] + 1);
                }
            }
            maxlans = Math.max(maxlans, dp[index]);
        }
        return maxlans;
    }
}
// @lc code=end
