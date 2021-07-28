import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/*
 * @lc app=leetcode.cn id=771 lang=java
 *
 * [771] 宝石与石头
 */

// @lc code=start
class Solution {
    public int numJewelsInStones(String jewels, String stones) {
        Set<Character> jewelsSet = new HashSet<>();
        for (int index = 0; index < jewels.length(); index++) {
            jewelsSet.add(jewels.charAt(index));
        }
        int total = 0;
        for (int index = 0; index < stones.length(); index++) {

            if (jewelsSet.contains(stones.charAt(index))) {
                total++;
            }
        }

        return total;
    }
}
// @lc code=end
