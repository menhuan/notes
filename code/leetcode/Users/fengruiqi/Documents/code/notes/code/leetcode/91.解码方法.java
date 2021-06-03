/*
 * @lc app=leetcode.cn id=91 lang=java
 *
 * [91] 解码方法
 */

// @lc code=start
class Solution {
    public int numDecodings(String s) {
        int n = s.length();
        // 动态规划方程求解。
        // 分为两种情况 f(i) 表示的是0...i 的解码次数
        // 如果最后一位操作的话 f(i) = f(i-1) 最后一位不能等于0
        // 还有一种情况是使用了 两种字符。 s(i-1) s（i） f(i) = f(i-2)
        // 组合起来的数字 必须小于等于26 10* s(i-1) + s(i) 在字符内
        // 两种情况加起来才是计算出来的结果
        int[] dp = new int[n + 1];
        // 字符串是空的话 解析出来空字符串
        dp[0] = 1;
        for (int index = 1; index < n + 1; index++) {
            if (s.charAt(index - 1) != '0') {
                dp[index] += dp[index - 1];
            }
            if (index > 1 && s.charAt(index - 2) != '0'
                    && ((s.charAt(index - 2) - '0') * 10 + (s.charAt(index - 1) - '0')) <= 26) {
                dp[index] += dp[index - 2];
            }

        }
        return dp[n];

    }
}
// @lc code=end
