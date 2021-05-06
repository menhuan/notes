import javax.swing.tree.TreeNode;

/*
 * @lc app=leetcode.cn id=111 lang=java
 *
 * [111] 二叉树的最小深度
 */

// @lc code=start
/**
 * Definition for a binary tree node. public class TreeNode { int val; TreeNode
 * left; TreeNode right; TreeNode() {} TreeNode(int val) { this.val = val; }
 * TreeNode(int val, TreeNode left, TreeNode right) { this.val = val; this.left
 * = left; this.right = right; } }
 */
class Solution {

    public int minDepth(TreeNode root) {
        // 递归的终止条件
        if (root == null) {
            return 0;
        }
        // 本层的处理
        if (root.left == null && root.right == null) {
            return 1;
        }
        // 下一层的处理
        int minDepth = Integer.MAX_VALUE;
        if (root.left != null) {
            minDepth = Math.min(minDepth(root.left), minDepth);
        }
        if (root.right != null) {
            minDepth = Math.min(minDepth(root.right), minDepth);
        }

        // 最后的处理工作，返回该层的值
        return minDepth + 1;

    }
}
// @lc code=end
