import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/*
 * @lc app=leetcode.cn id=36 lang=java
 *
 * [36] 有效的数独
 */

// @lc code=start
class Solution {
    public boolean isValidSudoku(char[][] board) {
        // 这道题目 是怎么区分下每个 3*3的九宫格。 可以使用 求这个一个区域的box_index
        // (row /3) *3 + columns /3
        // 另外一点就是行 与列怎么存储，这里是不用用到set的。一行就是一个数据 所以针对每一行放一个set
        Map<Integer, Set> rows = new HashMap<Integer, Set>();
        Map<Integer, Set> columns = new HashMap<Integer, Set>();
        Map<Integer, Set> boxs = new HashMap<Integer, Set>();
        for (int index = 0; index < board.length; index++) {
            rows.put(index, new HashSet<>());
            columns.put(index, new HashSet<>());
            boxs.put(index, new HashSet<>());
        }

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                char num = board[row][col];
                if (num != '.') {
                    int box_index = (row / 3) * 3 + col / 3;
                    if (!rows.get(row).add(num) || !columns.get(col).add(num) || !boxs.get(box_index).add(num)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
// @lc code=end
