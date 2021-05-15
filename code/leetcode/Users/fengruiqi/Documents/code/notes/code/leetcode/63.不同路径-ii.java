/*
 * @lc app=leetcode.cn id=63 lang=java
 *
 * [63] 不同路径 II
 */

// @lc code=start
class Solution {
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
        int[][] dp = new int[m][n];
        for (int row = 0; row < m; row++) {
            if (obstacleGrid[row][0] == 1)
                break;
            dp[row][0] = 1;
        }
        for (int column = 0; column < n; column++) {
            if (obstacleGrid[0][column] == 1)
                break;
            dp[0][column] = 1;
        }

        for (int index = 1; index < m; index++) {
            for (int j = 1; j < n; j++) {
                dp[index][j] = obstacleGrid[index][j] == 1 ? 0 : dp[index - 1][j] + dp[index][j - 1];
            }
        }
        return dp[m - 1][n - 1];
    }
}
// @lc code=end
