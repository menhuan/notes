import java.util.Arrays;

/*
 * @lc app=leetcode.cn id=213 lang=java
 *
 * [213] 打家劫舍 II
 */

// @lc code=start
class Solution {
    public int rob(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        }
        if (nums.length == 2) {
            return Math.max(nums[0], nums[1]);
        }

        // 因为是相连接的 所以 0 不选择的话，那么最大值就是数组的最后一位
        // 但是选择了数组0，那么就不能选择最后一个房屋,为了按照上一个步骤方式做，所以拆分为两个数组
        return Math.max(maxValue(Arrays.copyOfRange(nums, 0, nums.length)),
                maxValue(Arrays.copyOfRange(nums, 1, nums.length + 1)));
    }

    public int maxValue(int[] nums) {
        int n = nums.length;
        int[][] res = new int[n][2];
        res[0][0] = 0;
        res[0][1] = nums[0];
        int max = 0;
        for (int index = 1; index < n; index++) {
            res[index][0] = Math.max(res[index - 1][0], res[index - 1][1]);
            res[index][1] = res[index - 1][0] + nums[index];
            max = Math.max(max, Math.max(res[index - 1][0], res[index - 1][1]));
        }
        return max;
    }
}
// @lc code=end
