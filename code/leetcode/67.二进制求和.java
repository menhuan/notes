/*
 * @lc app=leetcode.cn id=67 lang=java
 *
 * [67] 二进制求和
 */

// @lc code=start
class Solution {
    public String addBinary(String a, String b) {

        var result = new StringBuffer();
        int maxValue =Math.max(a.length(), b.length());
        // 用来计算是否要进位
        var carry =0;
        for(int index =0; index<maxValue;index++){
            // -‘0’ 是用来强转类型的 ,如果index 大于最大的长度我们要置为0 
            // 倒叙输出进行拼接
            carry += index<a.length()?(a.charAt(a.length()-1-index)-'0'):0;
            carry += index<b.length()?(b.charAt(b.length()-1-index)-'0'):0;
            // 取余2计算当前的值
            result.append((char)(carry%2 + '0'));
            //计算是否进位，下一个循环使用
            carry/=2;
        }
        if (carry>0){
            result.append('1');
        }
        //翻转输出
        result.reverse();
        return result.toString();
    }
}
// @lc code=end

