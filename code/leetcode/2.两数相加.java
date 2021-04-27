/*
 * @lc app=leetcode.cn id=2 lang=java
 *
 * [2] 两数相加
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
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode p1 = l1 , p2 = l2;
        // 只要有一个不为空 那么循环就不终止
        // 需要计算下每一个节点
        ListNode ans = null;
        ListNode result = null;
        int inr = 0 ;
        while(p1 != null || p2 != null || inr > 0){
            int sum = 0 ;
            if(p1 != null ){
                sum += p1.val;
                p1 = p1.next;

            }
            if(p2 != null){
                sum+= p2.val;
                p2= p2.next ;
            }
            if (inr > 0) {
                sum+=inr;
            }
            if (sum >= 10 ){
                inr = 1;
                sum-=10;
            }else{
                inr = 0 ;
            }
            if (ans == null){
                ans = new ListNode(sum);
                result = ans ;
            }else{
                ans.next = new ListNode(sum);
                ans = ans.next;
            }
        }
        return result;

    }
}
// @lc code=end

