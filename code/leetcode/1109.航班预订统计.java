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
        int[] ans = new int[n];
        Map<Integer,Integer> ansMap = new HashMap<Integer,Integer>();
        for (int[] booking : bookings) {
            if(1<=booking[i] && booking[j] <=n ){
                
                if ansMap.containsKey(key)
            }
        }
        for (int index = 1; index <= n; index++) {
            int num = 0;
           
            ans[index - 1] = num;
        }
        return ans;
    }
}
// @lc code=end
