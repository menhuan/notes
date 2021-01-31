import java.util.List;

/*
 * @lc app=leetcode.cn id=120 lang=java
 *
 * [120] 三角形最小路径和
 */

// @lc code=start
class Solution {
    private Integer minValue = Integer.MAX_VALUE;

    public int minimumTotal(List<List<Integer>> triangle) {
        // 这道题可看到每一个i j 位置的数据都是来自于上一个数据构成，
        // 那么构成上一个数据之后，就可以得到当前位置数据 这样的方式就可以协程动态规划来做
        int size = triangle.size();
        int[][] sumResult = new int[size][size];
        // 刚开始的数据
        sumResult[0][0] = triangle.get(0).get(0);
        for (int index = 1; index < size; index++) {
            // 第一个结果
            sumResult[index][0] = sumResult[index - 1][0] + triangle.get(index).get(0);
            for (int j = 1; j < index; j++) {
                sumResult[index][j] = Math.min(sumResult[index - 1][j - 1], sumResult[index - 1][j])
                        + triangle.get(index).get(j);
            }
            // 最后一个
            sumResult[index][index] = sumResult[index - 1][index - 1] + triangle.get(index).get(index);
        }
        int min = Integer.MAX_VALUE;
        for (int index = 0; index < size; index++) {
            min = Math.min(sumResult[size - 1][index], min);
        }
        return min;
    }

    // 下面这个方式失败的原因是消耗的时间太长，递归时间条多导致的。
    public void minSum(List<List<Integer>> triangle, int sum, int position) {
        /**
         * triangle 代表这次遍历的数据 sum 代表上一层值的和。 position 上一个位置
         */

        if (triangle.size() == 1) {
            var list = triangle.get(0);
            int minSum = Integer.MAX_VALUE;
            if (list.size() >= 2) {
                minSum = Math.min(sum + list.get(position), sum + list.get(position + 1));
            } else {
                minSum = sum + list.get(position);
            }
            minValue = Math.min(minSum, minValue);
            return;
        }
        for (int index = position; index <= position + 1 && index < triangle.get(0).size(); index++) {
            System.out.println(index);

            int currentSum = sum + triangle.get(0).get(index);
            minSum(triangle.subList(1, triangle.size()), currentSum, index);
        }

    }
}
// @lc code=end
