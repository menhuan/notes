/*
 * @lc app=leetcode.cn id=268 lang=java
 *
 * [268] 丢失的数字
 */

// @lc code=start
class Solution {
    public int missingNumber(int[] nums) {
        // 数学运算 或者是高斯运算。 丢失一个数字可以这么做，但是丢失多个的话可能就不能这么做了
        int sum = nums.length * (nums.length + 1) / 2;
        int export = 0;
        for (int num : nums) {
            export += num;
        }
        return sum - export;
    }
}
// @lc code=end
