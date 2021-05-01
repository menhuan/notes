import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/*
 * @lc app=leetcode.cn id=429 lang=java
 *
 * [429] N 叉树的层序遍历
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
    public List<List<Integer>> levelOrder(Node root) {
        // 层序遍历 使用迭代的方式完成。放入到队列中。
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        if (root == null) {
            return result;
        }
        Deque<Node> queue = new LinkedList<Node>();
        queue.addLast(root);
        while (!queue.isEmpty()) {
            List<Integer> partResult = new ArrayList<Integer>();
            int currentSize = queue.size();
            int currentIndex = 1;
            while (currentIndex <= currentSize) {
                Node current = queue.pollFirst();
                List<Node> children = current.children;
                // 这个可以用addall来实现
                if (children.size() > 0) {
                    for (int index = 0; index < children.size(); index++) {
                        queue.addLast(children.get(index));
                    }
                }
                currentIndex++;
                partResult.add(current.val);
            }
            result.add(partResult);
        }
        return result;

    }
}
// @lc code=end
