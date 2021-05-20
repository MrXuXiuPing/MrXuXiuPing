package com.mallplus.provider.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ProducerSMController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RabbitTemplate rabbitTemplate;
}
