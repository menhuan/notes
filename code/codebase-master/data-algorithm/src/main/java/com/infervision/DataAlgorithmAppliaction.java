package com.infervision;

import com.infervision.algorithom.LinkNode;
import com.infervision.algorithom.Node;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClassName fruiqi
 * @Description
 * @Author frq
 * @Date 2019/4/3 0:04
 * @Version 1.0
 */
@SpringBootApplication
public class DataAlgorithmAppliaction {

    public static void main(String[] args) {
        SpringApplication.run(DataAlgorithmAppliaction.class, args);
    }


    /**
     * @Author fruiqi
     * @Description  测试单链接表
     * @Date 23:25 2019/4/6
     * @return void
     **/
    public void testAloneLink(){
        LinkNode linkNode = new LinkNode();
        Node head = new Node(null,"head");
        linkNode.addNode(head);
        Node first = new Node(null,"first");
        Node first1 = new Node(null,"first1");
        Node first2 = new Node(null,"first2");
        linkNode.addNode(first);
        linkNode.addNode(first1);
        linkNode.addNode(first2);

        linkNode.removeNode("first1");
        System.out.println("test");

    }

}
