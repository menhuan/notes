# Rabbitmq的初步使用

随着微服务概念发展，大应用逐步拆分为小应用，提高开发效率，专门的人做专门的事情，逐渐的流行起来。

在微服务上实现通信的方式大部分是采用rpc方式，也有升级版本的grpc。

还有另外一种实现就是使用mq来进行解耦。

今天初识mq，快速入门先，准备一个环境实现案例，该文涉及以下内容：

- 安装rabbitmq
- mq能解决的问题
- 实战演练

## 安装

rabbitmq的安装我们采用docker的方式，docker方便我们快速的实现rabbitmq的安装，不需要再对安装mq进行头疼。

docker 的两种方式

### docker方式

```Linux
//拉取mq镜像
docker pull rabbitmq
//启动mq
docker run -d --name rabbitmq3.7.7 -p 5672:5672 -p 15672:15672 -v `pwd`/data:/var/lib/rabbitmq --hostname myRabbit -e RABBITMQ_DEFAULT_VHOST=my_vhost  -e RABBITMQ_DEFAULT_USER=admin -e RABBITMQ_DEFAULT_PASS=admin df80af9ca0c9

```

说明：

1. -d 后台运行容器；
2. --name 指定容器名；
3. -p 指定服务运行的端口（5672：应用访问端口；15672：控制台Web端口号）；
4. -v 映射目录或文件；
5. --hostname  主机名（RabbitMQ的一个重要注意事项是它根据所谓的 “节点名称” 存储数据，默认为主机名）；
6. -e 指定环境变量；（RABBITMQ_DEFAULT_VHOST：默认虚拟机名；RABBITMQ_DEFAULT_USER：默认的用户名；RABBITMQ_DEFAULT_PASS：默认用户名的密码）

### docker-compose 方式

```yaml
version: "3"
services:
   rabbit:
      image: docker.infervision.com/library/rabbitmq:3-management
      ports:
        - "4369:4369"
        - "5671:5671"
        - "5672:5672"
        - "15671:15671"
        - "15672:15672"
      restart: always
      environment:
        - RABBITMQ_DEFAULT_USER=test
        - RABBITMQ_DEFAULT_PASS=test
      volumes:
        - /home/ruiqi/Desktop/disk/rabbitmq:/var/lib/rabbitmq
      container_name: rabbitmq

在该文件目录下执行：docker-compose up -d
```

