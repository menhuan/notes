import java.util.Arrays;
import javax.swing.tree.TreeNode;


/*
 * @lc app=leetcode.cn id=105 lang=java
 *
 * [105] 从前序与中序遍历序列构造二叉树
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
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        // 前序遍历 是指 根节点 左节点 右节点
        // 中序遍历 是指 左节点 根节点 右节点
        if (preorder.length == 0){
            return null;
        }
        
        if (preorder.length == 1){
            return new TreeNode(preorder[0]);
        }
        
        // 下面是大于长度大于2  
        int root = preorder[0];
        TreeNode node = new TreeNode(root);
        
        // 设置默认值？,貌似会有问题
        Integer left = null;
        for(int index=0;index<inorder.length;index++){
            if (inorder[index] == root ){
                left = index;
                break;
            }
        }
        // copy的时候 最右边的数据不拷贝
        if  (left != null){
            node.left = buildTree(Arrays.copyOfRange(preorder, 1, left+1), Arrays.copyOfRange(inorder, 0, left));
            node.right = buildTree(Arrays.copyOfRange(preorder, left+1, preorder.length), Arrays.copyOfRange(inorder, left+1, inorder.length));
        }
       
        return node;

    }

}
// @lc code=end

