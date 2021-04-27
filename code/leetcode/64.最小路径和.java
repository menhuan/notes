/*
 * @lc app=leetcode.cn id=64 lang=java
 *
 * [64] 最小路径和
 */

// @lc code=start
class Solution {
    public int minPathSum(int[][] grid) {
        // 动态硅规划路径和。作为边界
        int length = grid.length;
        int row = 0;
        if (length > 0) {
            row = grid[0].length;
        }

        int[][] nums = new int[length][row];
        nums[0][0] = grid[0][0];
        for (int index = 1; index < length; index++) {
            nums[index][0] = grid[index][0] + nums[index - 1][0];
        }
        for (int index = 1; index < row; index++) {
            nums[0][index] = grid[0][index] + nums[0][index - 1];
        }
        for (int index = 1; index < length; index++) {
            for (int j = 1; j < row; j++) {
                nums[index][j] = Math.min(nums[index - 1][j], nums[index][j - 1]) + grid[index][j];
            }
        }

        // for (int index = 0; index < length; index++) {
        // for (int j = 0; j < row; j++) {
        // System.out.println(nums[index][j]);
        // }
        // }
        return nums[length - 1][row - 1];

    }
}
// @lc code=end
