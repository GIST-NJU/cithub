package com.gist.cithub.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@MapperScan("com.gist.cithub.user.dao")
@EnableDiscoveryClient
@EnableFeignClients(basePackages="com.gist.cithub.user.feign")
public class CithubUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(CithubUserApplication.class, args);
    }

}
