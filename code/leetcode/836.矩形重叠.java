/*
 * @lc app=leetcode.cn id=836 lang=java
 *
 * [836] 矩形重叠
 */

// @lc code=start
class Solution {
    public boolean isRectangleOverlap(int[] rec1, int[] rec2) {
        return (Math.min(rec1[2], rec2[2])>Math.max(rec1[0], rec2[0]) && Math.min(rec1[3], rec2[3])>Math.max(rec1[1], rec2[1]));
    }
}
// @lc code=end

