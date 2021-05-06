/*
 * @lc app=leetcode.cn id=226 lang=java
 *
 * [226] 翻转二叉树
 */

// @lc code=start
/**
 * Definition for a binary tree node. public class TreeNode { int val; TreeNode
 * left; TreeNode right; TreeNode() {} TreeNode(int val) { this.val = val; }
 * TreeNode(int val, TreeNode left, TreeNode right) { this.val = val; this.left
 * = left; this.right = right; } }
 */
class Solution {
    public TreeNode invertTree(TreeNode root) {
        // 翻转二叉树
        generate(root);
        return root;
    }

    public void generate(TreeNode node) {
        // 终止条件
        if (node == null) {
            return;
        }
        // 本层的处理
        TreeNode left = node.left;
        node.left = node.right;
        node.right = left;

        // 下一层的处理
        generate(node.left);
        generate(node.right);
    }
}
// @lc code=end
