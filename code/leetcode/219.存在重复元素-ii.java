/*
 * @lc app=leetcode.cn id=219 lang=java
 *
 * [219] 存在重复元素 II
 */

// @lc code=start
class Solution {
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        if (nums.length == 0 || nums.length == 1) {
            return false;
        }
        int start = 0, end = 1;
        while (end < nums.length) {
            if (nums[end] == nums[start] && end - start <= k) {
                return true;
            }
            // 会这个是需要注意边界问题 end比较快速，可能会很快就打到 了边界
            if (end - start == k || end + 1 == nums.length) {
                start++;
                end = start + 1;
                continue;
            }
            end++;

        }
        return false;
    }
}
// @lc code=end
