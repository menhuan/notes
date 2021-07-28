//给你一个字符串 s，请你将 s 分割成一些子串，使每个子串都是 回文串 。返回 s 所有可能的分割方案。 
//
// 回文串 是正着读和反着读都一样的字符串。 
//
// 
//
// 示例 1： 
//
// 
//输入：s = "aab"
//输出：[["a","a","b"],["aa","b"]]
// 
//
// 示例 2： 
//
// 
//输入：s = "a"
//输出：[["a"]]
// 
//
// 
//
// 提示： 
//
// 
// 1 <= s.length <= 16 
// s 仅由小写英文字母组成 
// 
// Related Topics 字符串 动态规划 回溯 
// 👍 781 👎 0

package src.leetcode.editor.cn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PalindromePartitioning {
    public static void main(String[] args) {
        Solution solution = new PalindromePartitioning().new Solution();
        solution.partition("aab");
    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        boolean[][] f;
        int len ;
        List<List<String>> res = new ArrayList<List<String>>();
        List<String> ans = new ArrayList();
        public List<List<String>> partition(String s) {
            // 分割回文串。既然结果中的每个字符串都是回文串，那么肯定先都得是回文字串才行。
            len = s.length();
            // 定义好字符串的长度
            f = new boolean[len][len];
            // 初始化 填充，每个单个字符的时候都是true。
            for(int index =0;index<len;index++){
                Arrays.fill(f[index],true);
            }
            for(int i =len-1;i  >=0;i--){
                for(int j =i+1 ;j < len;j++){
                    f[i][j] = ( s.charAt(i) == s.charAt(j)) && f[i+1][j-1] ;
                }
            }

            // 上面是确认上面都是回文字符串。现在需要将所有的回文字符串返回
            dfs(s,0);
            return res ;
        }
        public void dfs(String s,int i){
            if(i == s.length()){
                res.add(new ArrayList(ans));
                return ;
            }
            for(int j= i;j<len;j++){
                if (f[i][j]){
                    ans.add(s.substring(i,j+1));
                    dfs(s,j+1);
                    ans.remove(ans.size() -1);
                }

            }

        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}

