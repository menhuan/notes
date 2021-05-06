
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/*
 * @lc app=leetcode.cn id=46 lang=java
 *
 * [46] 全排列
 */

// @lc code=start
class Solution {
    private List<List<Integer>> ans;

    public List<List<Integer>> permute(int[] nums) {
        ans = new ArrayList<>();
        generate(nums, new LinkedList<>(), new HashSet<>());
        // 递归从 然后从数组里面选数字
        return ans;
    }

    /**
     * 
     * @param nums   数组集合
     * @param res    结果集合
     * @param indexs 代表已经使用的索引。
     */
    public void generate(int[] nums, LinkedList<Integer> res, Set<Integer> indexs) {
        // 终止条件
        if (res.size() == nums.length) {
            ans.add(new ArrayList<>(res));
            return;
        }
        // 本层处理的逻辑
        for (int index = 0; index < nums.length; index++) {
            // 添加成功，代表这个数字还没有在这个集合里面使用
            if (indexs.add(index)) {
                res.add(nums[index]);
                // 下一层处理
                generate(nums, res, indexs);
                // 进行回溯处理，把填入的数据删除.
                res.removeLast();
                indexs.remove(index);
            }
        }

    }
}
// @lc code=end
