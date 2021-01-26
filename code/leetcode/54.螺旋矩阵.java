import java.util.List;

/*
 * @lc app=leetcode.cn id=54 lang=java
 *
 * [54] 螺旋矩阵
 */

// @lc code=start
class Solution {
    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> order =new ArrayList<Integer>();
        if (matrix == null  || matrix.length ==0 || matrix[0].length ==0 ){
            return order;
        }

        int mLength = matrix.length;
        int nLength = matrix[0].length;
        // 定义好上下左右的坐标
        int top = 0 ,buttom = mLength -1 ,left =0 ,right=nLength-1;
        while(left<=right && top<= buttom){
            for(int cloumn = left;cloumn<=right;cloumn++){
                order.add(matrix[top][cloumn]);
            }
            for(int row=top+1;row<=buttom;row++){
                order.add(matrix[row][right]);
            }
            if(left< right && top < buttom){
                for(int row = right -1 ; row >=left; row --){
                    order.add(matrix[buttom][row]);
                }
                for(int column = buttom -1; column >=top+1;column --){
                    order.add(matrix[column][left]);
                }
            }
            // 遍历完一层
            top++;
            left++;
            buttom--;
            right--;

        }
        return order;
    }
}
// @lc code=end

