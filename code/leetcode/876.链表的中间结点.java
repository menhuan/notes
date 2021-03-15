/*
 * @lc app=leetcode.cn id=876 lang=java
 *
 * [876] 链表的中间结点
 */

// @lc code=start
/**
 * Definition for singly-linked list. public class ListNode { int val; ListNode
 * next; ListNode(int x) { val = x; } }
 */
class Solution {
    public ListNode middleNode(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;
        // 快慢指针 龟兔赛跑的方式。
        while (fast.next != null) {

            if (fast.next != null && fast.next.next == null) {
                slow = slow.next;
                break;

            }
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow;
    }
}
// @lc code=end
