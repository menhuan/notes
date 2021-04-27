import java.util.LinkedList;
import java.util.List;

/*
 * @lc app=leetcode.cn id=442 lang=java
 *
 * [442] 数组中重复的数据
 */

// @lc code=start
class Solution {
    public List<Integer> findDuplicates(int[] nums) {
        // 标记法，因为里面的数值都小于n 所以可以让其每次遍历一个数都可以在数组中找到值。
        List<Integer> res = new LinkedList();
        for (int num : nums) {
            int absNum = Math.abs(num);
            if (nums[absNum - 1] < 0) {
                res.add(absNum);
            } else {
                nums[absNum - 1] *= -1;
            }

        }
        return res;
    }
}
// @lc code=end
