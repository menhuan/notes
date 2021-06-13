/*
 * @lc app=leetcode.cn id=493 lang=java
 *
 * [493] 翻转对
 */

// @lc code=start
class Solution {

    public int reversePairs(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        return reversePairsRecursive(nums, 0, nums.length - 1);
    }

    /**
     * 归并排序的方式
     * 
     * @param nums  要排序的数组
     * @param left  左边界
     * @param right 右边解
     * @return
     */
    public int reversePairsRecursive(int[] nums, int left, int right) {
        // 归并排序的终止条件
        if (left == right) {
            return 0;
        } else {
            // 找到中间值
            int mid = (left + right) / 2;
            // 继续进行归并排序
            int n1 = reversePairsRecursive(nums, left, mid);
            int n2 = reversePairsRecursive(nums, mid + 1, right);
            // 找到两个查询到的子答案重要翻转对和。
            int ret = n1 + n2;

            // 首先统计下标对的数量 从上面进行二分截断查找，最后就是从左边开始进行查找匹配
            int i = left;
            int j = mid + 1;
            // i小于中间的值
            while (i <= mid) {
                // 右边解是到j
                while (j <= right && (long) nums[i] > 2 * (long) nums[j]) {
                    j++;
                }
                // 计算查出来的结果
                ret += j - mid - 1;
                i++;
            }

            // 随后合并两个排序数组
            int[] sorted = new int[right - left + 1];
            int p1 = left, p2 = mid + 1;
            int p = 0;
            while (p1 <= mid || p2 <= right) {
                if (p1 > mid) {
                    sorted[p++] = nums[p2++];
                } else if (p2 > right) {
                    sorted[p++] = nums[p1++];
                } else {
                    if (nums[p1] < nums[p2]) {
                        sorted[p++] = nums[p1++];
                    } else {
                        sorted[p++] = nums[p2++];
                    }
                }
            }
            // 将排好序的数组重新赋值到范围上。
            for (int k = 0; k < sorted.length; k++) {
                nums[left + k] = sorted[k];
            }
            return ret;
        }
    }
}
// @lc code=end
