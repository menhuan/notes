import java.util.HashMap;
import java.util.Map;

/*
 * @lc app=leetcode.cn id=242 lang=java
 *
 * [242] 有效的字母异位词
 */

// @lc code=start
class Solution {
    public boolean isAnagram(String s, String t) {
        // 异位词 的意思是字母顺序不同，但是字母出现的次数是相同的
        // 过滤下长度的数据
        if (s.length() != t.length()) {
            return false;
        }
        Map<Character, Integer> counts = new HashMap<>();
        for (int index = 0; index < s.length(); index++) {
            counts.put(s.charAt(index), counts.getOrDefault(s.charAt(index), 0) + 1);
        }
        for (int index = 0; index < t.length(); index++) {
            counts.put(t.charAt(index), counts.getOrDefault(t.charAt(index), 0) - 1);
            if (counts.get(t.charAt(index)) == 0) {
                counts.remove(t.charAt(index));
                continue;
            }
            if (counts.get(t.charAt(index)) < 0) {
                return false;
            }
        }
        return counts.isEmpty();
    }
}
// @lc code=end
