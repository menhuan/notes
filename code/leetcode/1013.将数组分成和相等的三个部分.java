/*
 * @lc app=leetcode.cn id=1013 lang=java
 *
 * [1013] 将数组分成和相等的三个部分
 */

// @lc code=start
class Solution {
    public boolean canThreePartsEqualSum(int[] arr) {
        int sum = 0;
        for (int num : arr) {
            sum += num;
        }
        System.out.println(sum);
        if (sum % 3 == 0) {
            int times = 0;
            int partSum = sum / 3;
            int part = 0;
            int left = 0;
            int right = arr.length - 1;
            for (int index = 0; index < arr.length; index++) {
                part += arr[index];
                // 找左边节点
                if (part == partSum) {
                    times++;
                    left = index + 1;
                    part = 0;
                    break;
                }
            }
            // System.out.println(left);
            for (int index = left; index < arr.length; index++) {
                part += arr[index];
                if (part == partSum) {
                    times++;
                    part = 0;
                    right = index + 1;
                }
            }
            for (int index = right; index < arr.length; index++) {
                part += arr[index];
                if (part == partSum) {
                    times++;
                }
            }
            // 大于等于3 代表是有些重复的数据 可以任意进行组合 分成三部分
            if (times >= 3) {
                return true;

            }
        }
        return false;
    }
}
// @lc code=end
