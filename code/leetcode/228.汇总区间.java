import java.util.ArrayList;
import java.util.List;

/*
 * @lc app=leetcode.cn id=228 lang=java
 *
 * [228] 汇总区间
 */

// @lc code=start
class Solution {
    public List<String> summaryRanges(int[] nums) {
        List<String> result = new ArrayList<>();
        int i = 0, size = nums.length;
        while (i < size) {
            int low = i;
            i++;
            while (i < size && nums[i] == nums[i - 1] + 1) {
                i++;
            }
            int height = i - 1;
            StringBuilder builder = new StringBuilder();
            builder.append(nums[low]);
            if (height != low) {
                builder.append("->");
                builder.append(nums[height]);
            }
            result.add(builder.toString());
        }
        return result;
    }
}
// @lc code=end
