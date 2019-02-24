package com.infervision.service.impl;

import com.infervision.config.MsgSender;
import com.infervision.service.RabbitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: fruiqi
 * @date: 19-2-18 下午5:46
 * @version:1.0
 **/
@Service
public class RabbitServiceImpl implements RabbitService {

    @Autowired
    MsgSender msgSender;

    /**
     * 尝试发送 message 到mq中
     * @param message
     * @return: void
     * @author: fruiqi
     * @date: 19-2-18 下午5:48
     */
    @Override
    public void sendMessage(String exchange, String routingKey,String message) {
        msgSender.sendMsg(exchange, routingKey, message);
    }
}
