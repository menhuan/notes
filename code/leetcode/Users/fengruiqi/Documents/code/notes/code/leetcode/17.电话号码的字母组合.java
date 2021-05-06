import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * @lc app=leetcode.cn id=17 lang=java
 *
 * [17] 电话号码的字母组合
 */

// @lc code=start
class Solution {
    private List<String> ans;

    public List<String> letterCombinations(String digits) {
        ans = new ArrayList();

        if (digits == "" || digits == null || digits.length() == 0) {
            return ans;
        }
        Map<Character, String> map = new HashMap<>();
        map.put('2', "abc");
        map.put('3', "def");
        map.put('4', "ghi");
        map.put('5', "jkl");
        map.put('6', "mno");
        map.put('7', "pqrs");
        map.put('8', "tuv");
        map.put('9', "wxyz");
        dfs(digits, 0, "", map);
        return ans;
    }

    public void dfs(String digits, int index, String part, Map<Character, String> map) {
        // 终止条件
        if (index == digits.length()) {
            ans.add(part);
            return;
        }

        // 本层处理 是选择左字 还是右字符
        String content = map.get(digits.charAt(index));
        for (int indexContent = 0; indexContent < content.length(); indexContent++) {
            // 下一层处理
            dfs(digits, index + 1, part + content.charAt(indexContent), map);
        }

    }
}
// @lc code=end
