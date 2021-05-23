import java.util.Arrays;

/*
 * @lc app=leetcode.cn id=322 lang=java
 *
 * [322] 零钱兑换
 */

// @lc code=start
class Solution {
    public int coinChange(int[] coins, int amount) {
        // 数字 走到11 ，我们把这个认为是走楼梯。进行计算。
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);
        dp[0] = 0;
        for (int index = 1; index <= amount; index++) {
            for (int j = 0; j < coins.length; j++) {
                if (coins[j] <= index) {
                    dp[index] = Math.min(dp[index], dp[index - coins[j]] + 1);
                }
            }
        }
        return dp[amount] > amount ? -1 : dp[amount];

    }
}
// @lc code=end
