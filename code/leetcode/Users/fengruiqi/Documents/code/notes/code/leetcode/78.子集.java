import java.awt.List;
import java.util.ArrayList;
import java.util.List;

/*
 * @lc app=leetcode.cn id=78 lang=java
 *
 * [78] 子集
 */

// @lc code=start
class Solution {
    private List<List<Integer>> ans;

    public List<List<Integer>> subsets(int[] nums) {
        ans = new ArrayList();
        generate(nums, 0, new ArrayList());
        return ans;
    }

    public void generate(int[] nums, int cur, List<Integer> part) {
        // 终止条件 遍历数组到结尾，而不是看列表里面满没满
        if (cur == nums.length) {
            ans.add(new ArrayList(part));
            return;
        }
        // 本层不选
        generate(nums, cur + 1, part);

        // 本层的数据选
        part.add(nums[cur]);
        generate(nums, cur + 1, part);
        // 数据移出来
        part.remove(part.size() - 1);

    }
}
// @lc code=end
