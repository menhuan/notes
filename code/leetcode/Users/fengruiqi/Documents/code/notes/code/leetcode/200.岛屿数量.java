/*
 * @lc app=leetcode.cn id=200 lang=java
 *
 * [200] 岛屿数量
 */

// @lc code=start
class Solution {

    int[] dx = { -1, 1, 0, 0 };
    int[] dy = { 0, 0, -1, 1 };
    char[][] g;

    public int numIslands(char[][] grid) {
        // 这个题目将1 变成0进行递归搜索
        int step = 0;
        g = grid;
        for (int index = 0; index < grid.length; index++) {
            for (int j = 0; j < grid[index].length; j++) {
                if (g[index][j] == '0')
                    continue;
                step += sink(index, j);
            }
        }
        return step;
    }

    public int sink(int i, int j) {
        // 结束条件
        if (g[i][j] == '0') {
            return 0;
        }

        // 本层处理
        g[i][j] = '0';

        for (int index = 0; index < dx.length; index++) {
            int newx = dx[index] + i, newy = dy[index] + j;
            if (newx > 0 && newx < g.length && newy > 0 && newy < g[i].length) {
                if (g[newx][newy] == '0')
                    continue;
                sink(newx, newy);
            }
        }

        return 1;
    }
}
// @lc code=end
