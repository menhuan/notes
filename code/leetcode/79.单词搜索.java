/*
 * @lc app=leetcode.cn id=79 lang=java
 *
 * [79] 单词搜索
 */

// @lc code=start
class Solution {
    public boolean exist(char[][] board, String word) {
        // i j = 当前的这个值是返回结果为Treu
        int length = board.length, w = board[0].length;
        boolean[][] verit = new boolean[length][w];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < w; j++) {
                boolean flag = check(board, word, i, j, 0, verit);
                if (flag) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean check(char[][] board, String word, int i, int j, int k, boolean[][] verit) {
        if (board[i][j] != word.charAt(k)) {
            return false;
        } else if (k == word.length() - 1) {
            return true;
        }

        verit[i][j] = true;
        boolean result = false;
        int[][] position = { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 } };
        for (int index = 0; index < position.length; index++) {
            int newx = i + position[index][0], newy = j + position[index][1];
            if (newx >= 0 && newy >= 0 && newx < board.length && newy < board[0].length) {
                if (!verit[newx][newy]) {
                    boolean flag = check(board, word, newx, newy, k + 1, verit);
                    if (flag) {
                        result = true;
                        break;
                    }
                }
            }
        }
        verit[i][j] = false;
        return result;

    }
}
// @lc code=end
