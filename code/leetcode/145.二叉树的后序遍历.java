import java.util.ArrayList;
import java.util.List;
/*
 * @lc app=leetcode.cn id=145 lang=java
 *
 * [145] 二叉树的后序遍历
 */

// @lc code=start
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public List<Integer> postorderTraversal(TreeNode root) {
        // 后序遍历是 左 右 跟
        List<Integer> nodeVals = new ArrayList<Integer>();
        postSequence(root,nodeVals);
        return nodeVals;
    }

    public void  postSequence(TreeNode node ,List<Integer> nodeVals){
        if (node == null) {
            return ;
        }
        postSequence(node.left, nodeVals);
        postSequence(node.right, nodeVals);
        nodeVals.add(node.val);
    }
}
// @lc code=end

