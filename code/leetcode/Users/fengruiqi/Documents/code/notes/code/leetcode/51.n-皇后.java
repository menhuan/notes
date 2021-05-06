import java.util.ArrayList;
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

        return ans;
    }

    /**
     * 
     * @param cur    当前运行到第几行了
     * @param n      最大的行数
     * @param indexs 存储放的皇后的n行 n 列
     */
    public void dfs(int cur, int n, List<Integer> indexs) {

        if (cur == n) {
            // 生成棋盘
            return;
        }
        // process
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
            index.add(index);

            // 在规则中都加上该数据
            column.add(index);
            pie.add(cur + index);
            na.add(cur - index);

            dfs(cur + 1, n, indexs);
            // 将其数据移出出来
            index.remove(index.size() - 1);
            column.remove(index);
            pie.remove(cur + index);
            na.remove(cur - index);

        }

    }

    /**
     * 数组的index代表一个行 value代表队的是列
     * 
     * @param indexs
     * @return
     */
    public String productBoard(List<Integer> indexs) {
        List<String> board = new ArrayList();
        for (int index = 0; index < indexs.size(); index++) {

        }
    }

}
// @lc code=end
