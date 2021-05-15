import java.util.Arrays;

/*
 * @lc app=leetcode.cn id=62 lang=java
 *
 * [62] 不同路径
 */

// @lc code=start
class Solution {
    public int uniquePaths(int m, int n) {
        // 走到最后的 一步 ，那么就是 f(x,y) = f(x-1,y) + f(x,y-1)
        // 确定边界
        int[][] dp = new int[m][n];
        // 行
        for (int index = 0; index < m; index++) {
            dp[index][0] = 1;
        }
        for (int column = 0; column < n; column++) {
            dp[0][column] = 1;
        }

        for (int index = 1; index < m; index++) {
            for (int j = 1; j < n; j++) {
                dp[index][j] = dp[index - 1][j] + dp[index][j - 1];
            }
        }

        return dp[m - 1][n - 1];

    }
}
// @lc code=end
