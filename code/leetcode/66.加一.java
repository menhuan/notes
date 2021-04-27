/*
 * @lc app=leetcode.cn id=66 lang=java
 *
 * [66] 加一
 */

// @lc code=start
class Solution {
    public int[] plusOne(int[] digits) {
        // 加1 考虑量两种情况  值为9和不为9的情况，需要判断进位+1、
        for(var index=digits.length-1;index>=0;index--){
            var value = (digits[index]+1)%10;
            if (value !=0) {
                digits[index]=digits[index]+1;
                return digits;
            }else{
                digits[index]=0;
            }
        }
        digits = new int[digits.length +1];
        digits[0]=1;
        return digits;
    }
}
// @lc code=end

