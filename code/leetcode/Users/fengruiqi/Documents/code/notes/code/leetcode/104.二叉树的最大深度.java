/*
 * @lc app=leetcode.cn id=104 lang=java
 *
 * [104] 二叉树的最大深度
 */

// @lc code=start
/**
 * Definition for a binary tree node. public class TreeNode { int val; TreeNode
 * left; TreeNode right; TreeNode() {} TreeNode(int val) { this.val = val; }
 * TreeNode(int val, TreeNode left, TreeNode right) { this.val = val; this.left
 * = left; this.right = right; } }
 */
class Solution {
    private int maxLayer = 0;

    public int maxDepth(TreeNode root) {
        generate(root, 0);
        return maxLayer;
    }

    /**
     * 判断二叉树的最大深度，每一层的话在上一层的基础上+1、然后跟存储的最大层进行比较
     * 
     */
    public void generate(TreeNode node, int layer) {
        // 递归的终止条件
        if (node == null) {
            return;
        }
        // 本层处理。
        layer += 1;
        maxLayer = Math.max(maxLayer, layer);

        // 下一层处理
        generate(node.left, layer);
        generate(node.right, layer);

    }
}
// @lc code=end
