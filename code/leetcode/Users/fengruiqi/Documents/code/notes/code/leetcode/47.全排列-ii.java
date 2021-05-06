import java.util.Arrays;
import java.util.List;
import java.util.Set;

/*
 * @lc app=leetcode.cn id=47 lang=java
 *
 * [47] 全排列 II
 */

// @lc code=start
class Solution {
    private List<List<Integer>> ans;

    public List<List<Integer>> permuteUnique(int[] nums) {
        ans = new ArrayList<>();
        // 排序 重复的数字不再放入
        Arrays.sort(nums);
        generate(nums, new LinkedList<>(), new HashSet<>(), new HashSet());
        // 递归从 然后从数组里面选数字
        return ans;
    }

    /**
     * 
     * @param nums   数组集合
     * @param res    结果集合
     * @param indexs 代表已经使用的索引。
     */
    public void generate(int[] nums, LinkedList<Integer> res, Set<Integer> indexs, Set<Integer> values) {
        // 终止条件
        if (res.size() == nums.length) {
            ans.add(new ArrayList<>(res));
            return;
        }
        // 本层处理的逻辑
        for (int index = 0; index < nums.length; index++) {
            if (indexs.contains(index)) {
                continue;
            }
            if (index > 0 && nums[index] == nums[index - 1] && !values.contains(nums[index - 1])) {
                continue;
            }
            values.add(nums[index]);
            res.add(nums[index]);
            // 下一层处理
            generate(nums, res, indexs, values);
            // 进行回溯处理，把填入的数据删除.
            res.removeLast();
            indexs.remove(index);
        }

    }
}
// @lc code=end
