/*
 * @lc app=leetcode.cn id=1122 lang=java
 *
 * [1122] 数组的相对排序
 */

// @lc code=start
class Solution {
    public int[] relativeSortArray(int[] arr1, int[] arr2) {
        // 用桶计数排序比较方便些。
        // 定义一个数组数组里面存储的是下标值出现的额次数。
        int maxVlue = 0;
        for (int arr : arr1) {
            maxVlue = Math.max(arr, maxVlue);
        }
        int[] fre = new int[maxVlue + 1];
        for (int arr : arr1) {
            ++fre[arr];
        }
        // 最后一个数组先遍历把在arr2中出现的数据放到前面。

        int[] ans = new int[arr1.length];
        // 永存保存ans的下标
        int index = 0;
        for (int arr : arr2) {
            for (int i = 0; i < fre[arr]; ++i) {
                ans[index++] = arr;
            }
            // 修改成0
            fre[arr] = 0;
        }

        // 剩下没有出现的就放到结果的最后面
        for (int i = 0; i <= maxVlue; i++) {
            // 遍历里面的次数
            for (int j = 0; j < fre[i]; j++) {
                ans[index++] = i;
            }
        }

        return ans;
    }
}
// @lc code=end
