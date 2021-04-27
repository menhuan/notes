/*
 * @lc app=leetcode.cn id=122 lang=java
 *
 * [122] 买卖股票的最佳时机 II
 */

// @lc code=start
class Solution {
    public int maxProfit(int[] prices) {
        // 最大利润和
        int maxProfit = 0;
        // 有时候可能会超过限制 注意
        int minPrice = prices[0];
        for (int index = 1; index < prices.length; index++) {
            if (prices[index] < prices[index - 1]) {
                // maxProfit += prices[index - 1] - minPrice;
                minPrice = prices[index];
                continue;
            } else {
                maxProfit += prices[index] - prices[index - 1];
            }
            minPrice = Math.min(prices[index], minPrice);

        }
        return maxProfit;
    }
}
// @lc code=end
