import java.util.Map;
import java.util.Set;
/*
 * @lc app=leetcode.cn id=167 lang=java
 *
 * [167] 两数之和 II - 输入有序数组
 */

// @lc code=start
class Solution {
    public int[] twoSum(int[] numbers, int target) {
        Map<Integer, Integer> numbersMap = new HashMap();
        for (int index = 0; index < numbers.length; index++) {
            numbersMap.put(numbers[index], index + 1);
        }
        for (int index = 0; index < numbers.length; index++) {
            int diff = target - numbers[index];
            if (numbersMap.containsKey(diff)) {
                return new int[] { index + 1, numbersMap.get(diff) };
            }
        }
        return null;
    }
}
// @lc code=end
