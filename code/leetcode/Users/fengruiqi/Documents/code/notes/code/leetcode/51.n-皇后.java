import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
/*
 * @lc app=leetcode.cn id=51 lang=java
 *
 * [51] N 皇后
 */

// @lc code=start
class Solution {
    private List<List<String>> ans;
    private Set<Integer> na;
    private Set<Integer> column;
    private Set<Integer> pie;

    public List<List<String>> solveNQueens(int n) {
        ans = new ArrayList();
        na = new HashSet<>();
        column = new HashSet<>();
        // 在没有在撇里面需要判断 行+column 和行- column在没在pie里面
        pie = new HashSet<>();
        int[] queues = new int[n];
        Arrays.fill(queues, -1);
        dfs(0, n, queues);
        return ans;
    }

    /**
     * 
     * @param cur    当前运行到第几行了
     * @param n      最大的行数
     * @param indexs 存储放的皇后的n行 n 列
     */
    public void dfs(int cur, int n, int[] queues) {

        if (cur == n) {
            // 生成棋盘
            ans.add(productBoard(queues, n));
            return;
        }
        // process 对列进行遍历 上面的cur是行，在dfs中的时候递增行即可
        for (int index = 0; index < n; index++) {

            // 如果这一列在上面set中那么就终止本次的循环
            if (column.contains(index)) {
                continue;
            }
            if (pie.contains(cur + index)) {
                continue;
            }
            if (na.contains(cur - index)) {
                continue;
            }
            // 将其加入到棋盘中
            queues[cur] = index;

            // 在规则中都加上该数据
            column.add(index);
            pie.add(cur + index);
            na.add(cur - index);

            dfs(cur + 1, n, queues);
            // 将其数据移出出来 下面是进行回溯计算 将插入的数据移出来
            queues[cur] = -1;

            column.remove(index);
            pie.remove(cur + index);
            na.remove(cur - index);

        }

    }

    /**
     * 数组queue 代表一个行 value代表这一行的列位置
     * 
     * @param queues 放的皇后的位置
     * @param n      代表的是棋盘的大小
     * @return
     */
    public List<String> productBoard(int[] queues, int n) {
        List<String> board = new ArrayList();
        // 生成
        for (int index = 0; index < n; index++) {
            // 一行数据
            char[] empty = new char[n];
            // 填充.
            Arrays.fill(empty, '.');
            empty[queues[index]] = 'Q';
            board.add(new String(empty));
        }
        return board;
    }

}
// @lc code=end
