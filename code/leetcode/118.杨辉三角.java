import java.util.ArrayList;
import java.util.List;

/*
 * @lc app=leetcode.cn id=118 lang=java
 *
 * [118] 杨辉三角
 */

// @lc code=start
class Solution {
    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        if (numRows == 0) {
            return result;
        }

        for (int index = 0; index < numRows; index++) {
            List<Integer> ans = new ArrayList<>();
            for (int j = 0; j <= index; j++) {
                if (j == 0) {
                    ans.add(1);
                    continue;
                }
                if (j == index && j != 0) {
                    ans.add(1);
                    continue;
                }
                int value = result.get(index - 1).get(j - 1) + result.get(index - 1).get(j);
                ans.add(value);
            }
            result.add(ans);

        }
        return result;
    }
}
// @lc code=end
