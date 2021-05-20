package com.mallplus.consumer.config;

import com.mallplus.consumer.listener.OrderListener;
import com.mallplus.consumer.listener.PayListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMqConfig {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMqConfig.class);


    @Autowired
    private Environment env;

    /**
     * channel链接工厂
     */
    @Autowired
    private CachingConnectionFactory connectionFactory;

    @Autowired
    private SimpleRabbitListenerContainerFactoryConfigurer factoryConfigurer;
    /**
     * 注入订单对列消费监听器
     */
    @Autowired
    private OrderListener orderListener;
    /**
     * 注入支付监听器
     */
    @Autowired
    private PayListener payListener;

    /**
     * 声明订单队列监听器配置容器
     * @return
     */
    @Bean("orderListenerContainer")
    public SimpleRabbitListenerContainerFactory orderListenerContainer(){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setConnectionFactory(connectionFactory);
        //TODO：并发配置
        factory.setConcurrentConsumers(env.getProperty("spring.rabbitmq.listener.concurrency",int.class));
        factory.setMaxConcurrentConsumers(env.getProperty("spring.rabbitmq.listener.max-concurrency",int.class));
        factory.setPrefetchCount(env.getProperty("spring.rabbitmq.listener.prefetch",int.class));
        return factory;
    }
    /**
     * 支付队列监听器容器
     * @return
     */
    @Bean(name = "payMessageListenerContainer")
    public SimpleRabbitListenerContainerFactory payMessageListenerContainer(){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setConnectionFactory(connectionFactory);
        //TODO：并发配置
        factory.setConcurrentConsumers(env.getProperty("spring.rabbitmq.listener.concurrency",int.class));
        factory.setMaxConcurrentConsumers(env.getProperty("spring.rabbitmq.listener.max-concurrency",int.class));
        factory.setPrefetchCount(env.getProperty("spring.rabbitmq.listener.prefetch",int.class));
        return factory;
    }
//    /**
//     * 支付队列监听器容器
//     * @return
//     */
//    @Bean(name = "payMessageListenerContainer")
//    public SimpleMessageListenerContainer payMessageListenerContainer(){
//        SimpleMessageListenerContainer container=new SimpleMessageListenerContainer();
//        container.setMessageConverter(new Jackson2JsonMessageConverter());
//        container.setConnectionFactory(connectionFactory);
//        //TODO：并发配置
//        container.setConcurrentConsumers(env.getProperty("spring.rabbitmq.listener.concurrency",int.class));
//        container.setMaxConcurrentConsumers(env.getProperty("spring.rabbitmq.listener.max-concurrency",int.class));
//        container.setPrefetchCount(env.getProperty("spring.rabbitmq.listener.prefetch",int.class));
//        //TODO:消息确认机制
//        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
//        container.setQueues(payQueue()); //指定监听器监听的队列
//        container.setMessageListener(payListener); //指定监听器
//        return container;
//    }
}
