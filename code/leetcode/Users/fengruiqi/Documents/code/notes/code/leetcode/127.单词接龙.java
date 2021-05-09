import java.util.HashSet;
import java.util.Set;
/*
 * @lc app=leetcode.cn id=127 lang=java
 *
 * [127] 单词接龙
 */

// @lc code=start
class Solution {
    private int minSetp = Integer.MAX_VALUE;

    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        // 找到一个最短转换序列
        dfs(new HashSet<>(), beginWord, endWord, 0, wordList);
        return minSetp == Integer.MAX_VALUE ? -1 : minSetp;
    }

    /**
     * 
     * @param use     已经使用过的字符串
     * @param current 当前的字符串是啥
     * @param endWord 结尾字符
     * @param step    当前层
     */
    public void dfs(Set<String> use, String current, String endWord, int step, List<String> wordList) {
        // 递归条件
        System.out.println(step + " " + current + " " + endWord);

        if (current.equals(endWord)) {
            minSetp = Math.min(minSetp, step);
        }

        // 本层处理
        for (String word : wordList) {
            int diff = 0;
            for (int index = 0; index < word.length(); index++) {
                if (current.charAt(index) != word.charAt(index)) {
                    if (++diff > 1)
                        break;
                }
            }
            if (diff == 1 && !use.contains(word)) {
                use.add(word);
                dfs(use, word, endWord, step + 1, wordList);
                use.remove(word);
            }
        }

    }
}
// @lc code=end
