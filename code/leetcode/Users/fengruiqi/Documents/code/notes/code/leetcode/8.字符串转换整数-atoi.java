/*
 * @lc app=leetcode.cn id=8 lang=java
 *
 * [8] 字符串转换整数 (atoi)
 */

// @lc code=start
class Solution {
    public int myAtoi(String s) {
        // 第一步去掉字符串的空格
        String str = s.trim();
        // 这时候字符串长度为0，那么就直接返回0
        if (str.length() == 0)
            return 0;
        // isDigit 用于判断字符是否为数字，不为数字的话 第一位是否是正负号行为
        if (!Character.isDigit(str.charAt(0)) && str.charAt(0) != '-' && str.charAt(0) != '+')
            return 0;
        // 转换结果
        int ans = 0;
        // 确定后面的正负位
        boolean neg = str.charAt(0) == '-';
        // 如果只有一个正负位就不需要进行循环做了
        int i = !Character.isDigit(str.charAt(0)) ? 1 : 0;
        while (i < str.length() && Character.isDigit(str.charAt(i))) {
            // 进行溢出的处理
            int tmp = ((neg ? Integer.MIN_VALUE : Integer.MIN_VALUE + 1) + (str.charAt(i) - '0')) / 10;
            if (tmp > ans) {
                return neg ? Integer.MIN_VALUE : Integer.MAX_VALUE;
            }
            // 上一位先乘以10 为啥是－
            System.out.println(ans);
            // ans = ans * 10 + (str.charAt(i++) - '0') 这种方式会提前溢出，所以采用反着来的结果计算
            ans = ans * 10 - (str.charAt(i++) - '0');
            System.out.println(ans);

        }
        return neg ? ans : -ans;
    }
}
// @lc code=end
