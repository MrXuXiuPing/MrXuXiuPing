package com.mallplus.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;

@Component
public class IPCheckFilter implements GlobalFilter, Ordered {
    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        HttpHeaders headers = exchange.getRequest().getHeaders();
        // 此处写得非常绝对, 只作演示用, 实际中需要采取配置的方式
        if (getIp(headers).equals("127.0.0.1")) {
            ServerHttpResponse response = exchange.getResponse();
            JSONObject data = new JSONObject();
            data.put("code:", "401");
            data.put("msg:", "非法请求");
            byte[] datas = JSON.toJSONString(data).getBytes(Charset.forName("UTF-8"));
            DataBuffer buffer = response.bufferFactory().wrap(datas);
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
            return response.writeWith(Mono.just(buffer));
        }
        return chain.filter(exchange);
    }

    // 这里从请求头中获取用户的实际IP,根据Nginx转发的请求头获取
    private String getIp(HttpHeaders headers) {
//        IpAddressUtil.getIpAddr(headers);
        String hostName = headers.getHost().getHostName();
        return "127.0.0.1";
    }
}
