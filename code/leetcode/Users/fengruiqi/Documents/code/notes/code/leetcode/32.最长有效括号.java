/*
 * @lc app=leetcode.cn id=32 lang=java
 *
 * [32] 最长有效括号
 */

// @lc code=start
class Solution {
    public int longestValidParentheses(String s) {
        int maxans = 0;
        // 代表的是i位置上 存储的最大字串长度
        int[] dp = new int[s.length()];
        for (int i = 1; i < s.length(); i++) {
            // 如果这个字串为结束标志进行计数计算
            if (s.charAt(i) == ')') {
                // 上一个能匹配上 代表加2
                if (s.charAt(i - 1) == '(') {
                    dp[i] = (i >= 2 ? dp[i - 2] : 0) + 2;

                }
                // 这里是处理的往前面找跟自己匹配的左括号。 例如 (()()()()()) 对于
                // 最后一个来说我们要找到进行匹配的那么已经需要在 找到中间字串的长度，这个就是dp[i-1] +2代表最后一个字符串跟前面的匹配上
                // i- dp[i-2] >2 代表的是 字串之前的 有效的字符串长度加上 比如()()()(()) 除了计算(()) 之外 还要加上前面的()()长度
                else if (i - dp[i - 1] > 0 && s.charAt(i - dp[i - 1] - 1) == '(') {

                    dp[i] = dp[i - 1] + 2 + ((i - dp[i - 1]) >= 2 ? dp[i - dp[i - 1] - 2] : 0);
                }
                maxans = Math.max(maxans, dp[i]);
            }
        }
        return maxans;

    }
}
// @lc code=end
