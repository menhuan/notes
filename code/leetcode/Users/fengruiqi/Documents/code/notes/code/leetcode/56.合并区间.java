import java.awt.List;
import java.util.ArrayList;
import java.util.Arrays;

/*
 * @lc app=leetcode.cn id=56 lang=java
 *
 * [56] 合并区间
 */

// @lc code=start
class Solution {
    public int[][] merge(int[][] intervals) {
        // 先按照左区间进行排序，然后按照这个排序结果，
        // 进行比较。放入到最终的数据结果里面
        if (intervals.length == 0) {
            return new int[0][2];
        }
        // 对数据进行排序
        Arrays.sort(intervals, (intervals1, intervals2) -> intervals1[0] - intervals2[0]);
        List<int[]> res = new ArrayList<int[]>();
        for (int index = 0; index < intervals.length; index++) {
            int left = intervals[index][0], right = intervals[index][1];
            if (res.size() == 0 || res.get(res.size() - 1)[1] < left) {
                res.add(new int[] { left, right });
            } else {
                res.get(res.size() - 1)[1] = Math.max(res.get(res.size() - 1)[1], right);
            }
        }
        return res.toArray(new int[res.size()][]);
    }
}
// @lc code=end
