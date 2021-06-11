package com.mall.websocket.task;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.mall.websocket.config.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.websocket.Session;
import java.util.concurrent.ConcurrentHashMap;

@Component
@EnableScheduling
public class TimeTask {

    @Autowired
    private WebSocketServer webSocketServer;
    // 5秒推送一次
//    @Scheduled(cron = "*/5 * * * * ?")
    @Scheduled(cron = "0/3 * * * * ?")
    public void sendMsg(){
        ConcurrentHashMap<String, WebSocketServer> socket = webSocketServer.getWebSocketMap();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("CPU", IdUtil.randomUUID());
        jsonObject.put("内存",IdUtil.randomUUID());
        jsonObject.put("磁盘",IdUtil.randomUUID());
        if(CollectionUtil.isNotEmpty(socket)){
//            webSocketServer.sendMessage("hello!!!!!");
            webSocketServer.sendToMessageById("1",jsonObject.toString());
        }
    }
}
