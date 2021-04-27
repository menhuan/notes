import java.util.HashMap;
import java.util.Map;

/*
 * @lc app=leetcode.cn id=13 lang=java
 *
 * [13] 罗马数字转整数
 */

// @lc code=start
class Solution {
    public int romanToInt(final String s) {
        
        Integer result =0;
        Map<String, Integer> pool = new HashMap<String, Integer>() {
            /**
             * 默认的serialVersionUID
             */
            private static final long serialVersionUID = 1L;

            {
                put("I", 1);
                put("V", 5);
                put("X", 10);
                put("L", 50);
                put("C", 100);
                put("D", 500);
                put("M", 1000);
                put("IV", 4);
                put("IX", 9);
                put("XL", 40);
                put("XC", 90);
                put("CD", 400);
                put("CM", 900);
            }
        };
        for (int index =0; index< s.length();){
            
            String value = String.valueOf(s.charAt(index));
            
            if (value.equals("I") || value.equals("X") || value.equals("C")){
                if (index+1 <s.length() && index+2<=s.length()){
                    String nextValue = s.substring(index+1,index+2);
                    if (pool.containsKey(value+nextValue)){
                        result += pool.get(value+nextValue);
                        index+=2;
                        continue;
                    }
                }
            }
            result += pool.get(value);
            index++;
            
        }
        return Integer.valueOf(result.toString());

    }
}
// @lc code=end
