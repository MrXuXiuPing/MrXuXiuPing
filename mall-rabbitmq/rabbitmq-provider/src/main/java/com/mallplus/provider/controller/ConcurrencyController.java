package com.mallplus.provider.controller;

import com.mallplus.provider.service.InitDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 模拟产生高并发的抢单请求Contrlooer控制器
 */
@RestController
public class ConcurrencyController {

    private static final Logger log= LoggerFactory.getLogger(ConcurrencyController.class);

    private static final String Prefix="concurrency";

    @Autowired
    private InitDataService initService;

    // TODO： http://127.0.0.1:8021/concurrency/robbing/thread
    @RequestMapping(value = Prefix+"/robbing/thread",method = RequestMethod.GET)
    public Object robbingThread(){
        initService.generateMultiThread();
        return "OK!!!!!";
    }
}