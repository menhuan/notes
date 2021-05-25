/*
 * @lc app=leetcode.cn id=714 lang=java
 *
 * [714] 买卖股票的最佳时机含手续费
 */

// @lc code=start
class Solution {
    public int maxProfit(int[] prices, int fee) {
        if (prices.length == 1) {
            return 0;
        }
        int n = prices.length;
        int[][] dp = new int[n][2];
        dp[0][0] = 0;
        dp[0][1] = -prices[0];
        for (int index = 1; index < n; index++) {
            dp[index][0] = Math.max(dp[index - 1][0], dp[index - 1][1] + prices[index] - fee);
            dp[index][1] = Math.max(dp[index - 1][0] - prices[index], dp[index - 1][1]);

        }
        return Math.max(dp[n - 1][0], dp[n - 1][1]);
    }
}
// @lc code=end
