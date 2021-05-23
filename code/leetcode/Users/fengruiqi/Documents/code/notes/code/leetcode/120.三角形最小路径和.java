import java.util.List;

/*
 * @lc app=leetcode.cn id=120 lang=java
 *
 * [120] 三角形最小路径和
 */

// @lc code=start
class Solution {
    public int minimumTotal(List<List<Integer>> triangle) {
        int size = triangle.size();
        if (size == 1) {
            return triangle.get(0).get(0);
        }
        // 做动态规划的话 可以考虑从后面进行计算

        int[] result = new int[size + 1];
        for (int index = triangle.size() - 1; index >= 0; index--) {
            for (int j = 0; j < triangle.get(index).size(); j++) {
                // 循环的更新前面数组的结果
                result[j] = triangle.get(index).get(j) + Math.min(result[j], result[j + 1]);
            }
        }
        return result[0];

    }
}
// @lc code=end
