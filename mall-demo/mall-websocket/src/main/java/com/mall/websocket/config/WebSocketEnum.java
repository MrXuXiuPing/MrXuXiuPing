package com.mall.websocket.config;

/**
 * 枚举描述:　不同页面区分webSocket
 */
public enum  WebSocketEnum {
    /**
     * 实时报警
     */
    SOCKET_ENUM_ALARM(1,"实时报警"),

    /**
     * 温度传感器
     */
    SOCKET_ENUM_TEMP(2,"温度传感器"),

    /**
     * 压力传感器
     */
    SOCKET_ENUM_PRESSURE(3,"压力传感器"),

    /**
     * 液位传感器
     */
    SOCKET_ENUM_LIQUID(4,"液位传感器"),

    /**
     * 可燃传感器
     */
    SOCKET_ENUM_COMBUSTIBLE(5,"可燃传感器"),

    /**
     * 有毒传感器
     */
    SOCKET_ENUM_POISONOUS(6,"有毒传感器");


    private Integer code;
    private String msg;

    WebSocketEnum(Integer code,String msg){
        this.code = code;
        this.msg = msg;
    }
    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
