/*
 * @lc app=leetcode.cn id=48 lang=java
 *
 * [48] 旋转图像
 */

// @lc code=start
class Solution {
    public void rotate(int[][] matrix) {
        // 旋转90度 ，先水平翻转，再进行对角线翻转
        int size = matrix.length;
        // x代表行 J代表列
        for( int x =0 ;x < size/2 ; x++){
            //水平翻转 列都要进行翻转
            for(int j = 0 ; j < size;j++){
                int tmp = matrix[size -1 -x][j];
                matrix[size-1-x][j] = matrix[x][j];
                matrix[x][j] = tmp;
            }
        }
        // 对角线翻转
        for(int  x=0;x  < size ;x++){
            for(int j =0 ; j < x;j++){
                int tmp = matrix[x][j];
                matrix[x][j] = matrix[j][x];
                matrix[j][x] = tmp;
            }
        }

    }
}
// @lc code=end

