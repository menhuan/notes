/*
 * @lc app=leetcode.cn id=123 lang=java
 *
 * [123] 买卖股票的最佳时机 III
 */

// @lc code=start
class Solution {
    public int maxProfit(int[] prices) {
        // 主要是定义多种状态、
        if (prices.length == 1) {
            return 0;
        }
        int n = prices.length;
        int[][] dp = new int[n][5];
        // 5个操作状态 因为0的时候都不需要操作，所以不会有利润存在
        dp[0][1] = -prices[0];
        dp[0][2] = 0; // 卖
        dp[0][3] = -prices[0]; // 第二次买入
        dp[0][4] = 0; // 第二次卖出
        for (int index = 1; index < n; index++) {
            // 第一次买入计算 上面没有买过
            dp[index][1] = Math.max(dp[index - 1][1], -prices[index]);
            dp[index][2] = Math.max(dp[index - 1][2], dp[index - 1][1] + prices[index]);
            dp[index][3] = Math.max(dp[index - 1][3], dp[index - 1][2] - prices[index]);
            dp[index][4] = Math.max(dp[index - 1][4], dp[index - 1][3] + prices[index]);
        }
        return dp[n - 1][4];
    }

}
// @lc code=end
