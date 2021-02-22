import java.util.LinkedList;
import java.util.Queue;

/*
 * @lc app=leetcode.cn id=695 lang=java
 *
 * [695] 岛屿的最大面积
 */

// @lc code=start
class Solution {
    public int maxAreaOfIsland(int[][] grid) {
        // 广度优先遍历
        int ans = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                int cur = 0;
                Queue<Integer> queuei = new LinkedList<>();
                Queue<Integer> queuej = new LinkedList<>();
                queuei.offer(i);
                queuej.offer(j);
                while (!queuei.isEmpty()) {
                    int curi = queuei.poll(), curj = queuej.poll();
                    if (curi < 0 || curj < 0 || curi == grid.length || curj == grid[0].length
                            || grid[curi][curj] != 1) {
                        continue;
                    }
                    cur++;
                    // 修改为0
                    grid[curi][curj] = 0;
                    int[] ax = { 0, 0, 1, -1 };
                    int[] by = { 1, -1, 0, 0 };
                    for (int index = 0; index < 4; index++) {
                        int x = curi + ax[index], y = curj + by[index];
                        queuei.offer(x);
                        queuej.offer(y);
                    }
                }
                ans = Math.max(ans, cur);

            }
        }
        return ans;
    }
}
// @lc code=end
