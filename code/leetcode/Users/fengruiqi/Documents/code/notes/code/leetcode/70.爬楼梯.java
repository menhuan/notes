/*
 * @lc app=leetcode.cn id=70 lang=java
 *
 * [70] 爬楼梯
 */

// @lc code=start
class Solution {
    public int climbStairs(int n) {
        // 看到了 1 2 个台阶 重复解问题。
        if (n <= 2) {
            return n;
        }
        int f1 = 1, f2 = 2, f3 = 3;
        int index = 3;
        while (index <= n) {
            f3 = f1 + f2;
            f1 = f2;
            f2 = f3;
            index++;
        }
        return f3;
    }
}
// @lc code=end
