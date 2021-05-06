/*
 * @lc app=leetcode.cn id=70 lang=java
 *
 * [70] 爬楼梯
 */

// @lc code=start
class Solution {
    public int climbStairs(int n) {
        // 看到了 1 2 个台阶 重复解问题。
        if (n <= 2)
            return n;
        int f1 = 1, f2 = 2, f3 = 3;
        for (int index = 3; index <= n; index++) {
            // 进行数据的交换 保证是最新的
            f3 = f1 + f2;
            // 只需要知道前两步 的数据，所以对下一步数据来说交换保存好数据就行
            f1 = f2;
            f2 = f3;
        }
        return f3;
    }
}
// @lc code=end
