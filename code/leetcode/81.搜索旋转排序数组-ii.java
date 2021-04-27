/*
 * @lc app=leetcode.cn id=81 lang=java
 *
 * [81] 搜索旋转排序数组 II
 */

// @lc code=start
class Solution {
    public boolean search(int[] nums, int target) {
        // 目标是使用二分法
        int length = nums.length;
        if (length == 0) {
            return false;
        }
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            // 为啥换成这个就能实现
            int mid = (left + right) / 2;
            // int mid = left + (right - left) / 2;
            if (nums[mid] == target || nums[left] == target || nums[right] == target) {
                return true;
            }
            System.out.println(mid + " " + left + " " + right);
            if (nums[left] == nums[mid]) {
                left++;
                continue;
            }
            if (nums[left] < nums[mid]) {
                if (nums[left] <= target && target < nums[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            } else {
                if (target > nums[mid] && target <= nums[right]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }

        }
        return false;
    }
}
// @lc code=end
