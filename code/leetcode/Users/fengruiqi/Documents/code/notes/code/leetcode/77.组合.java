import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/*
 * @lc app=leetcode.cn id=77 lang=java
 *
 * [77] 组合
 */

// @lc code=start
class Solution {
    private List<List<Integer>> ans;

    public List<List<Integer>> combine(int n, int k) {
        ans = new ArrayList<>();
        generate(1, n, k, new LinkedList<>());
        return ans;
    }

    public void generate(int start, int n, int k, LinkedList<Integer> list) {
        {
            // 终止条件
            if (list.size() == k) {
                ans.add(new ArrayList<>(list));
                return;
            }

            for (int index = start; index <= n; index++) {
                // 下一层操作
                list.add(index);
                generate(index + 1, n, k, list);
                list.removeLast();
            }
        }

    }
}
// @lc code=end
