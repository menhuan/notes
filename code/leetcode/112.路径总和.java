import java.util.LinkedList;
import java.util.Queue;

import javax.swing.tree.TreeNode;


/*
 * @lc app=leetcode.cn id=112 lang=java
 *
 * [112] 路径总和
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
    public boolean hasPathSum(TreeNode root, int sum) {
        if (root == null){
            return false;
        }
        Queue<TreeNode> nodes = new LinkedList<TreeNode>();
        Queue<Integer> paths = new LinkedList<Integer>();   
        nodes.offer(root);
        paths.offer(root.val);
        while(!nodes.isEmpty()){
            TreeNode node = nodes.poll();
            var pathValue = paths.poll();
            if (node.left == null && node.right == null){
                if (pathValue == sum){
                    return true;
                } 
                continue;
            }
            if (node.left != null){
                nodes.offer(node.left);
                paths.offer(node.left.val + pathValue);
            }
            if (node.right !=null){
                nodes.offer(node.right);
                nodes.offer(node.right.val + pathValue);
            }

        }
    }
}
// @lc code=end

