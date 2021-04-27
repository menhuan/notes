/*
 * @lc app=leetcode.cn id=129 lang=java
 *
 * [129] 求根到叶子节点数字之和
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
    public int sumNumbers(TreeNode root) {
       return sumRes(root,0);
       
    }
    public int sumRes(TreeNode root,int res){
        if (root ==null) return 0;
        res = res*10 + root.val;
        if(root.left == null && root.right ==null){
            return res;
        }
        return sumRes(root.left,res) +sumRes(root.right, res);

    }
}
// @lc code=end

