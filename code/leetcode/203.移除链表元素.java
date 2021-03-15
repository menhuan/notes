/*
 * @lc app=leetcode.cn id=203 lang=java
 *
 * [203] 移除链表元素
 */

// @lc code=start
/**
 * Definition for singly-linked list. public class ListNode { int val; ListNode
 * next; ListNode() {} ListNode(int val) { this.val = val; } ListNode(int val,
 * ListNode next) { this.val = val; this.next = next; } }
 */
class Solution {
    public ListNode removeElements(ListNode head, int val) {
        // 增加一个哨兵节点
        ListNode sentinel = new ListNode(0);
        sentinel.next = head;
        ListNode pre = sentinel, curr = head;
        while (curr != null) {
            if (curr.val == val) {
                // 当两个不相等的情况下 pre与curr的代表的节点是不一样的
                pre.next = curr.next;
            } else {
                // 不删除，改变下前节点
                pre = curr;
            }
            // 更改下当前节点
            curr = curr.next;

        }
        return sentinel.next;
    }
}
// @lc code=end
