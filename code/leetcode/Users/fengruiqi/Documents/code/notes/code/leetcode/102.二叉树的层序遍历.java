import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import javax.swing.tree.TreeNode;

/*
 * @lc app=leetcode.cn id=102 lang=java
 *
 * [102] 二叉树的层序遍历
 */

// @lc code=start
/**
 * Definition for a binary tree node. public class TreeNode { int val; TreeNode
 * left; TreeNode right; TreeNode() {} TreeNode(int val) { this.val = val; }
 * TreeNode(int val, TreeNode left, TreeNode right) { this.val = val; this.left
 * = left; this.right = right; } }
 */
class Solution {
    public List<List<Integer>> levelOrder(TreeNode root) {
        // 层序遍历
        if (root == null) {
            return new ArrayList<>();
        }
        List<List<Integer>> ans = new ArrayList<List<Integer>>();
        Deque<TreeNode> queue = new LinkedList<>();
        queue.addLast(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> result = new ArrayList();
            for (int index = 0; index < size; index++) {
                TreeNode cur = queue.pollFirst();
                result.add(cur.val);
                if (cur.left != null) {
                    queue.add(cur.left);
                }
                if (cur.right != null) {
                    queue.add(cur.right);
                }
            }
            ans.add(result);
        }
        return ans;

    }
}
// @lc code=end
