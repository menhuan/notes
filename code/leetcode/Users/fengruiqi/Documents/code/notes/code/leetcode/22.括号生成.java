import java.util.ArrayList;
import java.util.List;

/*
 * @lc app=leetcode.cn id=22 lang=java
 *
 * [22] 括号生成
 */

// @lc code=start
class Solution {

    private List<String> ans;

    public List<String> generateParenthesis(int n) {
        // 如果想生成一个括号需要什么呢？ 那么左括号与右括号必须是匹配的。
        // 第二个数量是一样的，左括号笔试先于右括号生成
        ans = new ArrayList<String>();
        dfs(n, 0, 0, "");
        return ans;
    }

    // 因为这道题目 可以用递归来生成
    /**
     * 
     * @param n     代表数量
     * @param left
     * @param right
     * @param s
     */
    public void dfs(int n, int left, int right, String s) {
        // 递归终止的条件
        if (left == n && right == n) {
            ans.add(s);
            return;
        }
        // 当前层的逻辑
        if (left < n) {
            // 下一层的逻辑
            dfs(n, left + 1, right, s + "(");
        }
        if (right < left) {
            dfs(n, left, right + 1, s + ")");
        }

    }

}
// @lc code=end
