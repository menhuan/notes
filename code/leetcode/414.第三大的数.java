import java.util.Arrays;

/*
 * @lc app=leetcode.cn id=414 lang=java
 *
 * [414] 第三大的数
 */

// @lc code=start
class Solution {
    public int thirdMax(int[] nums) {
        Arrays.sort(nums);
        int index = 0;
        if (nums.length < 3) {
            return nums[nums.length - 1];
        }

        for (int i = nums.length - 2; i >= 0; i--) {
            if (nums[i] != nums[i + 1]) {
                index++;
            }
            if (index == 2) {
                System.out.println(i);
                return nums[i];
            }

        }
        return nums[nums.length - 1];
    }
}
// @lc code=end
