import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/*
 * @lc app=leetcode.cn id=49 lang=java
 *
 * [49] 字母异位词分组
 */

// @lc code=start
class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        // 异位词进行分组。对字符串进行排序
        Map<String, List<String>> map = new HashMap<>();
        for (String str : strs) {
            char[] parts = str.toCharArray();
            Arrays.sort(parts);
            String order = new String(parts);
            List<String> result = map.getOrDefault(order, new LinkedList<>());
            result.add(str);
            map.put(order, result);
        }
        return new ArrayList<>(map.values());
    }
}
// @lc code=end
