/*
 * @lc app=leetcode.cn id=122 lang=java
 *
 * [122] 买卖股票的最佳时机 II
 */

// @lc code=start
class Solution {
    public int maxProfit(int[] prices) {
        // 动态规划方程，设计到多个交易，所以需要使两个状态来表示
        if (prices.length == 1) {
            return 0;
        }
        int n = prices.length;
        int[][] dp = new int[n][2];
        // 代表没有持有股票的最大利润
        dp[0][0] = 0;
        dp[0][1] = 0 - prices[0];
        for (int index = 1; index < n; index++) {
            // 没有股票或者前面有，今天卖出了
            // 购买股票的时候已经把消耗金额计算到里面，所以卖出的时候卖出多少就是多少收益
            // 不需要再计算 差值
            dp[index][0] = Math.max(dp[index - 1][0], dp[index - 1][1] + prices[index]);
            dp[index][1] = Math.max(dp[index - 1][0] - prices[index], dp[index - 1][1]);
        }

        // 持有股票会减少一些收益
        return dp[n - 1][0];
    }
}
// @lc code=end
