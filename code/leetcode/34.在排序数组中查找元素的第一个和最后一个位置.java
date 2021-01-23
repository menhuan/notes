/*
 * @lc app=leetcode.cn id=34 lang=java
 *
 * [34] 在排序数组中查找元素的第一个和最后一个位置
 */

// @lc code=start
class Solution {
    public int[] searchRange(int[] nums, int target) {
        // 二分法查找对应的数据，找到第一个大于等于目标的位置，以及大于目标的第一个位置
        int left = searchMid(nums, target, true);
        int right = searchMid(nums, target, false)-1;
        if(left <= right  && nums[left] == target && nums[right] == target){
            return new  int[]{left,right};
        }
        return new int[]{-1,-1};
    }
    public int searchMid(int[] nums,int target,boolean lower){
        int left =0,right = nums.length -1,ans =nums.length;
        while(left <= right){
            int mid = (left+right)/2;
            // 如果lower 为true,代表的是相等继续 找低位，为false 代表找高位
            if(nums[mid] > target || (lower && nums[mid] >= target)){
                right = right -1 ;
                ans = mid ;
            }else{
                left = mid +1 ;
            }
        }
        return ans;

    }
}
// @lc code=end

