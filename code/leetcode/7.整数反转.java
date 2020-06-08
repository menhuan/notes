/*
 * @lc app=leetcode.cn id=7 lang=java
 *
 * [7] 整数反转
 */

// @lc code=start
class Solution {
    public int reverse(int x) {
        int new_x = 0;
        while(x!=0){
          // 老数据取出最后一位
          int tmp =  x%10;
          x/=10;
          // Integer.MAX_VALUE == 2147483647
          if(new_x> Integer.MAX_VALUE/10 || (new_x == Integer.MAX_VALUE && tmp>7 )) {return 0;}
          if(new_x< Integer.MIN_VALUE/10 || (new_x == Integer.MIN_VALUE && tmp <-8)) {return 0;}
          int temp = new_x * 10 + tmp; //防止在计算该数据时，这个数据下标越界进行提前计算
          new_x = temp; 

        }
        return new_x;
    }
}
// @lc code=end

