import java.util.Arrays;

/*
 * @lc app=leetcode.cn id=747 lang=java
 *
 * [747] 至少是其他数字两倍的最大数
 */

// @lc code=start
class Solution {
    public int dominantIndex(int[] nums) {
        int max = -1;
        int output = -1;
        for (int index = 0; index < nums.length; index++) {
            if (nums[index] > max) {
                max = nums[index];
                output = index;
            }
        }
        for (int num : nums) {

            if (num != 0 && num != max && max - num - num < 0) {
                output = -1;
                return output;
            }
        }
        return output;
    }
}
// @lc code=end
