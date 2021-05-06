import java.util.Arrays;

/*
 * @lc app=leetcode.cn id=105 lang=java
 *
 * [105] 从前序与中序遍历序列构造二叉树
 */

// @lc code=start
/**
 * Definition for a binary tree node. public class TreeNode { int val; TreeNode
 * left; TreeNode right; TreeNode() {} TreeNode(int val) { this.val = val; }
 * TreeNode(int val, TreeNode left, TreeNode right) { this.val = val; this.left
 * = left; this.right = right; } }
 */
class Solution {
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        // 前序 是 根节点 左节点 右节点
        // 中序是 左节点 根节点 右节点

        // 终止条件
        if (preorder.length == 0 || inorder.length == 0) {
            return null;
        }
        int rootVal = preorder[0];
        // 找到 中序遍历结果中的
        int left = 0;
        // 这里可以使用map进行优化
        for (int index = 0; index < inorder.length; index++) {
            if (inorder[index] == rootVal) {
                left = index;
                break;
            }
        }
        TreeNode node = new TreeNode(rootVal);
        node.left = buildTree(Arrays.copyOfRange(preorder, 1, preorder.length), Arrays.copyOfRange(inorder, 0, left));
        node.right = buildTree(Arrays.copyOfRange(preorder, left + 1, preorder.length),
                Arrays.copyOfRange(inorder, left + 1, inorder.length));
        return node;
    }

}
// @lc code=end
