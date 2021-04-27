import java.util.Arrays;

/*
 * @lc app=leetcode.cn id=977 lang=java
 *
 * [977] 有序数组的平方
 */

// @lc code=start
class Solution {
    public int[] sortedSquares(int[] nums) {
        int[] ans = new int[nums.length];
        for (int index = 0; index < nums.length; index++) {
            ans[index] = nums[index] * nums[index];
        }
        Arrays.sort(ans);
        return ans;

    }
}
// @lc code=end
