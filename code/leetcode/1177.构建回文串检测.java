import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

/*
 * @lc app=leetcode.cn id=1177 lang=java
 *
 * [1177] 构建回文串检测
 */

// @lc code=start
class Solution {
    public List<Boolean> canMakePaliQueries(String s, int[][] queries) {
        // 什么是回文串 “回文串”是一个正读和反读都一样的字符串，比如“level”或者“noon”等等就是回文串。
        // Stack<Character> stack = new Stack<>();
        List<Boolean> ans = new ArrayList<>(queries.length);
        for (int[] query : queries) {
            Set<Character> partSet = new HashSet<Character>();

            String partString = s.substring(query[0], query[1] + 1);
            int replaseTimes = query[2];
            for (int index = 0; index < partString.length(); index++) {
                Character partChar = partString.charAt(index);
                if (partSet.contains(partChar)) {
                    partSet.remove(partChar);
                } else {
                    partSet.add(partChar);
                }
            }
            if (partSet.size() >= 2) {
                // 在大于等于2 的时候才需要进行替换来变成回文字符串
                if (partSet.size() / 2 <= replaseTimes) {
                    ans.add(true);
                } else {
                    ans.add(false);
                }
            } else {
                ans.add(true);
            }
            // if (stack.empty() ){
            // stack.add(e);
            // }
        }
        return ans;
    }
}
// @lc code=end
