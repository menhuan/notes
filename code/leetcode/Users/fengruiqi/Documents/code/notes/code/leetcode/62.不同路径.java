import java.util.Arrays;

/*
 * @lc app=leetcode.cn id=62 lang=java
 *
 * [62] 不同路径
 */

// @lc code=start
class Solution {
    public int uniquePaths(int m, int n) {
        // 现在我们假设是走到最后完成了，那么想要得到最后完成的结果就是需要
        // 上面的步数，才能得到结果对不对、
        // 所以我们可以写出来动态方程式f(x,y) = f(x-1,y) + f(x,y-1);
        // 为什么要用两位数组呢，这是因为要记录下行和列
        // 再次思考下，我们有必要用二维数组来存储中间过程吗？
        // 其实一位数据就可以计算出来。就是在本行计算的时候加上下原先的计算结果就行
        if (m * n <= 1) {
            return 1;
        }
        // 定义数组 按照列存储
        int[] dp = new int[n];
        Arrays.fill(dp, 1);

        for (int index = 1; index < m; index++) {
            for (int j = 1; j < n; j++) {
                // 这里是加上了原先保存的号的结果
                dp[j] += dp[j - 1];
            }
        }

        return dp[n - 1];

    }
}
// @lc code=end
