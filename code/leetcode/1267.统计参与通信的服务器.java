/*
 * @lc app=leetcode.cn id=1267 lang=java
 *
 * [1267] 统计参与通信的服务器
 */

// @lc code=start
class Solution {
    public int countServers(int[][] grid) {
        int[] row = new int[grid.length];
        int[] column = new int[grid[0].length];
        Arrays.fill(row, 0);
        Arrays.fill(column, 0);
        for (int index = 0; index < grid.length; index++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[index][j] == 1) {
                    row[index] += 1;
                    column[j] += 1;
                }
            }
        }
        int ans = 0;
        for (int index = 0; index < grid.length; index++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[index][j] == 1 && (row[index] > 1 || column[j] > 1)) {
                    ans += 1;
                }
            }
        }
        return ans;
    }
}
// @lc code=end
