import java.util.HashMap;
import java.util.Map;

/*
 * @lc app=leetcode.cn id=387 lang=java
 *
 * [387] 字符串中的第一个唯一字符
 */

// @lc code=start
class Solution {
    public int firstUniqChar(String s) {
        Map<Character, Integer> conMap = new HashMap<Character, Integer>();
        for (int index = 0; index < s.length(); index++) {
            conMap.put(s.charAt(index), conMap.getOrDefault(s.charAt(index), 0) + 1);
        }

        for (int index = 0; index < s.length(); index++) {
            if (conMap.get(s.charAt(index)) == 1) {
                return index;
            }
        }
        return -1;
    }
}
// @lc code=end
