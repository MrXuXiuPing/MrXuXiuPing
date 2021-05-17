package com.mallplus.gateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import reactor.core.publisher.Mono;

/**
 * 路由限流配置
 * 限流，漏桶算法以及令牌桶算法 漏桶算法 有一个固定容量的桶，对于流入的水无法预计速率，流出的水以固定速率，当水满之后会溢出。
 * 令牌桶算法，有一个固定容量的桶，桶里存放着令牌（token）。桶最开始是空的，token以一个固定速率向桶中填充，直到达到桶的容量，
 * 多余的token会被丢弃。每当一个请求过来时，都先去桶里取一个token，如果没有token的话请求无法通过。
 * 两种算法的最主要区别是令牌桶算法允许一定流量的突发，因为令牌桶算法中取走token是不需要时间的，
 * 即桶内有多少个token都可以瞬时拿走。基于这个特点令牌桶算法在互联网企业中应用比较广泛。
 */

/**
 * 配置限流key
 */
@Configuration
public class GatewayConfiguration {

    @Bean(name="apiKeyResolver")
    @Primary
    public KeyResolver apiKeyResolver() {
        //URL限流,超出限流返回429状态
        return exchange -> Mono.just(exchange.getRequest().getPath().value());
    }

    /**
     * ip限流操作
     *
     * @return
     */
    @Bean
    public KeyResolver ipKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getHostName());
    }
    /**
     * 用户限流
     * 使用这种方式限流，请求路径中必须携带userId参数。
     * @return
     */
//    @Bean
//    KeyResolver userKeyResolver() {
//        return exchange -> Mono.just(exchange.getRequest().getQueryParams().getFirst("userId"));
//    }
}
