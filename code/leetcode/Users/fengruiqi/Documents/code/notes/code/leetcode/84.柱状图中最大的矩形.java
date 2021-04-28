import java.util.Stack;

/*
 * @lc app=leetcode.cn id=84 lang=java
 *
 * [84] 柱状图中最大的矩形
 */

// @lc code=start
class Solution {
    // 如果按照 暴力解决方法的话
    public int largestRectangleArea(int[] heights) {
        // 使用单调栈的方式，让数据成为递增的或者递减的。
        /**
         * 递增那么就把数据一个个放进去，只要大舅放，不大就取出来。
         */
        Stack<Integer> stack = new Stack<>();
        stack.push(-1);
        int maxArea = 0;
        for (int index = 0; index < heights.length; index++) {
            // 循环遍历的与栈顶的元素进行比较
            while (stack.peek() != -1 && heights[stack.peek()] >= heights[index]) {
                maxArea = Math.max(maxArea, heights[stack.pop()] * (index - stack.peek() - 1));
            }
            // 放入的是下标
            stack.push(index);
            // 否则的话
        }
        while (stack.peek() != -1) {
            int bar_heigh = heights[stack.pop()];
            int area = bar_heigh * (heights.length - stack.peek() - 1);
            maxArea = Math.max(maxArea, area);
        }

        return maxArea;
    }
}
// @lc code=end
