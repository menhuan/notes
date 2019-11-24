package com.infervision.algorithom;

/**
 * @ClassName fruiqi
 * @Description 单链表的实现
 * @Author frq
 * @Date 2019/4/6 20:33
 * @Version 1.0
 */
public class Node {

    /**
     * 指向下一个
     */
    public Node next;

    /**
     * 存储的数据内容
     */
    public String data;

    public Node(Node next, String data) {
        this.next = next;
        this.data = data;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
