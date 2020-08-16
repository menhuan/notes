/*
 * @lc app=leetcode.cn id=58 lang=java
 *
 * [58] 最后一个单词的长度
 */

// @lc code=start
class Solution {
    public int lengthOfLastWord(String s) {
        var result = 0;
        s=s.trim();
        if (s.length() == 0 ){
            return result;
        }
        for(var index =0;index<s.length();index++){
            if (s.charAt(index) ==' '){
                result =0;
            }
            else{
                result++;
            }
        }

        return result;
    }
}
// @lc code=end

