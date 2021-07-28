/*
 * @lc app=leetcode.cn id=1143 lang=java
 *
 * [1143] 最长公共子序列
 */

// @lc code=start
class Solution {
    public int longestCommonSubsequence(String text1, String text2) {
        // 动态规划、最长子序列
        int m = text1.length();
        int n = text2.length();
        int[][] dp = new int[m + 1][n + 1];
        // 进行行和列的初始化
        for (int index = 1; index <= m; index++) {
            for (int j = 1; j <= n; j++) {
                if (text1.charAt(index - 1) == text2.charAt(j - 1)) {
                    dp[index][j] = dp[index - 1][j - 1] + 1;
                } else {
                    dp[index][j] = Math.max(dp[index - 1][j], dp[index][j - 1]);
                }
            }
        }
        return dp[m][n];
    }
}
// @lc code=end
