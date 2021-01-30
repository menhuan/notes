import javax.swing.tree.TreeNode;
/*
 * @lc app=leetcode.cn id=226 lang=java
 *
 * [226] 翻转二叉树
 */

// @lc code=start
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public TreeNode invertTree(TreeNode root) {
        if (root == null ){
            return root;
        }
        invertTree(root.left);
        invertTree(root.right);
        TreeNode left = root.left ;
        root.left = root.right;
        root.right = left;
        return root ;
    }

    
}
// @lc code=end

