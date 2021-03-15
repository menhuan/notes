/*
 * @lc app=leetcode.cn id=1572 lang=java
 *
 * [1572] 矩阵对角线元素的和
 */

// @lc code=start
class Solution {
    public int diagonalSum(int[][] mat) {
        int sum = 0;
        for (int i = 0; i < mat.length;) {
            if ((mat.length - 1 - i) != i) {
                sum += mat[i][i] + mat[i][mat[0].length - 1 - i];
            } else {
                sum += mat[i][i];
            }

            i++;

        }
        return sum;

    }
}
// @lc code=end
