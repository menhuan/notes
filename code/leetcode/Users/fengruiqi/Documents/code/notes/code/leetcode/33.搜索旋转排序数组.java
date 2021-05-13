/*
 * @lc app=leetcode.cn id=33 lang=java
 *
 * [33] 搜索旋转排序数组
 */

// @lc code=start
class Solution {
    public int search(int[] nums, int target) {
        int lo = 0;
        int hi = nums.length - 1;

        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            // 当[0,mid]有序时,向后规约条件 左侧升序的话，在判断这个值是否在右侧 ，那么左侧值+1
            if (nums[0] <= nums[mid] && (target > nums[mid] || target < nums[0])) {
                lo = mid + 1;
                // 当[0,mid]发生旋转时，向后规约条件 左侧有旋转，那么右侧就是递增的
            } else if (target > nums[mid] && target < nums[0]) {
                // 修改左侧的值进行变化
                lo = mid + 1;
            } else {
                // 上面都不符合则将其设定为中间的
                hi = mid;
            }
        }
        // lo与高的相等， 如果值是目标值则返回索引 ，不是则返回-1；
        return lo == hi && nums[lo] == target ? lo : -1;
    }
}

// @lc code=end
