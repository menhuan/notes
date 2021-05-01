/*
 * @lc app=leetcode.cn id=589 lang=java
 *
 * [589] N 叉树的前序遍历
 */

// @lc code=start
/*
// Definition for a Node.
class Node {
    public int val;
    public List<Node> children;

    public Node() {}

    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, List<Node> _children) {
        val = _val;
        children = _children;
    }
};
*/

class Solution {
    public List<Integer> preorder(Node root) {
        return recursive(root);

    }

    public List<Integer> recursive(Node root) {
        // 前序遍历的方式 跟节点 左节点 右节点
        // 对于N叉树来说就是先遍历根节点，再遍历每个节点
        if (root == null) {
            return new ArrayList<>();
        }
        List<Integer> result = new ArrayList<>();
        helper(root, result);
        return result;
    }

    public void helper(Node root, List<Integer> result) {
        if (root != null) {
            result.add(root.val);
            if (root.children.size() > 0) {
                for (int index = 0; index < root.children.size(); index++) {
                    helper(root.children.get(index), result);
                }
            }

        }
    }
}
// @lc code=end
