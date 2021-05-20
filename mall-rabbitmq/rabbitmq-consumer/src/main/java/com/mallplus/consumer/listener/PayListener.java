package com.mallplus.consumer.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mallplus.consumer.service.PayService;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

//import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;

@Component
public class PayListener implements ChannelAwareMessageListener {

    private static final Logger logger = LoggerFactory.getLogger(PayListener.class);

    @Autowired
    private PayService payService;

    @Value("${pay.mq.queue.name}")
    private String payQueue;
    @RabbitListener(queues = {"#{payQueue}"},containerFactory = "payMessageListenerContainer")
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        Long tag = message.getMessageProperties().getDeliveryTag();
        try {
            String str = new String(message.getBody(), "utf-8");
            logger.info("接收到的消息：{}",str);
            JSONObject json = JSON.parseObject(str);
            String orderId = json.getString("id");
            payService.confirmPay(orderId);
            channel.basicAck(tag, true);
        }catch(Exception e){
            logger.info("支付消息消费出错：{}",e.getMessage());
            logger.info("出错的tag:{}",tag);
            // TODO  确认消费
            channel.basicReject(tag,false);
        }
    }
}
