package com.gist.cithub.repo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@MapperScan("com.gist.cithub.repo.dao")
@EnableFeignClients("com.gist.cithub.repo.feign")
@EnableDiscoveryClient
public class CithubRepoApplication {

    public static void main(String[] args) {
        SpringApplication.run(CithubRepoApplication.class, args);
    }

}
