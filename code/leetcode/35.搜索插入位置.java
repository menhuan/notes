/*
 * @lc app=leetcode.cn id=35 lang=java
 *
 * [35] 搜索插入位置
 */

// @lc code=start
class Solution {
    public int searchInsert(int[] nums, int target) {
        var index =0;
        for(int start =0;start <nums.length; start++){
           if (target == nums[start]){
                return start;
           }
        }
        for(int start =0;start <nums.length;start ++) {
            if (start+1 < nums.length){
                if (nums[start] <target && target < nums[start+1]){
                    index = start+1;
                    System.out.println("进来？"+index);
                    return index;
                }
            }
            if (start ==0 && target <nums[start]){
                index = start;
                return index ;
            }
            if(start ==nums.length -1 && target > nums[start]){
                index = start+1;
                return index ;
            }
        }

        return index;
    }
}
// @lc code=end

