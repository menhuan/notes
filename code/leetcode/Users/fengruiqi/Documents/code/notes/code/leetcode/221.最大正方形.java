/*
 * @lc app=leetcode.cn id=221 lang=java
 *
 * [221] 最大正方形
 */

// @lc code=start
class Solution {
    public int maximalSquare(char[][] matrix) {
        // 最大面积 就是找到最大的边。
        // 所以我们应该找到的是求边的动态规划方程
        // 我们假设 dp[i,j] 坐标的最大边，那么就是的知道他上一个正方形的边大小
        // 则是求三个方向。
        int maxSide = 0;
        if (matrix == null || matrix.length == 0) {
            return maxSide;
        }

        int row = matrix.length, col = matrix[0].length;
        int[][] dp = new int[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                // 等于1 的时候才会开始计算最大边
                if (matrix[i][j] == '1') {
                    // 边界条件
                    if (i == 0 || j == 0) {
                        dp[i][j] = 1;
                    } else {
                        dp[i][j] = Math.min(dp[i - 1][j], Math.min(dp[i - 1][j - 1], dp[i][j - 1])) + 1;

                    }
                    // 最大编程可能是1 在边界上
                    maxSide = Math.max(maxSide, dp[i][j]);
                }
            }
        }
        return maxSide * maxSide;
    }
}
// @lc code=end
