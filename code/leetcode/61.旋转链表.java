/*
 * @lc app=leetcode.cn id=61 lang=java
 *
 * [61] 旋转链表
 */

// @lc code=start
/**
 * Definition for singly-linked list. public class ListNode { int val; ListNode
 * next; ListNode() {} ListNode(int val) { this.val = val; } ListNode(int val,
 * ListNode next) { this.val = val; this.next = next; } }
 */
class Solution {
    public ListNode rotateRight(ListNode head, int k) {
        // bad case
        if (head == null)
            return null;
        if (head.next == null)
            return head;
        int length = 0;
        Deque<ListNode> stack = new LinkedList<ListNode>();
        ListNode cur = head;
        while (cur != null) {
            length++;
            stack.add(cur);
            if (cur.next == null) {
                cur.next = head;
                break;
            }
            cur = cur.next;

        }
        ListNode end = null;
        int times = k % length;
        while (times > 0) {
            end = stack.pollLast();
            stack.offerFirst(end);
            times--;
        }
        end = stack.pop();
        cur = end;
        while (length > 0) {
            length--;
            if (length == 0) {
                cur.next = null;
                break;
            }
            cur = cur.next;
        }
        return end;

    }
}
// @lc code=end
