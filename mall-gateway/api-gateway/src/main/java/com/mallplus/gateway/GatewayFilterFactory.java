package com.mallplus.gateway;

import com.mallplus.gateway.filter.LoggingFilter;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;

public class GatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {
    @Override
    public GatewayFilter apply(Object config) {
//        return (GatewayFilter) new LoggingFilter();

        return null;
    }
}
