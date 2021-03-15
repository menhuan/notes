import java.util.ArrayList;
import java.util.List;

/*
 * @lc app=leetcode.cn id=234 lang=java
 *
 * [234] 回文链表
 */

// @lc code=start
/**
 * Definition for singly-linked list. public class ListNode { int val; ListNode
 * next; ListNode() {} ListNode(int val) { this.val = val; } ListNode(int val,
 * ListNode next) { this.val = val; this.next = next; } }
 */
class Solution {
    public boolean isPalindrome(ListNode head) {
        // 首先明白回文的一个观点是 回文的意思是前后是一致的。
        // 简单的方式是通过转换为回文字符串的方式来做。
        List<Integer> heads = new ArrayList<>();
        ListNode copyHead = head;
        while (copyHead != null) {
            heads.add(copyHead.val);
            copyHead = copyHead.next;
        }
        int start = 0;
        int end = heads.size() - 1;
        while (start <= end) {
            if (!heads.get(start).equals(heads.get(end))) {
                return false;
            }
            start++;
            end--;
        }
        return true;

    }
}
// @lc code=end
