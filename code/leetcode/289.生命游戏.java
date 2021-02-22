/*
 * @lc app=leetcode.cn id=289 lang=java
 *
 * [289] 生命游戏
 */

// @lc code=start
class Solution {
    public void gameOfLife(int[][] board) {
        // 确定行和列

        int[] neighbor = { 0, 1, -1 };
        int rows = board.length;
        int columns = board[0].length;
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[0].length; column++) {

                int liveNumber = 0;
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (!(neighbor[i] == 0 && neighbor[j] == 0)) {
                            int r = (neighbor[i] + row);
                            int c = (neighbor[j] + column);
                            // 判断细胞是否存储
                            if ((r < rows && r >= 0) && (c < columns && c >= 0) && Math.abs(board[r][c]) == 1) {
                                liveNumber += 1;
                            }
                        }
                    }
                }
                // 找到存活的数量。然后按照规则进行处理
                // 规则一或者3 都是活细胞进行死亡。将该位置置为-1
                if (board[row][column] == 1 && (liveNumber < 2 || liveNumber > 3)) {
                    board[row][column] = -1;
                }
                if (board[row][column] == 0 && liveNumber == 3) {
                    board[row][column] = 2;
                }
            }

        }
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                if (board[row][column] > 0) {
                    board[row][column] = 1;
                } else {
                    board[row][column] = 0;
                }
            }

        }

    }
}
// @lc code=end
