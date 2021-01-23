import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * @lc app=leetcode.cn id=40 lang=java
 *
 * [40] 组合总和 II
 */

// @lc code=start
class Solution {
    // 存储结果
    List<int[]> freq = new ArrayList<int[]>();
    List<Integer> combine = new ArrayList<Integer>();
    List<List<Integer>> ans = new ArrayList<List<Integer>>();

    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        // 这个要求与上一个不同点 是 每个数字在每个在每个组合中只能用1次。
        // 每一个数字都需要进行计算下， 注意里面可能会出现
        // 所以在给里面的方案里面有一个字典 也是用的数组 第一个代表的是元素，第二个数是里面有几个数据
        // 按照从低到高进行排序
        Arrays.sort(candidates);
        for (int num : candidates) {
            int size = freq.size();
            if (freq.isEmpty() || freq.get(size - 1)[0] != num) {
                freq.add(new int[] { num, 1 });
            } else {
                ++freq.get(size - 1)[1];
                // freq.set(size - 1, freq.get(size - 1)[1] + 1);
            }
        }
        dfs(0, target);
        return ans;
    }

    public void dfs(int idx, int target) {
        if (target == 0) {
            ans.add(new ArrayList<>(combine));
        }
        // target小于后面的值就直接剪纸，不需要再进行计算了
        if (idx == freq.size() || target < freq.get(idx)[0]) {
            return;
        }
        // idx现在代表的是freq的index位置
        dfs(idx + 1, target);

        int most = Math.min(target / freq.get(idx)[0], freq.get(idx)[1]);
        for (int index = 1; index <= most; index++) {
            combine.add(freq.get(idx)[0]);
            dfs(idx + 1, target - index * freq.get(idx)[0]);
        }
        for (int index = 1; index <= most; index++) {
            combine.remove(combine.size() - 1);
        }
    }
}
// @lc code=end
