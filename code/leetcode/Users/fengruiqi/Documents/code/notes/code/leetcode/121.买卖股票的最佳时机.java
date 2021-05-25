/*
 * @lc app=leetcode.cn id=121 lang=java
 *
 * [121] 买卖股票的最佳时机
 */

// @lc code=start
class Solution {
    public int maxProfit(int[] prices) {
        // 使用一个位置来判断买入了
        if (prices.length == 1) {
            return 0;
        }
        int min = prices[0];
        int[] dp = new int[prices.length];
        // dp方程 当前的最大利润
        dp[0] = 0;
        int max = 0;

        // 每一天的最大利润。 dp[i] = 前面的最小值与当前的计算
        for (int index = 1; index < prices.length; index++) {
            min = Math.min(min, prices[index]);
            dp[index] = Math.max(prices[index] - min, dp[index - 1]);
        }
        return dp[prices.length - 1];
    }
}
// @lc code=end
