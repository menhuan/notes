package com.infervision.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: fruiqi
 * @date: 19-2-18 下午2:42
 * @version:1.0 rabbit配置
 **/
@Configuration
public class RabbitConfig {

    /**
     * 日志
     **/
    private static final Logger logger = LoggerFactory.getLogger(RabbitConfig.class);


    @Value("${spring.rabbitmq.username}")
    String userName;

    @Value("${spring.rabbitmq.password}")
    String userPassword;

    @Value("${spring.rabbitmq.host}")
    String host;

    @Value("${spring.rabbitmq.port}")
    Integer port;

    /**
     * 注入
     *
     * @param
     * @return com.rabbitmq.client.Connection
     * @author fruiqi
     * @date 19-1-22 下午5:41
     **/
    @Bean
    public ConnectionFactory getConnection() throws Exception {
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setUsername(userName);
        factory.setPassword(userPassword);
        factory.setHost(host);
        factory.setPort(port);
        return factory;
    }


    /**
     * 创建制定的 监听容器
     *
     * @param queueName  监听的队列名字
     * @param listenerChannel 设置是否将监听的频道 公开给已注册的
     * @param PrefetchCount  告诉代理一次请求多少条消息过来
     * @param ConcurrentConsumers  制定创建多少个并发的消费者数量
     * @param acknowledgeMode  消息确认模式
     * @param listener 监听器
     * @return org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer
     * @author fruiqi
     * @date 19-2-11 下午4:13
     **/
    public SimpleMessageListenerContainer setSimpleMessageListenerContainer(String queueName, boolean listenerChannel,
                                                                            int PrefetchCount, int ConcurrentConsumers,
                                                                            AcknowledgeMode acknowledgeMode,
                                                                            ChannelAwareMessageListener listener) throws Exception {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(getConnection());
//        container.setQueueNames(getMACAddressRrefix(queueName));
        container.setQueueNames(queueName);
        container.setExposeListenerChannel(listenerChannel);
        container.setPrefetchCount(PrefetchCount);
        container.setConcurrentConsumers(ConcurrentConsumers);
        container.setAcknowledgeMode(acknowledgeMode);
        container.setMessageListener(listener);
        return container;
    }


}
