import java.security.KeyStore.Entry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 * @lc app=leetcode.cn id=621 lang=java
 *
 * [621] 任务调度器
 */

// @lc code=start
class Solution {
    public int leastInterval(char[] tasks, int n) {
        Map<String, Integer> contains = new HashMap();
        boolean isBreak = false;
        for (int index = 0; index < tasks.length; index++) {
            if (contains.containsKey(String.valueOf(tasks[index]))) {
                int value = contains.get(String.valueOf(tasks[index])) + 1;
                contains.put(String.valueOf(tasks[index]), value);
            } else {
                contains.put(String.valueOf(tasks[index]), 1);
            }
        }
        List<Integer> nums = new ArrayList<>(contains.values());
        int size = nums.size();
        Collections.sort(nums);
        boolean isContinue = true;
        int result = 0;
        System.out.println(nums);
        while (isContinue) {
            int zeroTimes = 0;
            int times = 0;
            for (int index = 0; index < nums.size(); index++) {
                if (nums.get(index) != 0) {
                    int newValue = nums.get(index) - 1;
                    nums.set(index, newValue);
                    times++;
                    if (times == n + 1) {
                        result += n;
                        break;
                    }
                } else {
                    zeroTimes++;
                    if (zeroTimes == size) {
                        System.out.println("result" + result);
                        return result;
                    }

                }
            }

        }
        return result;

    }
}
// @lc code=end
