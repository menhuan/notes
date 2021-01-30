package com.infervision.algorithom;

import com.alibaba.fastjson.JSON;
import java.util.concurrent.ConcurrentMap;

/**
 * @ClassName fruiqi
 * @Description 链表的实现
 * @Author frq
 * @Date 2019/4/6 21:08
 * @Version 1.0
 */
public class LinkNode {

    Node head = null;

    /**
     * @return boolean
     * @Author fruiqi
     * @Description 增加节点
     * @Date 21:12 2019/4/6
     * @Param [node]
     **/
    public boolean addNode(Node node) {

        // 新增节点需要判断是不是新的链表
        if (head == null) {
            head = node;
            return true;
        }

        Node tmp = head;
        // 从头开始 遍历，找到尾指针存在的位置
        while (tmp.next != null) {
            tmp = tmp.next;
        }

        tmp.next = node;
        return true;
    }

    /**
     * @return boolean
     * @Author fruiqi
     * @Description 删除指定的节点, 不止删除一个
     * @Date 22:00 2019/4/6
     * @Param [node]
     **/
    public boolean removeNode(String content) {
        // 头节点是null
        if (head == null) {
            return false;
        }

        Node tmp = head.next;
        Node pre = head;
        if (pre.data.equals(content)) {
            // 将head 换下位置内容
            head = head.next;
            pre = head;
        }
        //删除链表中的所有节点内容等于指定内容的节点
        while (tmp.next != null) {
            if (tmp.data.equals(content)) {
                Node next = tmp.next;
                pre.next = next;
                tmp = tmp.next;
                continue;
            }
            pre = tmp;
            tmp = tmp.next;
        }

        return true;

    }

    /**
     * @return void
     * @Author fruiqi
     * @Description 链表的反转
     * @Date 12:34 2019/4/21
     * @Param []
     **/
    public void LinkedInversion() {

    }

    /**
     * @return java.util.List<com.infervision.algorithom.Node>
     * @Author fruiqi
     * @Description 创建一个单链表
     * @Date 23:38 2019/11/6
     * @Param []
     **/
    public void SingleNodes() {
        Node head = new Node(null, "data");

        int maxNode = 10;
        Node next = new Node(null,"next1");
        head.setNext(next);
        for (int i = 0; i < maxNode; i++) {
            Node nextEnd = new Node(null,"next"+i);
            next.setNext(nextEnd);
            next = nextEnd;
        }
        System.out.println(JSON.toJSONString(head));
    }

    public void test(){
        var hashmap =  new ConcurrentMap();
    }
}
