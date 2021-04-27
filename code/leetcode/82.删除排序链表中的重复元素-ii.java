/*
 * @lc app=leetcode.cn id=82 lang=java
 *
 * [82] 删除排序链表中的重复元素 II
 */

// @lc code=start
/**
 * Definition for singly-linked list. public class ListNode { int val; ListNode
 * next; ListNode() {} ListNode(int val) { this.val = val; } ListNode(int val,
 * ListNode next) { this.val = val; this.next = next; } }
 */
class Solution {
    public ListNode deleteDuplicates(ListNode head) {
        // 创建一个哑结点
        ListNode cur = new ListNode(0);
        cur.next = head;
        Integer repate = null;
        ListNode node = cur;

        while (node.next != null && node.next.next != null) {
            if (node.next.val == node.next.next.val) {
                repate = node.next.val;
            }
            int times = 0;
            while (repate != null && node.next != null && node.next.val == repate) {
                node.next = node.next.next;
                times++;
            }
            if (times == 0) {
                node = node.next;

            }
        }
        return cur.next;

    }
}
// @lc code=end
