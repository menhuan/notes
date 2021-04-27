/*
 * @lc app=leetcode.cn id=9 lang=java
 *
 * [9] 回文数
 */

// @lc code=start
class Solution {
    public boolean isPalindrome(int x) {
       
     
        if (x < 0 || (x%10==0 && x!=0)){
            
            return false;
        }
        int order_num = 0;
        while(order_num <= x){
            final int tmp = x % 10;
            x /= 10;
            order_num = order_num * 10 + tmp;
        }
        if (order_num / 10 == x || order_num == x) {
            return true;
        } else {
            return false;
        }

    }
}
// @lc code=end

