import javax.swing.tree.TreeNode;

/*
 * @lc app=leetcode.cn id=98 lang=java
 *
 * [98] 验证二叉搜索树
 */

// @lc code=start
/**
 * Definition for a binary tree node. public class TreeNode { int val; TreeNode
 * left; TreeNode right; TreeNode() {} TreeNode(int val) { this.val = val; }
 * TreeNode(int val, TreeNode left, TreeNode right) { this.val = val; this.left
 * = left; this.right = right; } }
 */
class Solution {
    public boolean isValidBST(TreeNode root) {
        // 验证一棵树 是不是二叉搜索树，就是校验左节点小于根节点，根节点小于右节点。
        // 第二部分就是 该子二叉搜索树 必须小于父节点的 右节点。也就是右节点有上限。
        return checkNode(root, Long.MAX_VALUE, Long.MIN_VALUE);
    }

    public boolean checkNode(TreeNode node, long rightMax, long leftMin) {
        // 终止条件
        if (node == null) {
            return true;
        }

        // 本层处理的判断条件，给定一个上下限制
        if (node.val >= rightMax || node.val <= leftMin) {
            return false;
        }

        // 下一层的限制
        // 左节点的限制
        boolean leftResult = checkNode(node.left, node.val, leftMin);
        // 右节点的检查
        boolean rightResult = checkNode(node.right, rightMax, node.val);

        // 最后的处理。两遍都符合的话才能机选
        return leftResult && rightResult;

    }

}
// @lc code=end
