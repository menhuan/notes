/*
 * @lc app=leetcode.cn id=709 lang=java
 *
 * [709] 转换成小写字母
 */

// @lc code=start
class Solution {
    public String toLowerCase(String s) {
        char[] array = s.toCharArray();
        for (int index = 0; index < array.length; index++) {
            if (array[index] >= 'A' && array[index] <= 'Z') {
                // 大小转小写 + 32 小写转大写 -32
                array[index] += 32;
            }
        }
        return new String(array);

    }
}
// @lc code=end
