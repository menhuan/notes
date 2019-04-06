package com.infervision.algorithom;

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


}
