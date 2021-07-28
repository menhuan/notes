/*
 * @lc app=leetcode.cn id=72 lang=java
 *
 * [72] 编辑距离
 */

// @lc code=start
class Solution {
    public int minDistance(String word1, String word2) {
        // 看到这个题目 就转换成 二维数组
        int m = word1.length();
        int n = word2.length();

        int[][] dp = new int[m + 1][n + 1];
        for (int index = 0; index <= m; index++) {
            dp[index][0] = index;
        }

        for (int index = 0; index <= n; index++) {
            dp[0][index] = index;
        }
        for (int index = 1; index <= m; index++) {
            for (int j = 1; j <= n; j++) {
                // 分为两种情况，最后一个字符相等于不相等的问题、
                int left = dp[index][j - 1] + 1;
                int down = dp[index - 1][j] + 1;
                int left_down = dp[index - 1][j - 1];
                if (word1.charAt(index - 1) != word2.charAt(j - 1)) {
                    left_down += 1;
                }
                dp[index][j] = Math.min(left, Math.min(down, left_down));

            }
        }

        return dp[m][n];
    }
}
// @lc code=end
