package com.infervision.listener;


import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;

import org.springframework.stereotype.Component;

/**
 * @author: fruiqi
 * @date: 19-2-19 下午2:51
 * @version:1.0
 **/
@Component
public class LabelStatisticsListener implements ChannelAwareMessageListener {


    private static final Logger logger = LoggerFactory.getLogger(LabelStatisticsListener.class);

    /**
     * 处理传输过来的数据
     * @param message 传送的消息内容
     * @param channel 实现通道
     * @return: void
     * @author: fruiqi
     * @date: 19-2-19 下午2:52
     */
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        String mes = new String(message.getBody());
        logger.info("[INFO] message is {}",mes);

        // 手动应答 消息已消费
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);

    }
}
