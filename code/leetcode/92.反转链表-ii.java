/*
 * @lc app=leetcode.cn id=92 lang=java
 *
 * [92] 反转链表 II
 */

// @lc code=start
/**
 * Definition for singly-linked list. public class ListNode { int val; ListNode
 * next; ListNode() {} ListNode(int val) { this.val = val; } ListNode(int val,
 * ListNode next) { this.val = val; this.next = next; } }
 */
class Solution {
    public ListNode reverseBetween(ListNode head, int left, int right) {
        // 一次翻转 遍历过程中将链表进行翻转。2放到3后面 3 放到4后面这样操作
        ListNode dumyNode = new ListNode(-1);
        dumyNode.next = head;
        ListNode cur = head, pre = dumyNode;
        int index = 1;
        ListNode next;
        while (cur != null) {
            if (index >= left && index < right) {
                // ListNode tmp = cur;
                next = cur.next;
                cur.next = next.next;
                next.next = pre.next;
                pre.next = next;
            } else if (index < left) {
                // 小于的时候找到前指针
                pre = pre.next;
                cur = cur.next;
            } else {
                // 大于right时候结束循环
                break;
            }
            index++;

        }
        return dumyNode.next;
    }
}
// @lc code=end
