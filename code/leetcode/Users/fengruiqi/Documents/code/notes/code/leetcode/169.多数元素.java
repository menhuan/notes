/*
 * @lc app=leetcode.cn id=169 lang=java
 *
 * [169] 多数元素
 */

// @lc code=start
class Solution {
    public int majorityElement(int[] nums) {
        return majorityElementRec(nums, 0, nums.length - 1);

    }

    public int countInRange(int[] nums, int cruValue, int start, int end) {
        int count = 0;
        for (int index = start; index <= end; index++) {
            if (nums[index] == cruValue) {
                count++;
            }
        }
        return count++;
    }

    public int majorityElementRec(int[] nums, int cur, int length) {
        // 终止条件
        if (cur == length) {
            // 代表的是当前数为本次递归中的众数
            return nums[cur];
        }

        // 本层次处理，因为需要下一次的处理 这种取中间值的方式防止相加超过int的限制
        int mid = (length - cur) / 2 + cur;
        int left = majorityElementRec(nums, cur, mid);
        int right = majorityElementRec(nums, mid + 1, length);

        // 如果上面两个都相等那么就不需要计算众数了
        if (left == right) {
            return left;
        }
        // 如果上面两个不相等 那么就需要计算下这两次循环中 哪两个元素是众数
        int leftCount = countInRange(nums, left, cur, length);
        int rightCount = countInRange(nums, right, cur, length);
        return leftCount > rightCount ? left : right;

    }
}
// @lc code=end
