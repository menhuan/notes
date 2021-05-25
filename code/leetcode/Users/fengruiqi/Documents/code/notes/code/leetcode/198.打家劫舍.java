/*
 * @lc app=leetcode.cn id=198 lang=java
 *
 * [198] 打家劫舍
 */

// @lc code=start
class Solution {
    public int rob(int[] nums) {

        // 计算金额的最大，对于这个位置可选与不可选两种，
        // 定义动态转移方程 f[i] 0...1 这个代表位置上的最大值。二维数据0 1 代表房屋选与不选
        // 不选[0] 则只需要计算上一个位置的最大值。 本次选了那么上一个就不能选择
        // 只有一个的话就不需要进行计算了
        if (nums.length == 1) {
            return nums[0];
        }
        int n = nums.length;
        int[][] res = new int[n][2];
        res[0][0] = 0;
        res[0][1] = nums[0];
        for (int index = 1; index < n; index++) {
            res[index][0] = Math.max(res[index - 1][0], res[index - 1][1]);
            res[index][1] = res[index - 1][0] + nums[index];
        }
        return Math.max(res[n - 1][0], res[n - 1][1]);
    }
}
// @lc code=end
