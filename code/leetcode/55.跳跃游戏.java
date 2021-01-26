/*
 * @lc app=leetcode.cn id=55 lang=java
 *
 * [55] 跳跃游戏
 */

// @lc code=start
class Solution {
    public boolean canJump(int[] nums) {
        // 正向计算
        int size = nums.length;
        int end = 0 ;
        int maxPosition =0;
        int ans = 0 ;
        boolean jump = false;
        for(int index=0;index < size;index ++){
            if (index <=maxPosition){
                maxPosition = Math.max(maxPosition,index + nums[index]);
                if (maxPosition >= size -1 ){
                    jump = true;
                }
            }
           
        }
        
        return jump;
    }
}
// @lc code=end

