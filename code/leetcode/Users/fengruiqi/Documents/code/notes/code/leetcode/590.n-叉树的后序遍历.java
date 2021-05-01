import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;

/*
 * @lc app=leetcode.cn id=590 lang=java
 *
 * [590] N 叉树的后序遍历
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
    public List<Integer> postorder(Node root) {

    }

    public List<Integer> recursive(Node root) {
        // 后序遍历的方式是 左节点 右节点 根节点
        // 对于N叉树来哦说就是遍历每个节点 之后再遍历根节点
        if (root == null) {
            return new ArrayList<>();
        }
        List<Integer> result = new ArrayList<>();
        helper(root, result);
        return result;
    }

    public void helper(Node root, List<Integer> result) {
        if (root != null) {
            if (root.children.size() > 0) {
                for (int index = 0; index < root.children.size(); index++) {
                    helper(root.children.get(index), result);
                }
            }
            result.add(root.val);
        }
    }
}
// @lc code=end
