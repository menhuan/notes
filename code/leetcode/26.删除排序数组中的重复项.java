

/*
 * @lc app=leetcode.cn id=26 lang=java
 *
 * [26] 删除排序数组中的重复项
 */

// @lc code=start
class Solution {
    public int removeDuplicates(int[] nums) {
        // 快慢指针，一个代表当前位置，另外一个快速的去遍历寻找
        if (nums.length == 0) return 0;
        int i = 0;
        for (int j = 1; j < nums.length; j++) {
            if (nums[j] != nums[i]) {
                i++;
                nums[i] = nums[j];
            }
        }
        return i + 1;

        /**下面是我的思路 */
        // int nums_length = nums.length;

        // for(int index=0;index<nums_length;index++){

        //     if (index+1 >=nums_length){
        //        break;
        //     }
        //     while(nums[index] ==nums[index+1]){
        //         if (index +2 >=nums_length){
        //             break;
        //         }
        //         nums[index+1] = nums[index+2];
        //     }
        // }
        
        // int result_length=0;
        // for(int index=0;index<nums_length;index++){

        //     if(index+1==nums_length){
        //         nums =Arrays.copyOfRange(nums, 0, result_length);
        //         return result_length;
        //     }
        //     if(nums[index]==nums[index+1]){
        //         nums =Arrays.copyOfRange(nums, 0, result_length+1);
        //         return result_length+1;
        //     }else{
        //         result_length+=1;   
        //     }
        // }
        // return result_length;
    }
}
// @lc code=end

