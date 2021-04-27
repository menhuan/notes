/*
 * @lc app=leetcode.cn id=69 lang=java
 *
 * [69] x 的平方根
 */

// @lc code=start
class Solution {
    public int mySqrt(int x) {
        // 使用二分查找
        // 写二分查找的步骤
        /**
         * 1. 确定二分法的上下限
         * 2. 确定中间值怎么写
         * 3. 大于和小于中间值时，start 或者顶部值变为mid上下
         * 4. 关于等于怎么处理呢？这个根据题目要求特殊处理了。
         * 
         */
        int start =0,r=x,ans =-1;
        while(start <= r){
            int mid = start+ (r-start)/2;
            if ((long)mid * mid <=x){
                ans = mid;
                start = mid +1;
            }else{
                r = mid -1;
            }
        }
        return ans;

        

    }
}
// @lc code=end

