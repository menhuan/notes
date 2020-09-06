/*
 * @lc app=leetcode.cn id=88 lang=java
 *
 * [88] 合并两个有序数组
 */

// @lc code=start
class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        // 数组备份 使用双指针的方式进行插入数据
        int[] nums1_bak=new int[m];
        System.arraycopy(nums1, 0, nums1_bak, 0, m);
        // 设置双指针
        int num_p1 =0;
        int num_p2 =0;
        int num1_ponints = 0 ;
        // 遍历里面的元素，直到其中一个结束
        while (num_p1 < m && num_p2 < n){
            nums1[num1_ponints++] = nums1_bak[num_p1] < nums2[num_p2] ? nums1_bak[num_p1++] :nums2[num_p2++];
        }
        // 判断哪个没有全部放到数组中，把剩下的放到数组中
        if (num_p1 < m){
            System.arraycopy(nums1_bak, num_p1, nums1, num1_ponints, m+n-num1_ponints);
        }
        if (num_p2 < n){
            System.arraycopy(nums2, num_p2, nums1, num1_ponints, m+n-num1_ponints);
        }
        

    }
}
// @lc code=end

