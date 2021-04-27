/*
 * @lc app=leetcode.cn id=714 lang=java
 *
 * [714] 买卖股票的最佳时机含手续费
 */

// @lc code=start
class Solution {
    public int maxProfit(int[] prices, int fee) {
        // 定义状态 \textit{dp}[i][0]dp[i][0] 表示第 ii 天交易完后手里没有股票的最大利润，\
        // textit{dp}[i][1]dp[i][1] 表示第 ii 天交易完后手里持有一支股票的最大利润（ii 从 00 开始）
        int n = prices.length;
        int[][] dp = new int[n][2];
        dp[0][0] = 0;
        dp[0][1] = -prices[0];
        for (int index = 1; index < n; index++) {
            // dp[index][0] 代表两种情况 前一天持有股票dp[index - 1][1]，然后今天卖出， + price - fee
            // 前一天没有持有股票，所以今天也没有持有,代表值是dp[index - 1 ][0]
            dp[index][0] = Math.max(dp[index - 1][0], dp[index - 1][1] + prices[index] - fee);
            // dp[index][1] 供养有两种情况。昨天没有骨片，然后今天购买了一个股票 dp[index-1][0] -price[]
            // 原先就有股票，今天不买 dp[index -1][1]
            dp[index][1] = Math.max(dp[index - 1][1], dp[index - 1][0] - prices[index]);
        }
        // dp[i][1] 代表的是手里面有一只股票的最大利润，肯定没有不持有股票最大收益方便。
        return dp[n - 1][0];
    }
}
// @lc code=end
