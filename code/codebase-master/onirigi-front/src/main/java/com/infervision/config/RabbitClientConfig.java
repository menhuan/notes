package com.infervision.config;

import com.infervision.listener.LabelStatisticsListener;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.infervision.constants.Constants.*;

/**
 * @author: fruiqi
 * @date: 19-2-19 下午2:41
 * @version:1.0 rabbit 客户端配置
 **/
@Configuration
public class RabbitClientConfig {


    @Autowired
    RabbitConfig rabbitConfig;

    @Autowired
    RabbitProperties rabbitProperties;

    /**
     * @return org.springframework.amqp.core.Queue
     * @author fruiqi
     * @description //TODO 初始化队列，如果队列已存在，则不作任何处理
     * @date 19-1-9 下午4:37
     **/
    @Bean
    public Queue labelStatisticQueue() {
        return QueueBuilder.durable(LABEL_FIEL_XML_QUEUE_NAME).build();
    }

    /**
     * @return org.springframework.amqp.core.TopicExchange
     * @author fruiqi
     * @description //TODO 初始化交换机
     * @date 19-1-9 下午4:38
     **/
    @Bean
    public TopicExchange topicLableExchange() {
        return (TopicExchange) ExchangeBuilder.topicExchange(DEFAULT_TOPIC_TEST_EXCHANGE).durable(true).build();
    }

    /**
     * @param labelStatisticQueue 队列名称
     * @param topicExchange       绑定的交换机
     * @return org.springframework.amqp.core.Binding
     * @author fruiqi
     * @description 将队列labelStatisticQueue与topicExchange绑定, binding_key为route.label.statistic.queue, 就是完全匹配
     * @date 19-1-9 下午4:33
     **/
    @Bean
    Binding bindingExchangeLabelStatisticQueue(Queue labelStatisticQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(labelStatisticQueue).to(topicExchange).with(LABEL_FIEL_XML_QUEUE_ROUTING_KEY);
    }


    /**
     * 创建监听器。
     * @author fruiqi
     * @date 19-2-11 下午4:18
     * @param labelStatisticsListener 监听器
     * @return org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer
     **/
    @Bean
    public SimpleMessageListenerContainer mqMessageContainer(LabelStatisticsListener labelStatisticsListener) throws Exception {
        SimpleMessageListenerContainer container = rabbitConfig.setSimpleMessageListenerContainer(LABEL_FIEL_XML_QUEUE_NAME,
                true, rabbitProperties.getMaximumDelivery(),
                rabbitProperties.getConsumer(), AcknowledgeMode.MANUAL, labelStatisticsListener);
        return container;
    }


}
