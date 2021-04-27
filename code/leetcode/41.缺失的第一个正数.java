/*
 * @lc app=leetcode.cn id=41 lang=java
 *
 * [41] 缺失的第一个正数
 */

// @lc code=start
class Solution {
    public int firstMissingPositive(int[] nums) {
        // 缺失的第一个数 这里使用的哈希的方式。
        // 因为是找正数，所以我们先把负数修改为大于N的值。
        int size = nums.length;
        for (int index = 0; index < size; index++) {
            if (nums[index] <= 0) {
                nums[index] = size + 1;
            }
        }

        // 将属于【1，N】内的参数标记他们应该属于的位置为负数，他们的值-1就是他们应该在的位置、
        for (int index = 0; index < size; index++) {
            int num = Math.abs(nums[index]);
            if (num <= size) {
                // 数组从0 开始，正数从1开始,得加绝对值，如果出现相同得分数字了；变为负值的就不是负值了。
                nums[num - 1] = -Math.abs(nums[num - 1]);
            }
        }
        for (int index = 0; index < size; index++) {
            if (nums[index] > 0) {
                return index + 1;
            }
        }
        return size + 1;
    }
}
// @lc code=end
