package com.mall.websocket.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@ServerEndpoint("/api/webSocket/{name}")
public class WebSocketServer {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
//    private static Integer onLineCount = 0;
    private static AtomicInteger onlineNum = new AtomicInteger();
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    //concurrent包的线程安全Set，用来存放每个客户端对应的WebSocketServer对象。
    private static ConcurrentHashMap<String, WebSocketServer> webSocketMap = new ConcurrentHashMap<>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    //接收sid
    private String name = "";

    //建立连接成功调用
    @OnOpen
    public void onOpen(Session session, @PathParam("name") String name) {
        this.session = session;
        this.name = name;
        webSocketMap.put(name, this);
        addOnlineCount();
        sendMessage("连接成功");
    }

    //收到客户端信息
    @OnMessage
    public void onMessage(String message, Session session) {
        this.sendMessage(message);
    }

    //发送消息
    public void sendMessage(String message) {
        try {
            synchronized (session) {
                getSession().getBasicRemote().sendText(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendToMessageById(String name, String message) {
        if (webSocketMap.get(name) != null) {
            webSocketMap.get(name).sendMessage(message);
        } else {
            System.out.println("webSocket中没有此key，不推送消息");
        }
    }

    //关闭连接时调用
    @OnClose
    public void onClose(@PathParam("name") String name) {
        webSocketMap.remove(name);
        subOnlineCount();
    }

    //错误时调用
    @OnError
    public void onError(Session session, Throwable throwable) {
        throwable.printStackTrace();
    }

    public Session getSession() {
        return session;
    }

    // 获取当前所有连接的客户端对象
    public ConcurrentHashMap<String, WebSocketServer> getWebSocketMap() {
        return webSocketMap;
    }

    //加一
    public static void addOnlineCount() {
        onlineNum.incrementAndGet();
    }

    //减一
    public static void subOnlineCount() {
        onlineNum.decrementAndGet();
    }


}
