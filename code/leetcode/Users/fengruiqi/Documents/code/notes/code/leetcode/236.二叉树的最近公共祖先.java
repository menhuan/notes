import javax.swing.tree.TreeNode;

/*
 * @lc app=leetcode.cn id=236 lang=java
 *
 * [236] 二叉树的最近公共祖先
 */

// @lc code=start
/**
 * Definition for a binary tree node. public class TreeNode { int val; TreeNode
 * left; TreeNode right; TreeNode(int x) { val = x; } }
 */
class Solution {
    private TreeNode ans = null;

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // 最近公共祖先有两种情况 一个是他们都属于左右节点 ， 一种是他们自身。
        // 截止条件
        dfs(root, p, q);
        return ans;

    }

    public boolean dfs(TreeNode root, TreeNode p, TreeNode q) {
        // 终止条件
        if (root == null) {
            return false;
        }
        // 还是对本层次的去做 检查下一层
        boolean leftContain = dfs(root.left, p, q);
        boolean rightContain = dfs(root.right, p, q);

        // 本层的处理逻辑
        boolean layer = leftContain || rightContain || (root.val == q.val || root.val == p.val);

        if ((leftContain && rightContain)
                || ((root.val == p.val || root.val == q.val) && (leftContain || rightContain))) {
            // 设置祖先
            ans = root;
        }
        return layer;

    }
}
// @lc code=end
