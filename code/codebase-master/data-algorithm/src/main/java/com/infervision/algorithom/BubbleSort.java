package com.infervision.algorithom;

import com.alibaba.druid.support.json.JSONUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName fruiqi
 * @Description 冒泡排序
 * @Author frq
 * @Date 2019/4/21 12:33
 * @Version 1.0
 */
public class BubbleSort {

    private  static final Logger logger = LoggerFactory.getLogger(BubbleSort.class);
    /**
     * @return void
     * @Author fruiqi
     * @Description 对数组内容进行冒泡排序算法的实现
     * 目标：从大到小排序 相反的顺序也是可以的 判断条件需要进行改变
     * 重点：冒泡排序进行比较相邻两个元素
     * @Date 12:35 2019/4/21
     * @Param [nums]
     **/
    public void bubbleSortFirst(Integer[] nums) {
        int size = nums.length;

        // 小于2 的情况不用排序
        if (size < 2) {
            return;
        }

        // 全部循环遍历完毕 时间复杂度是O(n*n)
        for (int i = 0; i < size; i++) {
            // 标志位，当一次循环中，没有数据交换
            boolean isFlag = false;
            for (int j = i; j < size - 1; j++) {
                if (nums[j] < nums[j + 1]) {
                    //进行数据的交换
                    int value = nums[j];
                    nums[j] = nums[j + 1];
                    nums[j + 1] = value;
                    isFlag = true;
                }
            }

            if (!isFlag) {
                break;
            }
        }
        logger.info("message {}", JSONUtils.toJSONString());
    }

    /**
     * @return void
     * @Author fruiqi
     * @Description 插入排序算法的实现, 目的是从小到大
     * 插入排序的思想是在已知的序列中，找到需要插入的位置，将后面的数据进行迁移。
     * 分为两部分 已排序区间与未排序区间。
     * 目标在实现从小到大，拿数据从末尾查找位置，
     * @Date 21:30 2019/4/21
     * @Param [nums]
     **/
    public void insertSort(Integer[] nums) {
        int size = nums.length;
        // 排除数据就一个的不需要排序这种情况
        if (size < 2) {
            return;
        }

        // 无序再有序
        for (int i = 1; i < size; i++) {
            int value = nums[i];
            int j = i - 1;
            for (; j > 0; j--) {
                //查找数据的位置，数据的迁移 只进行迁移一步，前面数据以保存数据值
                if (nums[j] > value) {
                    nums[j + 1] = nums[j];
                } else {
                    break;
                }
            }

            //最后插入数据
            nums[j + 1] = value;
        }
    }

    /**
     * @return void
     * @Author fruiqi
     * @Description 选择排序,从小到大
     * 选择排序也区分 有序与无序的空间。
     * 但与插入排序的区别是，选择排序是从未排序的空间里面选择最小的（自己实现可以根据别的实现方式）
     * 选择最下的元素之后，进行数据的交换，不需要引入其他同样大小的数组空间
     * @Date 22:54 2019/4/21
     * @Param [nums]
     **/
    public void choseSort(Integer[] nums) {
        int size = nums.length;
        // 小于2 就不用排序
        if (size < 2) {
            return;
        }

        for (int index = 0; index < size; index++) {
            int value = nums[index];
            // 找到元素中最小的
            int min = Integer.MIN_VALUE ;
            int j= index + 1 ;
            int minJ = 0;
            for ( ; j <size ;j ++ ){
                if (nums[j]<min){
                    min = nums[j];
                    minJ = j ;
                }
            }
            nums[index] = min;
            nums[minJ] = value;
        }


    }

}
