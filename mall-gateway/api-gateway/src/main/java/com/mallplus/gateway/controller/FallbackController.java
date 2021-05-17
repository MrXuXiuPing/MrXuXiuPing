package com.mallplus.gateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hystrix 熔断降级
 */
@RestController
public class FallbackController {

    @GetMapping("/fallback")
    public ResponseEntity fallback() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("服务暂不可用！！！！");
    }
}