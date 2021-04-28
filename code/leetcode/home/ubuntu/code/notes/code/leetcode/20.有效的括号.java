import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/*
 * @lc app=leetcode.cn id=20 lang=java
 *
 * [20] 有效的括号
 */

// @lc code=start
class Solution {
    public boolean isValid(String s) {
        if (s.length() == 0) {
            return true;
        }
        if (s.length() % 2 == 1) {
            return false;
        }
        Map<Character, Character> symbols = new HashMap<>() {
            {
                put('(', ')');
                put('{', '}');
                put('[', ']');

            }
        };
        Deque<Character> deque = new LinkedList<Character>();
        for (int index = 0; index < s.length(); index++) {
            if (symbols.keySet().contains(s.charAt(index))) {
                deque.offerLast(s.charAt(index));
            } else {

                if (symbols.get(deque.peekLast()).equals(s.charAt(index))) {
                    deque.pollLast();
                } else {
                    return false;
                }
            }
        }
        return deque.isEmpty();

    }
}

// @lc code=end
