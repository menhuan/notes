/*
 * @lc app=leetcode.cn id=746 lang=java
 *
 * [746] 使用最小花费爬楼梯
 */

// @lc code=start
class Solution {
    public int minCostClimbingStairs(int[] cost) {
        int length = cost.length;
        int[] dp = new int[length + 1];
        dp[0] = dp[1] = 0;
        for (int index = 2; index <= length; index++) {
            dp[index] = Math.min(dp[index - 1] + cost[index - 1], dp[index - 2] + cost[index - 2]);
        }
        return dp[length];
    }
}
// @lc code=end
