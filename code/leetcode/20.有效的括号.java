import java.util.HashMap;
import java.util.Stack;

/*
 * @lc app=leetcode.cn id=20 lang=java
 *
 * [20] 有效的括号
 */

// @lc code=start
class Solution {
    public boolean isValid(String s) {
        // 把字符串的内容取出放入到栈中，然后取出来做对比
        if ( s == null ||s.length() == 0){
            return Boolean.TRUE;
        }
        Stack<String> stack = new Stack<String>();

        stack.push(String.valueOf(s.charAt(0)));
        var chartResult = new HashMap<String, String>() {
            {
                put("(", ")");
                put("{", "}");
                put("[", "]");
            }
        };
        for (int index = 1; index < s.length(); index++) {
            var content = String.valueOf(s.charAt(index));
            
            if (chartResult.containsKey(content)){
               stack.push(content);
               continue;
            }else{
                if (stack.isEmpty()){
                    return Boolean.FALSE;
                }
                var popChart = stack.pop();
                if (content.equals(chartResult.get(popChart))) {
                    continue;
                }else{
                    return Boolean.FALSE;
                }
            }
        }
        return stack.isEmpty();
    }
}
// @lc code=end
