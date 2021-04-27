import java.util.Collections;
import java.util.List;

/*
 * @lc app=leetcode.cn id=123 lang=java
 *
 * [123] 买卖股票的最佳时机 III
 */

// @lc code=start
class Solution {
    public int maxProfit(int[] prices) {
        // 用来存储利润的结果
        List<Integer> result = new ArrayList();
        int currentIndex = -1;
        int resultIndex = 0;
        // 利润
        int profit = 0;
        // 最小的价格 遇到拐角的时候进行更新
        int minPrice = prices[0];

        for (int index = 1; index < prices.length; index++) {
            if (prices[index] < prices[index - 1]) {
                minPrice = prices[index];
                profit = 0;
                resultIndex++;
                continue;
            } else {
                profit += prices[index] - prices[index - 1];
                if (currentIndex == resultIndex) {
                    result.set(currentIndex, profit);
                } else {
                    result.add(profit);
                    currentIndex++;
                }

            }

        }
        if (result.size() == 0) {
            return 0;
        }
        if (result.size() >= 2) {
            Collections.sort(result);
            return result.get(result.size() - 2) + result.get(result.size() - 1);
        }
        if (result.size() == 1) {
            return result.get(0);
        }
        return 0;
    }
}
// @lc code=end
