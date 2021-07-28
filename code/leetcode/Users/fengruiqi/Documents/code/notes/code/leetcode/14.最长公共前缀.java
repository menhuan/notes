/*
 * @lc app=leetcode.cn id=14 lang=java
 *
 * [14] 最长公共前缀
 */

// @lc code=start
class Solution {
    public String longestCommonPrefix(String[] strs) {
        // 最长公共前缀
        if (strs == null || strs.length == 0) {
            return "";
        }
        int count = strs.length;
        String prefix = strs[0];
        for (int index = 1; index < count; index++) {
            // 找到最小的字符串长度
            int minLength = Math.min(prefix.length(), strs[index].length());
            int start = 0;
            while (start < minLength && prefix.charAt(start) == strs[index].charAt(start)) {
                start++;
            }
            prefix = prefix.substring(0, start);
            if (prefix.length() == 0) {
                return "";
            }
        }
        return prefix;

    }
}
// @lc code=end
