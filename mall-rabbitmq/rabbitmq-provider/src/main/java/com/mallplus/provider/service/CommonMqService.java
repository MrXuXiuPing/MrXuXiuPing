package com.mallplus.provider.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/**
 * 将抢单请求的手机号信息压入队列，等待排队处理
 */
@Service
public class CommonMqService {
    private static final Logger log= LoggerFactory.getLogger(CommonMqService.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Environment env;

    /**
     * 发送抢单信息入队列
     * @param mobile
     */
    public void sendRobbingMsgV2(String mobile){
        try {
            rabbitTemplate.setExchange(env.getProperty("order.mq.exchange.name"));
            rabbitTemplate.setRoutingKey(env.getProperty("order.mq.routing.key.name"));
            Message message= MessageBuilder.withBody(mobile.getBytes("UTF-8")).setDeliveryMode(MessageDeliveryMode.PERSISTENT)
                    .build();
            rabbitTemplate.send(message);
            log.info( "将抢单请求的手机号信息压入队列V2，等待排队处理 mobile:{}",mobile);
        }catch (Exception e){
            log.error("发送抢单信息入队列V2 发生异常： mobile={} ",mobile);
        }
    }
}
