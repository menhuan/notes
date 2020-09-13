/*
 * @lc app=leetcode.cn id=415 lang=java
 *
 * [415] 字符串相加
 */

// @lc code=start
class Solution {
    public String addStrings(String num1, String num2) {
        int num1_max = num1.length() -1 ;
        int num2_max = num2.length() -1 ;
        int add = 0; // 进位
        StringBuilder builder = new StringBuilder();
        while(num1_max >=0 || num2_max >=0 || add >0){
            int value = num1_max >= 0 ? num1.charAt(num1_max) - '0':0;
            int value2 = num2_max >= 0 ? num2.charAt(num2_max) - '0':0;
            int result = value + value2 + add;
            builder.append(result%10);
            System.out.print(result);
            System.out.print(value);
            System.out.print(value2);
            System.out.print(add);

            add = result/10;
            num1_max-- ;
            num2_max--;
        }
        builder.reverse();
        return builder.toString();
    }
}
// @lc code=end

