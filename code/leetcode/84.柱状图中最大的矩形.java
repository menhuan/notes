/*
 * @lc app=leetcode.cn id=84 lang=java
 *
 * [84] 柱状图中最大的矩形
 */

// @lc code=start
class Solution {
    public int largestRectangleArea(int[] heights) {
        // 暴力循环 时间超过限制。
        // 计算最大的面积，需要确定的是宽和高
        int ans = 0;
        int size = heights.length;

        for (int left = 0; left < size; left++) {
            int minHeight = Integer.MAX_VALUE;

            for (int right = left; right < size; right++) {
                minHeight = Math.min(minHeight, heights[right]);
                ans = Math.max(ans, (right - left + 1) * minHeight);

            }
        }
        return ans;
    }
}
// @lc code=end
