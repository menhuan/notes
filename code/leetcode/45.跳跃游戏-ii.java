/*
 * @lc app=leetcode.cn id=45 lang=java
 *
 * [45] 跳跃游戏 II
 */

// @lc code=start
class Solution {
    public int jump(int[] nums) {
        // 跳到目标位置 采用正向的方式来跳
        int length = nums.length;
        int maxPosition = 0;
        int end = 0;
        int step = 0;
        // 最后一步肯定跳到或者跳过去，所以减少最后一次的跳跃
        for(int point =0; point < length-1 ; point++){
            maxPosition = Math.max(maxPosition,point + nums[point]);
            // 代表的是跳到的结束位置与当前坐标一致了，更换end下标，病step +1
            if (point == end){
                end = maxPosition;
                step++;
            }
            // 过滤下
            if (end > length -1){
                break;
            }
        }
        return step;
    }
}
// @lc code=end

