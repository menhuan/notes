import java.util.ArrayList;
import java.util.List;

/*
 * @lc app=leetcode.cn id=39 lang=java
 *
 * [39] 组合总和
 */

// @lc code=start
class Solution {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        // 回溯算法
        // 一个集合保存等于目标和，其中一个用来存储idx坐标值，index等于数组长度时结束终止
        // 等坐标值结束后 选择一个值之后从数组中再遍历是否有其他数字进行匹配来时目标值相等。
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> combine = new ArrayList<>();
        dfs(candidates, target, result, combine, 0);
        return result;
    }
    public void dfs(int[] candiates,int target ,List<List<Integer>> result,List<Integer> combine,int idx){
        // 遍历到最后一个值
        if (idx == candiates.length){
            return ;
        }
        // 检查目标值是否已经为0，为0 代表已经结束
        if (target == 0){
            // 需要new一个新的对象，容器出现引用问题
            result.add(new ArrayList<>(combine));
            return;
            
        }
        // 索引值+1
        dfs(candiates,target,result,combine,idx+1);
        // 目标值减去当前的值检查是否有
        if (target - candiates[idx]>=0){

            combine.add(candiates[idx]);
            dfs(candiates, target-candiates[idx], result, combine, idx);
            // 移除最后一个元素
            combine.remove(combine.size() -1);    
        }
    }
}
// @lc code=end

