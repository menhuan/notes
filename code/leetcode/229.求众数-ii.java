import java.util.List;

/*
 * @lc app=leetcode.cn id=229 lang=java
 *
 * [229] 求众数 II
 */

// @lc code=start
class Solution {
    public List<Integer> majorityElement(int[] nums) {
        List<Integer> result = new ArrayList<Integer>();
        if (nums == null || nums.length == 0)
            return result;
        // 摩尔投票法
        // 定义两个候选人 以及对应的计数。
        int candidate = nums[0], candidateCount = 0;
        int candidate1 = nums[0], candidateCount1 = 0;
        for (int num : nums) {
            if (candidate == num) {
                candidateCount++;
                continue;
            }
            if (candidate1 == num) {
                candidateCount1++;
                continue;
            }
            if (candidateCount == 0) {
                candidate = num;
                candidateCount++;
                continue;
            }

            if (candidateCount1 == 0) {
                candidate1 = num;
                candidateCount1++;
                continue;
            }
            candidateCount--;
            candidateCount1--;
        }
        int count0 = 0;
        int count1 = 0;
        for (int num : nums) {
            if (num == candidate)
                count0++;
            else if (num == candidate1)
                count1++;
        }
        if (count0 > nums.length / 3)
            result.add(candidate);
        if (count1 > nums.length / 3)
            result.add(candidate1);
        return result;

    }
}
// @lc code=end
