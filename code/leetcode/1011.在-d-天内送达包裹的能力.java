/*
 * @lc app=leetcode.cn id=1011 lang=java
 *
 * [1011] 在 D 天内送达包裹的能力
 */

// @lc code=start
class Solution {
    public int shipWithinDays(int[] weights, int D) {
        int sum = 0;
        int max = 0;
        for (int index = 0; index < weights.length; index++) {
            sum += weights[index];
            max = Math.max(weights[index], max);
        }
        boolean notBreak = true;
        while (notBreak) {
            if (max * D < sum) {
                max++;
            }
            int partSum = 0;
            int times = 0;
            boolean end = false;
            for (int index = 0; index < weights.length; index++) {
                partSum += weights[index];
                if (partSum > max) {
                    // 记一次数
                    times++;
                    // 因为比原先的数字大，所以把这个数字多承载的数量丢掉。
                    partSum -= weights[index];
                    index--;
                    partSum = 0;
                    // 如果 次数比天大了 那么就需要终止掉。
                    if (times >= D) {
                        max++;

                        break;

                    }

                }
                if (index == weights.length - 1 && times <= D - 1 && max * D >= sum) {
                    notBreak = false;
                }
            }

        }

        return max;
    }
}
// @lc code=end
