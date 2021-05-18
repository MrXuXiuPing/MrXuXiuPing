//package com.mallplus.gateway.filter;
//
//import cn.hutool.core.util.StrUtil;
//import cn.hutool.json.JSONUtil;
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import lombok.Data;
//import org.reactivestreams.Publisher;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.Ordered;
//import org.springframework.core.io.buffer.DataBuffer;
//import org.springframework.core.io.buffer.DataBufferFactory;
//import org.springframework.core.io.buffer.DataBufferUtils;
//import org.springframework.http.server.reactive.ServerHttpResponse;
//import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//import java.io.Serializable;
//import java.nio.charset.Charset;
//
//@Component
//public class WrapperResponseFilter implements GlobalFilter, Ordered {
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        return null;
////        ServerHttpResponse originalResponse = exchange.getResponse();
////        DataBufferFactory bufferFactory = originalResponse.bufferFactory();
////        ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
////            @Override
////            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
////                if (body instanceof Flux) {
////                    Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;
////                    return super.writeWith(fluxBody.map(dataBuffer -> {
////                        byte[] content = new byte[dataBuffer.readableByteCount()];
////                        dataBuffer.read(content);
////                        // 释放掉内存
////                        DataBufferUtils.release(dataBuffer);
////                        String s = new String(content, Charset.forName("UTF-8"));
////                        JSONObject data = new JSONObject();
////                        data.put("code:", "1");
////                        data.put("msg:", "请求成功");
////                        byte[] newRs = JSON.toJSONString(data).getBytes(Charset.forName("UTF-8"));
////                        originalResponse.getHeaders().setContentLength(newRs.length);//如果不重新设置长度则收不到消息。
////                        return bufferFactory.wrap(newRs);
////                    }));
////                }
////                return super.writeWith(body);
////            }
////        };
////        return chain.filter(exchange.mutate().response(decoratedResponse).build());
//    }
//
//    @Override
//    public int getOrder() {
//        return -2;
//    }
//}
