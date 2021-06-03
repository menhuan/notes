/*
 * @lc app=leetcode.cn id=72 lang=java
 *
 * [72] 编辑距离
 */

// @lc code=start
class Solution {
    public int minDistance(String word1, String word2) {
        // 拆解问题 后面看到两个字符串的操作 可以考虑将两个字符串组合成二维数组进行计算
        int m = word1.length();
        int n = word2.length();
        // 有个字符串是空串，那么就是领挖一个字符串的长度
        if (n * m == 0) {

            return n + m;
        }
        int[][] dp = new int[m + 1][n + 1];
        for (int index = 0; index < m + 1; index++) {
            dp[index][0] = index;

        }
        for (int j = 0; j < n + 1; j++) {
            dp[0][j] = j;
        }

        for (int row = 1; row < m + 1; row++) {
            for (int col = 1; col < n + 1; col++) {
                int left = dp[row - 1][col] + 1;
                int down = dp[row][col - 1] + 1;
                int left_down = dp[row - 1][col - 1];
                if (word1.charAt(row - 1) != word2.charAt(col - 1)) {
                    left_down += 1;
                }

                dp[row][col] = Math.min(left, Math.min(down, left_down));
            }
        }
        return dp[m][n];
    }
}
// @lc code=end
