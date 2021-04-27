/*
 * @lc app=leetcode.cn id=14 lang=java
 *
 * [14] 最长公共前缀
 */

// @lc code=start
class Solution {
    public String longestCommonPrefix(String[] strs) {
        // 最长公共前缀 不超过数组中最短的字符。
        // 首先找出来数组中最小的字符长度
        // 然后将最小长度进行二分查找，在字符串中进行匹配
        if (strs.length == 0) {
            return "";
        }
        // 找到最小的字符串长度
        var minLength = Integer.MAX_VALUE;
        for (String str : strs) {
            minLength = Math.min(minLength, str.length());
        }
        // 根据最小长度进行二分查找
        int low = 0, hight = minLength;
        while (low < hight) {
            // 求中位数，防止hight+low 特别大,导致内存溢出，使用hight -low /2
            // 比如hight =10 low =5 hight+low /2 =7 10-5/2 + 5 ==7是一样的。
            var middle = (hight - low + 1) / 2 + low;
            // 一个个字符进行比较，确定用来改变下一次二分查找的位置
            if (compareStrs(strs, middle)) {
                low = middle;
            } else {
                hight = middle-1 ;
            }

        }
        return strs[0].substring(0,low);
        
    }

    private boolean compareStrs(String[] strs, int middle) {
        var count = strs.length;
        var str0 = strs[0].substring(0, middle);
        for (int index = 1; index < count; index++) {
            var str1 = strs[index];
            if (str0.equals(str1.substring(0, middle))) {
                continue;
            } else {
                return Boolean.FALSE;
            }

        }
        return Boolean.TRUE;
    }
}
// @lc code=end
