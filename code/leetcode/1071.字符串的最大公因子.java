/*
 * @lc app=leetcode.cn id=1071 lang=java
 *
 * [1071] 字符串的最大公因子
 */

// @lc code=start
class Solution {
    public String gcdOfStrings(String str1, String str2) {
        //相同 任意拼接 都是相等的
        if (!(str1 + str2).equals(str2+ str1)){
            return "";
        }
        // 求最大公约数
        return str1.substring(0,gcd(str1.length(), str2.length()));

    }
    private int gcd(int a,int b ){
        System.out.println(a);
        System.out.println(b);
        return  a%b == 0 ? b: gcd(b,a%b);
    }
}
// @lc code=end

