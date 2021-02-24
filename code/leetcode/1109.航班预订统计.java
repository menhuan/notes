import java.util.HashMap;
import java.util.Map;

/*
 * @lc app=leetcode.cn id=1109 lang=java
 *
 * [1109] 航班预订统计
 */

// @lc code=start
class Solution {
    public int[] corpFlightBookings(int[][] bookings, int n) {
        int[] diff = new int[n];
        // 计算差分算法
        diff[0] = 0;
        for (int index = 0; index < bookings.length; index++) {
            diff[bookings[index][0] - 1] += bookings[index][2];
            if (bookings[index][1] < n) {
                diff[bookings[index][1]] -= bookings[index][2];
            }

        }
        System.out.println(diff[0]);

        for (int index = 1; index < n; index++) {
            System.out.println(diff[index]);

            diff[index] = diff[index] + diff[index - 1];
        }

        return diff;
    }
}
// @lc code=end