下载的rabbitmq内置管理界面，ip:15672 用户名与密码是我们在启动是写入的。
![2019-06-12-17-31-25](http://jikelearn.cn/2019-06-12-17-31-25.png)

## mq能解决什么？

通俗的来说，主要使用MQ来解决以下三个问题。

### 异步消息

在业务中，经常会遇到同时发送邮件，短信或者其他通知内容服务。业务初期，采用同步或者异步处理方式都需要等发送完毕后再返回给客户端。中间有一定的延迟

![2019-06-12-17-49-08](http://jikelearn.cn/2019-06-12-17-49-08.png)

业务增长后，此方式系统性能就会造成很大的浪费。采用消息队列，将这几个服务进行解耦，只需将消息内容发送到消息队列中，降低用户的等待时间，体验效果比原先好很多。

![2019-06-12-17-49-27](http://jikelearn.cn/2019-06-12-17-49-27.png)

### 应用间解耦

同一个服务中可能需要其他服务的配合才能完成一项业务操作.还是拿常见的购物案例来说明。

在京东下单支付后，消息要通知到商家，邮件通知用户已经购买某商品。

如果这两种操作都采用同步执行，用户等待时间会变长。

采用mq方式之后，订单系统将消息持久化到mq上，返回给用户下单成功。

- 商家接收到用户的下单信息，进行处理，如果有库存管理那么需要进行库存处理。
- 邮件通知用户，告知用户下单成功。

mq保证消息的可靠投递，不会导致消息丢失，保证消息的高可靠性。如果库存出现失败也不会导致用户下单失败的情况，可以重新进行投递。

### 流量削峰

流量削峰，一般是同一时间涌进来很多请求，后台处理不过来。那么需要采用削峰方式来处理。

简单来说是通过一个队列承接瞬时过来流量洪峰，在消费端平滑的将消息推送出去，如果消费者消费不及时可以将消息内容持久化在队列中，消息不存在丢失。

1. 消费端不及时进行消费，还可以动态的扩增消费者数量，提高消费速度。
2. 设定相关的阀值，多余的消息直接丢弃，告知用户秒杀失败等业务消息内容。

![摘自简书](http://jikelearn.cn/2019-06-13-13-28-30.png)

## 实战案例

本文是按照Java语言进行，使用Spring boot搭建，包管理工具Gradle。

### 导入rabbitmq jar包

```Gradle
 compile("org.springframework.boot:spring-boot-starter-amqp:1.5.10.RELEASE")
```

### 配置mq

yaml 文件配置

```Yaml
spring:
  rabbitmq:
    host: 192.168.110.5
    port: 5672
    username: tuixiang
    password: tuixiang
```

准备好模板类，供后面直接使用

```Java


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
     **/
    public SimpleMessageListenerContainer setSimpleMessageListenerContainer(String queueName, boolean listenerChannel,
                                                                            int PrefetchCount, int ConcurrentConsumers,
                                                                            AcknowledgeMode acknowledgeMode,
                                                                            ChannelAwareMessageListener listener) throws Exception {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(getConnection());
        container.setQueueNames(queueName);
        container.setExposeListenerChannel(listenerChannel);
        container.setPrefetchCount(PrefetchCount);
        container.setConcurrentConsumers(ConcurrentConsumers);
        container.setAcknowledgeMode(acknowledgeMode);
        container.setMessageListener(listener);
        return container;
    }
}


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
     * @description //TODO 发送消息到消息队列中
     **/
    public void sendMsg(String exchange, String routingKey, Object message) {
        try {
            rabbitTemplate.convertAndSend(exchange,routingKey,message);
        }catch (Exception e){
            logger.error("[ERROR] send statistic message error ",e);
        }
    }

}
```

### 实例链接mq

在使用rabbitmq 有的时候需要自己客户端创建queue，但有的时候并不是自己创建，在rabbitmq页面上进行创建queue，其他消费者直接引用。

#### 客户端创建mq

```Java

    //初始化队列，如果队列已存在，则不作任何处理 如果有权限控制如下操作并不能实现
    @Bean
    public Queue dicomQueue() {
        return new Queue(getMacPreStr(DICOM_QUEUE_NAME));
    }

    //初始化交换机
    @Bean
    public Exchange topicExchange() {
        return ExchangeBuilder.topicExchange((DEFAULT_TOPIC_EXCHANGE).durable(true).build();
    }

    // 将队列与交换机按照路由规则进行绑定
    @Bean
    Binding bindingExchangeDicomQueue(Queue dicomQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(dicomQueue).to(topicExchange).with(DICOM_QUEUE_ROUTING_KEY);
    }

```

### 使用

队列的使用:一个是发送，属于生产者；一个是监听，属于消费者.

#### 生产者实现

在mq配置模板类中，专门实现了一个发送类，发送文件内容，直接调用发送接口即可。

```Java


 @Autowired
    RabbitService rabbitService;

    /**
     * 练习 发送数据到 mq中
     * 1. 发送的数据会到 mq中
     * 2. 我们配置的 listener 是用来消费消息的
     * 3. 客户端配置 可以参考 RabbitClientConfig
     * @param name 名字编号
     * @param vo   实体内容
     * @return: com.infervision.model.NameVo
     */
    @ApiOperation(value = "增加name信息", notes = "实体信息")
    @PostMapping(value = "/{name}")
    @ApiImplicitParam(paramType = "query", name = "name", value = "用户名字", required = true, dataType = "string")
    public NameVo addNameVo(@RequestParam String name, @RequestBody NameVo vo) {
        rabbitService.sendMessage(DEFAULT_TOPIC_TEST_EXCHANGE, LABEL_FIEL_XML_QUEUE_ROUTING_KEY, JSON.toJSONString(vo));
        return vo;
    }


   @Service
public class RabbitServiceImpl implements RabbitService {

    @Autowired
    MsgSender msgSender;

    /**
     * 尝试发送 message 到mq中
     * @param message
     * @return: void
     */
    @Override
    public void sendMessage(String exchange, String routingKey,String message) {
        msgSender.sendMsg(exchange, routingKey, message);
    }
}

```

### 消费者实现

消费者实现有两种方式，一种通过注解的方式监听，一种是实现ChannelAwareMessageListener类来实现消费。

#### 注解实现监听

```Java
//在方法上进行注入。配置工厂帮助提高单个消费者一次性消费的消息数量，设置多少个消费者，用来提高程序的性能
@RabbitListener(queues = "dicom.queue",containerFactory = "multipleConsumerContainerFactory")
    public void processDicomMessage(Message message, Channel channel) {
            logger.info(message);
    }

// 工厂可以在配置模板类中中配置好。
@Bean("multipleConsumerContainerFactory")
    public SimpleRabbitListenerContainerFactory multipleConsumerContainerFactory(SimpleRabbitListenerContainerFactoryConfigurer configurer, ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setPrefetchCount(50);
        factory.setConcurrentConsumers(10);
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        configurer.configure(factory, connectionFactory);
        return factory;
    }
```

#### 实现接口方式

```Java

/**
     * 创建监听器。
     * @author fruiqi
     * @date 19-2-11 下午4:18
     * @param labelStatisticsListener 监听器
     * 调用我们公用的方法
     **/
    @Bean
    public SimpleMessageListenerContainer mqMessageContainer(LabelStatisticsListener labelStatisticsListener) throws Exception {
        SimpleMessageListenerContainer container = rabbitConfig.setSimpleMessageListenerContainer(“queue_name”,
                true, rabbitProperties.getMaximumDelivery(),
                rabbitProperties.getConsumer(), AcknowledgeMode.MANUAL, labelStatisticsListener);
        return container;
    }


@Component
public class LabelStatisticsListener implements ChannelAwareMessageListener {


    private static final Logger logger = LoggerFactory.getLogger(LabelStatisticsListener.class);

    /**
     * 处理传输过来的数据
     * @param message 传送的消息内容
     * @param channel 实现通道
     * @return: void
     */
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        String mes = new String(message.getBody());
        logger.info("[INFO] message is {}",mes);

        // 手动应答 消息已消费
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);

    }
}

```

## 总结

以上内容就完成了rabbitmq 从搭建到使用全部的流程。当然里面还有更多的可以让我们去探讨，比如mq的队列模式，一个系统配置多个mq等等内容。敬请期待我们下一篇mq系列内容。

大家在系统中使用过mq吗？你们使用的mq是什么样的？可以在留言区我们一起探讨哦。

·END·

路虽远，行则必至

本文原发于 同名微信公众号「胖琪的升级之路」，回复「1024」你懂得，给个赞呗。

微信ID：YoungRUIQ

![公众号](http://jikelearn.cn/2019-03-13-23-32-33.png)