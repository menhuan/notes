/*
 * @lc app=leetcode.cn id=28 lang=java
 *
 * [28] 实现 strStr()
 */

// @lc code=start
class Solution {
 
    public int strStr(String haystack, String needle) {
        if (needle.length() ==0) return 0;
        int needle_index =needle.length();
        int haystack_index =haystack.length();
        for(int start =0;start < haystack_index-needle_index +1;++start){
            if (haystack.substring(start, start + needle_index).equals(needle)) {
                return start;
              }
        }
           
        return -1;

    }
}
// @lc code=end