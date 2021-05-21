package com.mallplus.provider.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;

@Service
public class InitDataService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public static final int ThreadNum = 20;

    private static int mobile = 0;
    @Autowired
    private CommonMqService commonMqService;
    //TODO 采用 CountDownLatch 模拟产生高并发时的多线程请求
    @Async
    public void generateMultiThread() {
        log.info("开始初始化线程数----> ");
        try {
            CountDownLatch countDownLatch=new CountDownLatch(1);
            for (int i=0;i<ThreadNum;i++){
                new Thread(new RunThread(countDownLatch)).start();
            }
            //TODO：启动多个线程
            countDownLatch.countDown();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    class RunThread implements Runnable{
        private final CountDownLatch startLatch;

        public RunThread(CountDownLatch startLatch) {
            this.startLatch = startLatch;
        }

        public void run() {
            try {
                //TODO：线程等待
                log.info("线程等待----> ");
                startLatch.await();
                mobile += 1;
                log.info("mobile----> mobile:{}",mobile);
                //TODO：发送消息入抢单队列：env.getProperty("user.order.queue.name")
                commonMqService.sendRobbingMsgV2(String.valueOf(mobile));

                // TODO：直接处理抢单
                // concurrencyService.manageRobbing(String.valueOf(mobile));//v1.0

                // TODO： 发送抢单信息入队列 env.getProperty("product.robbing.mq.queue.name")
                //commonMqService.sendRobbingMsg(String.valueOf(mobile));//v2.0
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }
}

