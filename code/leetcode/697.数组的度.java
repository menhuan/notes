import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/*
 * @lc app=leetcode.cn id=697 lang=java
 *
 * [697] 数组的度
 */

// @lc code=start
class Solution {
    public int findShortestSubArray(int[] nums) {
        Map<Integer, Integer> left = new HashMap(), right = new HashMap<>(), count = new HashMap<>();

        for (int index = 0; index < nums.length; index++) {
            int num = nums[index];
            if (left.get(num) == null)
                left.put(num, index);
            right.put(num, index);
            count.put(num, count.getOrDefault(num, 0) + 1);
        }
        int degree = Collections.max(count.values());
        int ans = nums.length;
        for (int num : count.keySet()) {
            if (count.get(num) == degree) {
                ans = Math.min(ans, right.get(num) - left.get(num) + 1);
            }
        }
        return ans;
    }
}
// @lc code=end
