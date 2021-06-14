/*
 * @lc app=leetcode.cn id=72 lang=java
 *
 * [72] 编辑距离
 */

// @lc code=start
class Solution {
    public int minDistance(String word1, String word2) {
        // 看到这个问题就可以将其拆解为二维数组的问题。
        int m = word1.length();
        int n = word2.length();

        if (m * n == 0) {
            // 当you一个为0 是那么就是代表m为0，所以返回n的值就行。
            return m != 0 ? m : n;
        }
        // 不为0的情况下，就是使用m 和n组合的二维数组。
        // 使用dp[i][j] 表示字母A的前i个字母和B的前j个字母之间的距离。
        // 对于每个字符串进行A进行插入或者对B进行插入，或者对两者都进行替换操作
        // 增加一个空白字符
        int[][] dp = new int[m + 1][n + 1];
        // 行的初始化
        for (int index = 0; index < m + 1; index++) {
            dp[index][0] = index;
        }
        // 列初始化
        for (int index = 0; index < n + 1; index++) {
            dp[0][index] = index;
        }

        // 如果最后一个字符相等的情况下与不相等的情况需要区分下
        for (int index = 1; index < m + 1; index++) {
            for (int j = 1; j < n + 1; j++) {
                int left = dp[index][j - 1] + 1;
                int down = dp[index - 1][j] + 1;
                int left_down = dp[index - 1][j - 1];
                if (word1.charAt(index - 1) != word2.charAt(j - 1)) {
                    left_down += 1; // 不相等的情况下 需要+1
                }
                dp[index][j] = Math.min(left, Math.min(down, left_down));
            }
        }
        return dp[m][n];

    }
}
// @lc code=end
