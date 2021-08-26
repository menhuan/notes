//编写代码，移除未排序链表中的重复节点。保留最开始出现的节点。 
//
// 示例1: 
//
// 
// 输入：[1, 2, 3, 3, 2, 1]
// 输出：[1, 2, 3]
// 
//
// 示例2: 
//
// 
// 输入：[1, 1, 1, 1, 2]
// 输出：[1, 2]
// 
//
// 提示： 
//
// 
// 链表长度在[0, 20000]范围内。 
// 链表元素在[0, 20000]范围内。 
// 
//
// 进阶： 
//
// 如果不得使用临时缓冲区，该怎么解决？ 
// Related Topics 哈希表 链表 双指针 
// 👍 116 👎 0

package leetcode.editor.cn;

import java.util.HashSet;
import java.util.Set;

public class RemoveDuplicateNodeLcci {
    public static void main(String[] args) {
        Solution solution = new RemoveDuplicateNodeLcci().new Solution();
        solution.removeDuplicateNodes(null);
    }
    //leetcode submit region begin(Prohibit modification and deletion)

    /**
     * Definition for singly-linked list.
     * public class ListNode {
     * int val;
     * ListNode next;
     * ListNode(int x) { val = x; }
     * }
     */
    class Solution {
        public class ListNode {
            int val;
            ListNode next;

            ListNode(int x) {
                this.val = x;
            }
        }

        public ListNode removeDuplicateNodes(ListNode head) {
            // 借助set来实现
            if (head == null) {
                return null;
            }
            Set<Integer> con = new HashSet<Integer>();
            con.add(head.val);
            ListNode cur = head;
            while (cur.next != null) {
                int val = cur.next.val;
                if (con.add(val)) {
                    cur = cur.next;
                } else {
                    // 没有修改当前的cur 而是修改的next 这样就不会出现cur 是空的判断
                    cur.next = cur.next.next;
                }
            }
            cur.next = null;
            return head;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}

