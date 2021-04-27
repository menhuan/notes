/*
 * @lc app=leetcode.cn id=62 lang=java
 *
 * [62] 不同路径
 */

// @lc code=start
class Solution {
    public int uniquePaths(int m, int n) {
        // 最后一个节点是可以由多个节点数据构成的，所以可以写
        // 冬天规划方程。
        int[][] nums = new int[m][n];
        // 注意考虑边界问题 初始化边界数据都为1
        for (int index = 0; index < m; index++) {
            nums[index][0] = 1;
        }
        for (int index = 1; index < n; index++) {
            nums[0][index] = 1;
        }
        for (int index = 1; index < m; index++) {
            for (int j = 1; j < n; j++) {
                nums[index][j] = nums[index - 1][j] + nums[index][j - 1];
            }
        }
        return nums[m - 1][n - 1];
    }
}
// @lc code=end
