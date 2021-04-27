import java.util.ArrayList;
import java.util.List;

/*
 * @lc app=leetcode.cn id=59 lang=java
 *
 * [59] 螺旋矩阵 II
 */

// @lc code=start
class Solution {
    public int[][] generateMatrix(int n) {
        // 第一步生成 n * n的所有元素
        List<Integer> nums = new ArrayList(n * n);
        for (int index = 1; index <= n * n; index++) {
            nums.add(index);
        }
        int numsIndex = 0;
        int top = 0, left = 0, right = n - 1, buttom = n - 1;
        int[][] merge = new int[n][n];
        while (top <= buttom && left <= right) {
            for (int index = left; index <= right; index++) {
                merge[top][index] = nums.get(numsIndex);
                numsIndex++;
            }
            for (int index = top + 1; index <= buttom; index++) {
                merge[index][right] = nums.get(numsIndex);
                numsIndex++;
            }
            if (top < buttom && left < right) {
                for (int index = right - 1; index >= left; index--) {
                    merge[buttom][index] = nums.get(numsIndex);
                    numsIndex++;
                }
                for (int index = buttom - 1; index > top; index--) {
                    merge[index][left] = nums.get(numsIndex);
                    numsIndex++;
                }
            }
            top++;
            left++;
            buttom--;
            right--;

        }
        return merge;

    }
}
// @lc code=end
