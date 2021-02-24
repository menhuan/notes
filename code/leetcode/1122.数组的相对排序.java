import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/*
 * @lc app=leetcode.cn id=1122 lang=java
 *
 * [1122] 数组的相对排序
 */

// @lc code=start
class Solution {
    public int[] relativeSortArray(int[] arr1, int[] arr2) {
        Map<Integer, Integer> arrs = new HashMap<>();
        for (int index = 0; index < arr2.length; index++) {
            arrs.put(arr2[index], index);
        }
        Integer[] arr1Integers = Arrays.stream(arr1).boxed().toArray(Integer[]::new);

        Arrays.sort(arr1Integers, (Integer x, Integer y) -> {
            if (arrs.containsKey(x) || arrs.containsKey(y))
                return arrs.getOrDefault(x, 1001) - arrs.getOrDefault(y, 1001);
            return x - y;
        });

        return Arrays.stream(arr1Integers).mapToInt(Integer::valueOf).toArray();
    }
}
// @lc code=end
