package com.mallplus.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mallplus.gateway.utils.IpUtils;
import com.mallplus.gateway.utils.PropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * IP黑名单过滤器
 */
@Component
@Slf4j
public class IPCheckFilter implements GlobalFilter, Ordered {

    // 模拟⿊名单（实际可以去数据库或者redis中查询）
    private static List<String> blackList = new ArrayList<>();

    static {
        for (int i = 0; i <20 ; i++) {
//            blackList.add("127.0.0."+i); // 模拟本机地址
            blackList.add("192.0.0."+i); // 模拟本机地址
        }
       log.info("黑名单集合：{}",blackList);
    }
    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request= exchange.getRequest();
        boolean flag = blackList.contains(getIpInfo(request));
        if (flag) {
            ServerHttpResponse response = exchange.getResponse();
            JSONObject data = new JSONObject();
            data.put("code:", "401");
            data.put("msg:", "非法请求");
            byte[] datas = JSON.toJSONString(data).getBytes(Charset.forName("UTF-8"));
            DataBuffer buffer = response.bufferFactory().wrap(datas);
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
            return response.writeWith(Mono.just(buffer));
        }// 合法请求，放⾏，执⾏后续的过滤器
        return chain.filter(exchange);
    }

    // 这里从请求头中获取用户的实际IP,根据Nginx转发的请求头获取
    private String getIpInfo(ServerHttpRequest request) {
        String ipAddress = IpUtils.getIpAddress(request);
        log.info("来源访问ip：{}",ipAddress);
        return ipAddress;
    }
}
