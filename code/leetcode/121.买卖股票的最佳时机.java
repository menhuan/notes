/*
 * @lc app=leetcode.cn id=121 lang=java
 *
 * [121] 买卖股票的最佳时机
 */

// @lc code=start
class Solution {
    public int maxProfit(int[] prices) {
        int minPrice = Integer.MAX_VALUE;
        int maxProfit = 0;
        for (int index = 0; index < prices.length; index++) {
            minPrice = Math.min(prices[index], minPrice);
            if (prices[index] > minPrice) {
                maxProfit = Math.max(prices[index] - minPrice, maxProfit);
            } else {
                // 用来更新过程中的最小价格
                minPrice = prices[index];
            }
        }
        return maxProfit;

    }
}
// @lc code=end
