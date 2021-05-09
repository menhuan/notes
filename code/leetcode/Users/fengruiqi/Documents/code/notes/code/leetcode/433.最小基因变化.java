import java.util.HashSet;

/*
 * @lc app=leetcode.cn id=433 lang=java
 *
 * [433] 最小基因变化
 */

// @lc code=start
class Solution {
    // 最小基因变化
    int minStepCount = Integer.MAX_VALUE;

    public int minMutation(String start, String end, String[] bank) {
        // 进行dfs遍历
        dfs(new HashSet<String>(), 0, start, end, bank);
        // 等于初始值的话 代表没有发现
        return (minStepCount == Integer.MAX_VALUE) ? -1 : minStepCount;
    }

    private void dfs(HashSet<String> step, int stepCount, String current, String end, String[] bank) {
        // 终止条件
        if (current.equals(end))
            minStepCount = Math.min(stepCount, minStepCount);
        // 遍历每个候选
        for (String str : bank) {
            int diff = 0;
            // 遍历候选字符串与当前的字符串进行对比 相差一个的
            // 如果相差一个在递归中那么就代表可以进一步
            for (int i = 0; i < str.length(); i++)
                if (current.charAt(i) != str.charAt(i))
                    // 因为每次变化只能变化一个 所以超过一个的那么本次就不匹配了
                    if (++diff > 1) {
                        break;
                    }
            // 使用一个set 降低重复的概率
            if (diff == 1 && !step.contains(str)) {
                step.add(str);
                dfs(step, stepCount + 1, str, end, bank);
                step.remove(str);
            }
        }
    }
}
// @lc code=end
