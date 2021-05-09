import java.util.List;

import javax.swing.tree.TreeNode;

/*
 * @lc app=leetcode.cn id=515 lang=java
 *
 * [515] 在每个树行中找最大值
 */

// @lc code=start
/**
 * Definition for a binary tree node. public class TreeNode { int val; TreeNode
 * left; TreeNode right; TreeNode() {} TreeNode(int val) { this.val = val; }
 * TreeNode(int val, TreeNode left, TreeNode right) { this.val = val; this.left
 * = left; this.right = right; } }
 */
class Solution {
    private List<Integer> ans;

    public List<Integer> largestValues(TreeNode root) {
        // 每一层最大值放到对应的位置上。
        ans = new ArrayList();
        dfs(root, 0);
        // 如果用遍历的话 那么就好算了 不过这道题目要求用递归那就用递归吧
        return ans;
    }

    /**
     * 
     * @param root  代表当前的节点
     * @param level 代表第几层
     */
    public void dfs(TreeNode root, int level) {
        // 递归的终止条件
        if (root == null) {
            return;
        }

        // 本层处理，当层数超过size存储的数据 那么就直接+
        // 没超过层数就直接更改。这个代表已经在列表里面存储了
        if (level == ans.size()) {
            ans.add(root.val);
        } else {
            ans.set(level, Math.max(ans.get(level), root.val));
        }
        dfs(root.left, level + 1);
        dfs(root.right, level + 1);

    }
}
// @lc code=end
