/*
 * @lc app=leetcode.cn id=75 lang=java
 *
 * [75] 颜色分类
 */

// @lc code=start
class Solution {
    public void sortColors(int[] nums) {
        //
        int length = nums.length;
        int pre = 0;
        for (int index = 0; index < length; index++) {
            if (nums[index] == 0) {
                int tmp = nums[pre];
                nums[pre] = nums[index];
                nums[index] = tmp;
                pre++;
            }
        }
        for (int index = pre; index < length; index++) {
            if (nums[index] == 1) {
                int tmp = nums[pre];
                nums[pre] = nums[index];
                nums[index] = tmp;
                pre++;
            }

        }

    }
}
// @lc code=end
