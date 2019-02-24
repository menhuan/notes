package com.infervision.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author: fruiqi
 * @date: 19-2-19 下午4:57
 * @version:1.0
 **/
@ConfigurationProperties(prefix = RabbitProperties.RABBIT_CLIENT_CONFIG )
public class RabbitProperties {

    /**
     * rabbit 客户端配置
     */
    public final static String RABBIT_CLIENT_CONFIG = "spring.rabbitmq.client";

    /**
     * 消费者数量
     */
    private Integer  consumer ;

    /**
     * 每个消费者获取的最大投递数量
     */
    private Integer  maximumDelivery;


    public Integer getConsumer() {
        return consumer;
    }

    public RabbitProperties setConsumer(Integer consumer) {
        this.consumer = consumer;
        return this;
    }

    public Integer getMaximumDelivery() {
        return maximumDelivery;
    }

    public RabbitProperties setMaximumDelivery(Integer maximumDelivery) {
        this.maximumDelivery = maximumDelivery;
        return this;
    }
}
