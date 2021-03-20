/*
 * @lc app=leetcode.cn id=24 lang=java
 *
 * [24] 两两交换链表中的节点
 */

// @lc code=start
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode swapPairs(ListNode head) {
        //创建一个哑铃节点
        ListNode deampNode = new ListNode(0);
        deampNode.next = head;
        ListNode tmp = deampNode;
        // tmp 0  1 2 3 4 
        while(tmp.next !=null && tmp.next.next != null){
            // node1 1 2 3 4 
            ListNode node1 = tmp.next;
            //  2 3 4 
            ListNode node2 = tmp.next.next ;
            // 0 2 3 4 
            tmp.next = node2;
            // node1 1 3 4
            node1.next = node2.next ;
            // node2 2 1 3 4 
            node2.next = node1;
            // tmp 1 3 4  node1 已经是node2的子节点
            tmp = node1;
        }        
        return deampNode.next ;

    }
}
// @lc code=end

