/*
 * @lc app=leetcode.cn id=1143 lang=java
 *
 * [1143] 最长公共子序列
 */

// @lc code=start
class Solution {
    public int longestCommonSubsequence(String text1, String text2) {
        int m = text1.length(), n = text2.length();

        int[][] dp = new int[m + 1][n + 1];
        for (int index = 1; index < m + 1; index++) {
            for (int j = 1; j < n + 1; j++) {
                char A = text1.charAt(index - 1);
                char B = text2.charAt(j - 1);
                if (A == B) {
                    // 代表当前的字符是相等的
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
