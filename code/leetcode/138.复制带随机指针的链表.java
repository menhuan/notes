import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Node;

/*
 * @lc app=leetcode.cn id=138 lang=java
 *
 * [138] 复制带随机指针的链表
 */

// @lc code=start
/*
// Definition for a Node.
class Node {
    int val;
    Node next;
    Node random;

    public Node(int val) {
        this.val = val;
        this.next = null;
        this.random = null;
    }
}
*/

class Solution {
    Map<Node, Node> nodes = new HashMap();

    public Node copyRandomList(Node head) {
        // 复制的意思 其实就是将其新建立一个node，这里利用回溯算法，减少了迭代的次数
        if (head == null)
            return null;
        if (this.nodes.containsKey(head)) {
            return nodes.get(head);
        }
        // 如果没有被拷贝的话
        Node node = new Node(head.val);
        nodes.put(head, node);

        node.next = copyRandomList(head.next);
        node.random = copyRandomList(head.random);
        return node;
    }
}
// @lc code=end
