package com.ning.service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.ning.service.mapper")
@EnableScheduling
@EnableDiscoveryClient
public class YunosApplication {

    public static void main(String[] args) {
        SpringApplication.run(YunosApplication.class, args);
    }

}

