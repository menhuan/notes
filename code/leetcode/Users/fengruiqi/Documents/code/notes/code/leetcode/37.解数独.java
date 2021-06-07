import java.util.ArrayList;
import java.util.List;

/*
 * @lc app=leetcode.cn id=37 lang=java
 *
 * [37] 解数独
 */

// @lc code=start
class Solution {

    private boolean[][] lines = new boolean[9][9];

    private boolean[][] columns = new boolean[9][9];

    private boolean[][][] boxs = new boolean[3][3][9];

    // 用来存储空白的字符位置{i,j} i 代表行坐标，j代表列坐标
    private List<int[]> spaces = new ArrayList<int[]>();
    // 用来检验是否出现重复数字
    private boolean valid = false;

    /**
     * 首先需要三个数组 来存储下每一行数据是否已经在数组中填充过了。并且下标代表的是第几行第几个数
     * 
     * @param board
     */
    public void solveSudoku(char[][] board) {
        // 进行第一遍遍历
        for (int line = 0; line < board.length; line++) {
            for (int column = 0; column < board[0].length; column++) {
                if (board[line][column] == '.') {
                    spaces.add(new int[] { line, column });
                } else {
                    // 代表上面数字不是空格。
                    // 转换成数字 - ‘0’ 转成数字 -1代表的是存储在数组的位置

                    int digit = board[line][column] - '0' - 1;
                    // 这个数字存在了那么在这里设置成true;
                    lines[line][digit] = columns[column][digit] = boxs[line / 3][column / 3][digit] = true;

                }
            }
        }
        // 开始填充数据
        dfs(board, 0);

    }

    public void dfs(char[][] board, int pos) {
        // 递归终止条件 填充的数字满足了一共存在的空格数量
        if (pos == spaces.size()) {
            valid = true;
            return;
        }
        // 从空格里面取出来数据
        int[] space = spaces.get(pos);
        // 空格所在的位置
        int i = space[0], j = space[1];
        // 下面填充数字
        for (int digit = 0; digit < 9 && !valid; digit++) {
            // 如果三个都不存在 那么继续下一步 这里是过滤插入的数据
            if (!lines[i][digit] && !columns[j][digit] && !boxs[i / 3][j / 3][digit]) {
                lines[i][digit] = columns[j][digit] = boxs[i / 3][j / 3][digit] = true;
                board[i][j] = (char) (digit + '0' + 1);
                // 遍历下一个空格
                dfs(board, pos + 1);
                // 进行回溯 ,这里不需要对上面填充的数组进行回溯。
                lines[i][digit] = columns[j][digit] = boxs[i / 3][j / 3][digit] = false;

            }

        }
    }
}
// @lc code=end
