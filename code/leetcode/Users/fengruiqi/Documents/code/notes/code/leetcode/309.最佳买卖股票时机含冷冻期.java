/*
 * @lc app=leetcode.cn id=309 lang=java
 *
 * [309] 最佳买卖股票时机含冷冻期
 */

// @lc code=start
class Solution {
    public int maxProfit(int[] prices) {
        // 上一个计算步骤中是 可以买卖两次。相当于设计了多个状态。
        // 这个相当于多了一个状态。
        if (prices.length == 1) {
            return 0;
        }
        int n = prices.length;
        int[][] dp = new int[n][3];
        // dp[][0] 代表持有一个股票。 1代表没有持有股票处于冷冻期， 2 代表没有持有股票不处于冷冻期
        // dp[i]代表的是当前位置的最大利润
        dp[0][0] = -prices[0];
        dp[0][1] = 0;
        dp[0][2] = 0; // 冷冻期不能交易 所以利润是卖出时的利润
        for (int index = 1; index < n; index++) {
            // 在index持有一个股票 要不是原先就持有的 或者是上次卖出后这次再次买入的
            dp[index][0] = Math.max(dp[index - 1][0], dp[index - 1][2] - prices[index]);
            // 处于冷冻期 那么代表的是持有的股票在今天被卖出
            dp[index][1] = dp[index - 1][0] + prices[index];
            // 没有持有股票，并且也不处于冷冻期
            dp[index][2] = Math.max(dp[index - 1][1], dp[index - 1][2]);
        }
        return Math.max(dp[n - 1][1], dp[n - 1][2]);
    }
}
// @lc code=end
