import java.util.HashMap;
import java.util.Map;

/*
 * @lc app=leetcode.cn id=1160 lang=java
 *
 * [1160] 拼写单词
 */

// @lc code=start
class Solution {
    public int countCharacters(String[] words, String chars) {
        // 统计chars中的
        Map<Character, Integer> chatsMap = new HashMap<>();
        for (int index = 0; index < chars.length(); index++) {
            chatsMap.put(chars.charAt(index), chatsMap.getOrDefault(chars.charAt(index), 0) + 1);
        }
        int ans = 0;
        for (String word : words) {
            Map<Character, Integer> wordMap = new HashMap<>();
            for (int index = 0; index < word.length(); index++) {
                wordMap.put(word.charAt(index), wordMap.getOrDefault(word.charAt(index), 0) + 1);
            }
            boolean isAns = true;
            for (int index = 0; index < word.length(); index++) {
                char value = word.charAt(index);
                if (chatsMap.getOrDefault(value, 0) < wordMap.get(value)) {
                    isAns = false;
                    break;
                }
            }
            if (isAns) {
                ans += word.length();
            }

        }

        return ans;
    }
}
// @lc code=end
