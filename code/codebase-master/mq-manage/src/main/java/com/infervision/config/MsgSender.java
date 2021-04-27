package com.infervision.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author: fruiqi
 * @date: 19-2-18 下午2:51
 * @version:1.0
 **/
@Component
public class MsgSender {


    private static final Logger logger = LoggerFactory.getLogger(MsgSender.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * @param exchange 交换机名称
     * @param routingKey 路由名称
     * @param message 消息内容
     * @return void
     * @author fruiqi
     * @description //TODO 发送消息到消息队列中
     * @date 19-1-9 下午4:46
     **/
    public void sendMsg(String exchange, String routingKey, Object message) {
        try {
            rabbitTemplate.convertAndSend(exchange,routingKey,message);
        }catch (Exception e){
            logger.error("[ERROR] send statistic message error ",e);
        }
    }

}
