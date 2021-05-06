/*
 * @lc app=leetcode.cn id=50 lang=java
 *
 * [50] Pow(x, n)
 */

// @lc code=start
class Solution {
    public double myPow(double x, int n) {
        // 目标是进行数据的递归计算
        // 注意负数处理
        return n > 0 ? generate(x, n) : 1.0 / generate(x, n);
    }

    public double generate(double x, int n) {
        // 实现幂运算 要求的是进行 n/2
        if (n == 0) {
            return 1.0;
        }

        // 下一层进行处理
        double low = generate(x, n / 2);
        // 本层处理返回值
        return n % 2 == 0 ? low * low : low * low * x;

    }
}
// @lc code=end
