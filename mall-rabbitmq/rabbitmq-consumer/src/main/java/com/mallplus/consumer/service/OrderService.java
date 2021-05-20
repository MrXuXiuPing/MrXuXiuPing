package com.mallplus.consumer.service;

import com.alibaba.fastjson.JSON;
import com.mallplus.consumer.mapper.SeckillMapper;
import com.mallplus.consumer.request.OrderRecord;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.UUID;


@Service
public class OrderService {

    @Resource
    private SeckillMapper seckillMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Environment env;

    /**
     * 下单，操作数据库
     * @param userId
     * @param goodsId
     */
    @Transactional()
    public void order(String userId,String goodsId){
        //该商品库存-1（当库存>0时）
        int count = seckillMapper.reduceGoodsStockById(goodsId);
        //更新成功，表明抢单成功，插入下单记录，支付状态设为2-待支付
        if(count > 0){
            OrderRecord orderRecord = new OrderRecord();
            orderRecord.setId(UUID.randomUUID().toString());
            orderRecord.setGoodsId(goodsId);
            orderRecord.setUserId(userId);
            orderRecord.setPayStatus(2);
            seckillMapper.insertOrderRecord(orderRecord);
            //将该订单添加到支付队列
            rabbitTemplate.setExchange(env.getProperty("pay.mq.exchange.name"));
            rabbitTemplate.setRoutingKey(env.getProperty("pay.mq.routing.key"));
            rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
            String json = JSON.toJSONString(orderRecord);
            Message msg = MessageBuilder.withBody(json.getBytes()).build();
            rabbitTemplate.convertAndSend(msg);
        }
    }

}
