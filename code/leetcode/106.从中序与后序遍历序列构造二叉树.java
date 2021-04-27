import java.util.Arrays;

/*
 * @lc app=leetcode.cn id=106 lang=java
 *
 * [106] 从中序与后序遍历序列构造二叉树
 */

// @lc code=start
/**
 * Definition for a binary tree node. public class TreeNode { int val; TreeNode
 * left; TreeNode right; TreeNode() {} TreeNode(int val) { this.val = val; }
 * TreeNode(int val, TreeNode left, TreeNode right) { this.val = val; this.left
 * = left; this.right = right; } }
 */
class Solution {
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        // 中序遍历 左节点 根节点 右节点
        // 后序节点 左节点 右节点 根节点
        int size = postorder.length;
        if (size == 0) {
            return null;
        }
        if (size == 1) {
            return new TreeNode(inorder[0]);
        }
        int root = postorder[postorder.length - 1];
        TreeNode node = new TreeNode(root);
        Integer rootIndex = null;
        for (int index = 0; index < inorder.length; index++) {
            if (inorder[index] == root) {
                rootIndex = index;
                break;
            }
        }

        if (rootIndex != null) {
            node.left = buildTree(Arrays.copyOfRange(inorder, 0, rootIndex),
                    Arrays.copyOfRange(postorder, 0, rootIndex));
            node.right = buildTree(Arrays.copyOfRange(inorder, rootIndex + 1, inorder.length),
                    Arrays.copyOfRange(postorder, rootIndex, postorder.length - 1));
        }
        return node;
    }
}
// @lc code=end
