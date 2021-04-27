import java.util.Arrays;

/*
 * @lc app=leetcode.cn id=169 lang=java
 *
 * [169] 多数元素
 */

// @lc code=start
class Solution {
    public int majorityElement(int[] nums) {
        int numCount = nums.length / 2;
        Arrays.sort(nums);

        return nums[numCount];
    }
}
// @lc code=end
