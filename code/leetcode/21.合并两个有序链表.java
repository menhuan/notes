/*
 * @lc app=leetcode.cn id=21 lang=java
 *
 * [21] 合并两个有序链表
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
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode result1=new ListNode(-1);
        ListNode result =result1;
        
        while(l1!=null && l2 !=null){
            if (l1.val <=l2.val){
                result.next=l1;
                l1=l1.next;
              
            }else{
                result.next=l2;
                l2=l2.next;
            }
            result = result.next;
        }    
        result.next = l1 ==null ? l2:l1 ;

        return result1.next;
        
    }
}
// @lc code=end

