package com.gist.cithub.tools;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.gist.cithub.tools.dao")
public class CithubToolsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CithubToolsApplication.class, args);
    }

}
