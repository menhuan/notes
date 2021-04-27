/*
 * @lc app=leetcode.cn id=70 lang=java
 *
 * [70] 爬楼梯
 */

// @lc code=start
class Solution {
    public int climbStairs(int n) {
        /**
         * 动态规划解答
         * 动态规划 首先是每个问题都可以分解成小的子问题来解决
         * 然后根据子问题可以根据前一个问题找到下一个问题的结果
         */
        int[] result = new int[n+1];
        result[0]=1;
        result[1]=1;
        for(int index=2;index <=n;index++){
            result[index]=result[index-1]+result[index-2];
        }
        return result[n];

    }
}
// @lc code=end

