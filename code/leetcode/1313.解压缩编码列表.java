import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * @lc app=leetcode.cn id=1313 lang=java
 *
 * [1313] 解压缩编码列表
 */

// @lc code=start
class Solution {
    public int[] decompressRLElist(int[] nums) {
        List<Integer> ans = new ArrayList<>();
        for (int index = 0; index < nums.length; index += 2) {
            for (int j = 0; j < nums[index]; j++) {
                ans.add(nums[index + 1]);
            }
        }
        return ans.stream().mapToInt(Integer::valueOf).toArray();

    }
}
// @lc code=end
