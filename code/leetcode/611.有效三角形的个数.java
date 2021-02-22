/*
 * @lc app=leetcode.cn id=611 lang=java
 *
 * [611] 有效三角形的个数
 */

// @lc code=start
class Solution {
    public int triangleNumber(int[] nums) {
        int count = 0;
        if (nums.length < 3) {
            return count;
        }
        for (int index = 0; index < nums.length - 2; index++) {
            if (nums[index] == 0) {
                continue;
            }
            for (int b = index + 1; b < nums.length - 1; b++) {
                if (nums[b] == 0) {
                    continue;
                }
                for (int c = b + 1; c < nums.length; c++) {
                    if (nums[c] == 0) {
                        continue;
                    }

                    if (nums[index] + nums[b] > nums[c] && nums[index] + nums[c] > nums[b]
                            && nums[b] + nums[c] > nums[index] && nums[index] - nums[b] < nums[c]
                            && nums[index] - nums[c] < nums[b] && nums[c] - nums[b] < nums[index]) {
                        count++;
                    }
                }
            }

        }
        return count;

    }
}
// @lc code=end
