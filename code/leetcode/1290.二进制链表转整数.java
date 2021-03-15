import java.util.List;

/*
 * @lc app=leetcode.cn id=1290 lang=java
 *
 * [1290] 二进制链表转整数
 */

// @lc code=start
/**
 * Definition for singly-linked list. public class ListNode { int val; ListNode
 * next; ListNode(int x) { val = x; } }
 */
class Solution {
    public int getDecimalValue(ListNode head) {
        List<Integer> collects = new ArrayList<>();
        while (head != null) {
            collects.add(head.val);
            head = head.next;
        }
        int sum = 0;
        for (int index = collects.size() - 1; index >= 0; index--) {
            sum += collects.get(index) * Math.pow(2, collects.size() - 1 - index);

        }
        return sum;
    }
}
// @lc code=end
