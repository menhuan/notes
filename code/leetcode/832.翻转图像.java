/*
 * @lc app=leetcode.cn id=832 lang=java
 *
 * [832] 翻转图像
 */

// @lc code=start
class Solution {
    public int[][] flipAndInvertImage(int[][] A) {
        int size = A[0].length;
        for (int[] num : A) {
            for (int index = 0; index < (size + 1) / 2; index++) {
                int tmp = num[index] ^ 1;
                num[index] = num[size - index - 1] ^ 1;
                num[size - index - 1] = tmp;
            }
        }
        return A;
    }
}
// @lc code=end
