package com.mallplus.gateway;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 *
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class ApiGateWay implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(ApiGateWay.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
