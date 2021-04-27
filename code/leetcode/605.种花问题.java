/*
 * @lc app=leetcode.cn id=605 lang=java
 *
 * [605] 种花问题
 */

// @lc code=start
class Solution {
    public boolean canPlaceFlowers(int[] flowerbed, int n) {
        int count = 0;

        for (int index = 0; index < flowerbed.length; index++) {
            if (flowerbed.length > 1) {
                if (index == 0 && flowerbed[index] == 0 && flowerbed[index + 1] == 0) {
                    count++;
                    flowerbed[index] = 1;
                    continue;
                }
                if (index == flowerbed.length - 1 && flowerbed[index] == 0 && flowerbed[index - 1] == 0) {
                    count++;
                    flowerbed[index] = 1;
                }
                if (index != 0 && index != flowerbed.length - 1 && flowerbed[index - 1] == 0
                        && flowerbed[index + 1] == 0 && flowerbed[index] == 0) {
                    count++;
                    flowerbed[index] = 1;
                    continue;
                }
            } else {
                if (flowerbed[0] == 0) {
                    count++;
                }
            }

        }
        if (count >= n) {
            return true;
        }
        return false;
    }
}
// @lc code=end
