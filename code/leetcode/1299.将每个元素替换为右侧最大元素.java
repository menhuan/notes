import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;
/*
 * @lc app=leetcode.cn id=1299 lang=java
 *
 * [1299] 将每个元素替换为右侧最大元素
 */

// @lc code=start
class Solution {
    public int[] replaceElements(int[] arr) {
        // 找到最大的元素
        int[] ans = new int[arr.length];
        Deque<Integer> stack = new LinkedList<Integer>();

        for (int index = 0; index < arr.length; index++) {
            if (stack.isEmpty()) {
                stack.add(arr[index]);
            } else {
                while (true && !stack.isEmpty()) {
                    var num = stack.peekLast();
                    if (num < arr[index]) {
                        num = stack.pollLast();

                    } else {
                        break;
                    }
                }

                stack.add(arr[index]);
            }
        }
        System.out.println(stack);
        for (int index = 0; index < arr.length; index++) {
            if (!stack.isEmpty()) {
                if (arr[index] < stack.peekFirst()) {
                    ans[index] = stack.peekFirst();
                } else if (arr[index] == stack.peekFirst()) {
                    stack.pollFirst();
                    if (!stack.isEmpty()) {
                        ans[index] = stack.peekFirst();
                    } else {
                        ans[index] = -1;
                    }
                } else {
                    ans[index] = stack.pollFirst();
                }

            } else {
                ans[index] = -1;
            }

        }
        return ans;
    }
}
// @lc code=end
