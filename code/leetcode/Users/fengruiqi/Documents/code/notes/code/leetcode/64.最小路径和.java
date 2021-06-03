/*
 * @lc app=leetcode.cn id=64 lang=java
 *
 * [64] 最小路径和
 */

// @lc code=start
class Solution {
    public int minPathSum(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] dp = new int[m][n];
        dp[0][0] = grid[0][0];
        for (int index = 1; index < m; index++) {
            dp[index][0] = dp[index - 1][0] + grid[index][0];
        }
        for (int j = 1; j < n; j++) {
            dp[0][j] = dp[0][j - 1] + grid[0][j];
        }

        for (int row = 1; row < m; row++) {
            for (int column = 1; column < n; column++) {
                dp[row][column] = Math.min(dp[row - 1][column], dp[row][column - 1]) + grid[row][column];
            }
        }

        return dp[m - 1][n - 1];
    }
}
// @lc code=end
