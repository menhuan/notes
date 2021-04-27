/*
 * @lc app=leetcode.cn id=143 lang=java
 *
 * [143] 重排链表
 */

// @lc code=start
/**
 * Definition for singly-linked list. public class ListNode { int val; ListNode
 * next; ListNode() {} ListNode(int val) { this.val = val; } ListNode(int val,
 * ListNode next) { this.val = val; this.next = next; } }
 */
class Solution {
    public void reorderList(ListNode head) {
        // 将链表翻转
        ListNode revert = null;
        ListNode cur = head;
        int length = 0;
        while (cur != null) {
            ListNode tmp = cur.next;

            cur.next = revert;
            revert = cur;
            cur = tmp;
            length++;
        }

        ListNode pre = head, last = revert;

        int mid;
        if (length % 2 > 1) {
            mid = length / 2 + 1;
        } else {
            mid = length / 2;
        }
        int index = 1;
        ListNode end = new ListNode(0);
        while (index <= mid) {
            end.next = pre;
            end.next.next = last;
            System.out.println("pre:" + pre.val);
            System.out.println("last:" + last.val);
            pre = pre.next;
            last = last.next;

            // System.out.println(end.next.val);
            // System.out.println(end.next.next.val);

            index++;
        }
        head = end.next;
    }
}
// @lc code=end
