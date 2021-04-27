package com.infervision.algorithom;

/**
 * @ClassName fruiqi
 * @Description 队列的数组实现方式
 * @Author frq
 * @Date 2019/4/6 0:06
 * @Version 1.0
 */
public class QueueArray {

    /**
     * 头指针
     */
    private int head;

    /**
     * 尾指针
     */
    private int tail;

    /**
     * 队列大小
     */
    private int size;

    private String[] content;

    public QueueArray(int size) {
        this.content = new String[size];
        this.head =0;
        this.tail =0;
        this.size = size;
    }

    /**
     * @Author fruiqi
     * @Description  入队列
     * 这里没考虑的是扩容的问题。
     * @Date 0:14 2019/4/6
     * @Param [content]
     * @return boolean
     **/
    public boolean popqueue(String con){
        // 入队列需要判断队列是否已满
        if(tail == size){
            //代表队列已满
            if (head == 0){
                return false;
            }
            //如果队列没有满，那么进行数据的迁移
            for (int index = head; index< tail; index ++){
                content[index-head] = content[index];
            }

            //迁移玩数据之后，设置头指针与尾指针
            tail-=head;
            head =0 ;
        }

        content[tail] = con;
        tail++;
        return true;
    }

    
    /**
     * @Author fruiqi
     * @Description  出队列
     * @Date 0:16 2019/4/6
     * @Param []
     * @return java.lang.String
     **/
    public String dequeue(){
        if (size == head){
            //代表 队列已经空
            return null;
        }

        String con = content[head];
        head++;
        return con;
        
    }

}
