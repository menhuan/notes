/*
 * @lc app=leetcode.cn id=746 lang=java
 *
 * [746] 使用最小花费爬楼梯
 */

// @lc code=start
class Solution {
    public int minCostClimbingStairs(int[] cost) {
        // 计算最小的 花费。 也就是每一步都是计算最小的花费。
        // 需要确定下 每一步需要花费最小的数据。
        // 定义动态规划里面存储的是最小花费
        int[] dp = new int[cost.length + 1];
        dp[0] = 0;
        // 注意这里可以选择楼梯1作为开始
        dp[1] = 0;
        for (int index = 2; index <= cost.length; index++) {
            dp[index] = Math.min(dp[index - 2] + cost[index - 2], dp[index - 1] + cost[index - 1]);
        }
        return dp[cost.length];
    }
}
// @lc code=end
