import java.util.HashSet;
import java.util.Set;

/*
 * @lc app=leetcode.cn id=3 lang=java
 *
 * [3] 无重复字符的最长子串
 */

// @lc code=start
class Solution {
    public int lengthOfLongestSubstring(String s) {
        // 第一步 将字符串中的字节进行拆分 拆分成一个个的字符串与其组合，
        // 在组合的放入到map 中key是字符的长度
        int max_len = 0; 
        Set<String> end_str = new HashSet();
        for(int index=0 ;index < s.length();){
            String content = s.substring(index, index+max_len+1);
            if (end_str.contains(content));
                
            
            
        }
    }
}
// @lc code=end

