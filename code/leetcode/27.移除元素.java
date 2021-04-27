/*
 * @lc app=leetcode.cn id=27 lang=java
 *
 * [27] 移除元素
 */

// @lc code=start
class Solution {
    public int removeElement(int[] nums, int val) {
        if (nums.length ==0) return 0;
        // 慢指针
        int i = 0;
        for(int j =0;j<nums.length;j++){
            if (nums[j] != val ){
                nums[i]=nums[j];
                i++;
            }
        }
        return  i;
    }
}
// @lc code=end

