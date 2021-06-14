/*
 * @lc app=leetcode.cn id=91 lang=java
 *
 * [91] 解码方法
 */

// @lc code=start
class Solution {
    public int numDecodings(String s) {
        int length = s.length();
        // 设置的dp是代表当前index的可解码数量 空字符串也代表一个数据
        int[] dp = new int[length + 1];
        // 针对最后一个字母来说，分为两个情况，就是一个字符，那么就是上一个结果.
        // 如果是两个字符那么就的满足 上一个字符不能为0的情况才能计算。

        // 刚开始空字符符所以可以这么计算
        dp[0] = 1;

        // 下面都是按照－1来找到解码的位置数据 ，所以下面是－2
        for (int index = 1; index <= length; index++) {
            if (s.charAt(index - 1) != '0') {
                dp[index] += dp[index - 1];
            }
            // 这两个关系是并列，需要重复计算，针对最后一个字符的多种情况计算
            if (index > 1 && s.charAt(index - 2) != '0'
                    && ((s.charAt(index - 2) - '0') * 10 + (s.charAt(index - 1) - '0') <= 26)) {
                dp[index] += dp[index - 2];
            }

        }
        return dp[length];

    }
}
// @lc code=end
