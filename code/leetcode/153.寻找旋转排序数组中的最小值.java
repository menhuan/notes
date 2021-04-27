/*
 * @lc app=leetcode.cn id=153 lang=java
 *
 * [153] 寻找旋转排序数组中的最小值
 */

// @lc code=start
class Solution {
    public int findMin(int[] nums) {
        // 使用二分法进行计算
        int left = 0, right = nums.length - 1;
        int min = 1000000;
        while (left <= right) {
            int mid = (left + right) / 2;
            // 要不在前面是升序
            min = Math.min(nums[mid], min);
            if (nums[mid] >= nums[nums.length - 1]) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return min;

    }
}
// @lc code=end
