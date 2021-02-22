import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/*
 * @lc app=leetcode.cn id=870 lang=java
 *
 * [870] 优势洗牌
 */

// @lc code=start
class Solution {
    public int[] advantageCount(int[] A, int[] B) {
        // 第一步对两个数字进行排序。
        int[] sortA = A.clone();
        Arrays.sort(sortA);
        int[] sortB = B.clone();
        Arrays.sort(sortB);

        Map<Integer, Deque<Integer>> assigned = new HashMap<>();
        for (int b : B)
            assigned.put(b, new LinkedList());
        // 未分配的数组B
        Deque<Integer> remind = new LinkedList<>();
        // 数组B从小开始遍历
        int j = 0;
        for (int a : sortA) {
            if (a > sortB[j]) {
                assigned.get(sortB[j++]).add(a);
            } else {
                // 不大于最小值，那么放入到未分配中
                remind.add(a);
            }
        }

        // 从上面分配好的方法中取出答案。
        int[] ans = new int[A.length];
        for (int index = 0; index < B.length; index++) {
            if (assigned.get(B[index]).size() > 0) {
                ans[index] = assigned.get(B[index]).pop();
            } else {
                ans[index] = remind.pop();
            }
        }
        return ans;
    }
}
// @lc code=end
