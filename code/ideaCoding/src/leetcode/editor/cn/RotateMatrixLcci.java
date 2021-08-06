//给你一幅由 N × N 矩阵表示的图像，其中每个像素的大小为 4 字节。请你设计一种算法，将图像旋转 90 度。 
//
// 不占用额外内存空间能否做到？ 
//
// 
//
// 示例 1: 
//
// 
//给定 matrix = 
//[
//  [1,2,3],
//  [4,5,6],
//  [7,8,9]
//],
//
//原地旋转输入矩阵，使其变为:
//[
//  [7,4,1],
//  [8,5,2],
//  [9,6,3]
//]
// 
//
// 示例 2: 
//
// 
//给定 matrix =
//[
//  [ 5, 1, 9,11],
//  [ 2, 4, 8,10],
//  [13, 3, 6, 7],
//  [15,14,12,16]
//], 
//
//原地旋转输入矩阵，使其变为:
//[
//  [15,13, 2, 5],
//  [14, 3, 4, 1],
//  [12, 6, 8, 9],
//  [16, 7,10,11]
//]
// 
//
// 注意：本题与主站 48 题相同：https://leetcode-cn.com/problems/rotate-image/ 
// Related Topics 数组 数学 矩阵 
// 👍 180 👎 0

package leetcode.editor.cn;
public class RotateMatrixLcci{
      public static void main(String[] args) {
           Solution solution = new RotateMatrixLcci().new Solution();
      }
      //leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public void rotate(int[][] matrix) {
        // 该题目是N*N的矩阵
        int n = matrix.length;
        int[][] matrix_new = new int[n][n];
        // 迁移到新的数组中
        for(int index =0;index < n ;index++){
            for(int j =0;j < n ;j++){
                matrix_new[j][n-index-1] = matrix[index][j];
            }
        }
        // 迁移到老的数组中
        for(int index =0 ;index< n ;index++){
            for(int j =0;j <n ;j++){
                matrix[index][j] = matrix_new[index][j];
            }
        }

    }
}
//leetcode submit region end(Prohibit modification and deletion)

}

