import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * @lc app=leetcode.cn id=1481 lang=java
 *
 * [1481] 不同整数的最少数目
 */

// @lc code=start
class Solution {
    public int findLeastNumOfUniqueInts(int[] arr, int k) {
        Map<Integer, Integer> group = new HashMap<>();
        for (int num : arr) {
            group.put(num, group.getOrDefault(num, 0) + 1);
        }
        List<int[]> frq = new ArrayList<int[]>();
        for (Map.Entry<Integer, Integer> entry : group.entrySet()) {
            int[] value = { entry.getKey(), entry.getValue() };
            frq.add(value);
        }
        Collections.sort(frq, (v1, v2) -> {
            return v1[1] - v2[1];
        });
        int ans = frq.size();
        for (int index = 0; index < frq.size(); index++) {
            int size = frq.get(index)[1];
            if (k >= size) {
                k -= size;
                --ans;
            } else {
                break;
            }
        }
        return ans;
    }
}
// @lc code=end
