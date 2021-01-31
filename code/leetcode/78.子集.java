import java.util.ArrayList;
import java.util.List;

/*
 * @lc app=leetcode.cn id=78 lang=java
 *
 * [78] 子集
 */

// @lc code=start
class Solution {
    List<Integer> ans = new ArrayList<Integer>();
    List<List<Integer>> result = new ArrayList<>();

    public List<List<Integer>> subsets(int[] nums) {
        //
        dfs(0, nums);
        return result;

    }

    public void dfs(int currnt, int[] nums) {
        if (currnt == nums.length) {
            result.add(new ArrayList<>(ans));
            return;
        }
        // 带当前数字
        ans.add(nums[currnt]);
        dfs(currnt + 1, nums);
        // 不带当前数字
        ans.remove(ans.size() - 1);
        dfs(currnt + 1, nums);
    }
}
// @lc code=end
