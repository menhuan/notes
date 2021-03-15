/*
 * @lc app=leetcode.cn id=1450 lang=java
 *
 * [1450] 在既定时间做作业的学生人数
 */

// @lc code=start
class Solution {
    public int busyStudent(int[] startTime, int[] endTime, int queryTime) {
        int ans = 0;
        for (int index = 0; index < startTime.length; index++) {
            if (queryTime <= endTime[index] && startTime[index] <= queryTime) {
                ans++;
            }
        }
        return ans;
    }
}
// @lc code=end
