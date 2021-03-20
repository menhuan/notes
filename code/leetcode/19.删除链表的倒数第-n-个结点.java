/*
 * @lc app=leetcode.cn id=19 lang=java
 *
 * [19] 删除链表的倒数第N个节点
 */

// @lc code=start
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        // 两个指针 快慢指针 加了一个头指针
        ListNode fast = head,low = new ListNode(0,head) , dump = low ;
        // 用来保存 返回的结果的头指针

        // 让 fast 指针快进n个
        for(int index =0 ; index < n;index++){
            fast = fast.next ;
        }
        while(fast != null ){
            fast = fast.next ;
            low = low.next;

        }
        // 删除要删除的节点
        low.next = low.next.next ;

        ListNode ans = dump.next ;

        return ans ; 

    }
}
// @lc code=end

