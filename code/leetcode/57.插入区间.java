import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/*
 * @lc app=leetcode.cn id=57 lang=java
 *
 * [57] 插入区间
 */

// @lc code=start
class Solution {
    public int[][] insert(int[][] intervals, int[] newInterval) {
        // 类似于前面的数据
        if (intervals.length == 0) {
            var result = new int[1][1];
            result[0] = newInterval;
            return result;
        }
        List<int[]> old = new ArrayList<int[]>();
        old.add(newInterval);
        for (int[] interval : intervals) {
            old.add(interval);
        }
        int[][] oldnew = old.toArray(new int[old.size()][]);
        Arrays.sort(oldnew, (old1, old2) -> old1[0] - old2[0]);
        List<int[]> merge = new ArrayList<int[]>();
        for (int index = 0; index < oldnew.length; index++) {
            int left = oldnew[index][0], right = oldnew[index][1];
            if (merge.size() == 0 || merge.get(merge.size() - 1)[1] < left) {
                merge.add(new int[] { left, right });
            } else {
                merge.get(merge.size() - 1)[1] = Math.max(merge.get(merge.size() - 1)[1], right);
            }
        }
        return merge.toArray(new int[merge.size()][]);
    }
}
// @lc code=end
