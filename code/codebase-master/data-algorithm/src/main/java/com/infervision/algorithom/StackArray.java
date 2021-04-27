package com.infervision.algorithom;

/**
 * @ClassName fruiqi
 * @Description 栈的数组实现
 * @Author frq
 * @Date 2019/4/3 0:07
 * @Version 1.0
 */
public class StackArray {
    //数组内容
    private String[] contents;

    //下标
    private int  index;

    // 容量
    private int num;

    // 初始化 栈空间
    public StackArray( int num) {
        this.contents = new String[num];
        this.index = 0;
        this.num = num;
    }

    public boolean pushStack(String content){
        if (num==index){
            //代表空间满了 入栈失败
            return false;
        }
        contents[index] = content;
        index ++;
        return true;
    }
    
    /**
     * @Author fruiqi
     * @Description  出栈操作
     * @Date 0:13 2019/4/3
     * @Param []
     * @return boolean
     **/
    public String popStack(){
        if (index ==0 ){
            //代表占空间已经空了
            return null ;
        }
        index--;
        String content = contents[index];
        return content;
    }



    public String[] getContent() {
        return contents;
    }

    public void setContent(String[] contents) {
        this.contents = contents;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
