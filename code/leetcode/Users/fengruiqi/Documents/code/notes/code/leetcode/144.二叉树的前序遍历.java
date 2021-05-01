import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.TreeNode;

/*
 * @lc app=leetcode.cn id=144 lang=java
 *
 * [144] 二叉树的前序遍历
 */

// @lc code=start
/**
 * Definition for a binary tree node. public class TreeNode { int val; TreeNode
 * left; TreeNode right; TreeNode() {} TreeNode(int val) { this.val = val; }
 * TreeNode(int val, TreeNode left, TreeNode right) { this.val = val; this.left
 * = left; this.right = right; } }
 */
class Solution {
    public List<Integer> preorderTraversal(TreeNode root) {
        // 前序遍历 根节点 左节点 右节点
        if (root == null) {
            return new ArrayList<>();
        }
        List<Integer> list = new ArrayList<>();
        helper(root, list);
        return list;

    }

    public void helper(TreeNode root, List<Integer> list) {
        if (root != null) {
            list.add(root.val);
            // 左节点
            helper(root.left, list);
            // 右节点
            helper(root.right, list);
        }
    }
}
// @lc code=end
