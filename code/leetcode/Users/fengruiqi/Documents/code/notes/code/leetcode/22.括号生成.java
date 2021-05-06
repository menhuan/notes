import java.util.ArrayList;
import java.util.List;

/*
 * @lc app=leetcode.cn id=22 lang=java
 *
 * [22] 括号生成
 */

// @lc code=start
class Solution {

    private List<String> list;

    public List<String> generateParenthesis(int n) {
        // 括号的生成 使用递归的方式 ，要不生成 左括号，要不生成右括号
        list = new ArrayList<>();
        generate(0, 0, n, "");
        return list;
    }

    /**
     * 生成需要的括号字符串 left，right 是从0开始的，所以我们在判断小于n，而不是等于n进行计算
     * 
     * @param left  代表左括号的数量
     * @param right 代表右括号的数量
     * @param n     终止条件
     * @param s     生成的字符串
     */
    public void generate(int left, int right, int n, String s) {
        // 括号的生成
        // 递归的终止条件
        if (left == n && right == n) {
            list.add(s);
            return;
        }
        // 本层处理
        if (left < n) {
            generate(left + 1, right, n, s + "(");
        }
        // 左右进行匹配
        if (right < left) {
            generate(left, right + 1, n, s + ")");
        }

        // 终止操作 或者其他操作。

    }

}
// @lc code=end
