package com.mallplus.canal;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import javax.annotation.Resource;

/**
 * @author xxp
 * canal数据同步
 */
@EnableDiscoveryClient
@SpringBootApplication
public class CanalApp/* implements CommandLineRunner , ApplicationRunner*/ {

//    @Resource
//    private CanalClient canalClient;

    public static void main(String[] args) {
        SpringApplication.run(CanalApp.class,args);
    }

   /* @Override
    public void run(String... args) throws Exception {
//        canalClient.run();
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }*/
}
