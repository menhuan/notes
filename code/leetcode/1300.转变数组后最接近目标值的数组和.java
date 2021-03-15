import java.util.Arrays;

/*
 * @lc app=leetcode.cn id=1300 lang=java
 *
 * [1300] 转变数组后最接近目标值的数组和
 */

// @lc code=start
class Solution {
    public int findBestValue(int[] arr, int target) {
        // 这道题注意一点的是 证书数组中小于等于value的值，不需要改变。
        int length = arr.length;
        int[] prefix = new int[length + 1];
        Arrays.sort(arr);
        // int[]数组初始化 默认是0 求得前缀和。
        for (int index = 1; index <= length; index++) {
            prefix[index] = prefix[index - 1] + arr[index - 1];
        }

        // 数组中最小值是0，最大值就只能是数组中的最大值。
        int ans = 0;
        int diff = target;
        int mean = target / length;
        for (int index = mean; index <= arr[length - 1]; index++) {
            // 这里枚举的次数比较多。
            int value = Arrays.binarySearch(arr, index);
            if (value < 0) {
                // 代表没有搜索到相关的数字
                value = -value - 1;
            }
            int sum = prefix[value] + (length - value) * index;
            if (Math.abs(sum - target) < diff) {
                ans = index;
                diff = Math.abs(sum - target);
            }
        }
        return ans;
    }
}
// @lc code=end
