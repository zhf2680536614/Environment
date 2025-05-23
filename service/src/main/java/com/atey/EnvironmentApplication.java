package com.atey;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.atey.mapper")
public class EnvironmentApplication {
    public static void main(String[] args) {
        SpringApplication.run(EnvironmentApplication.class, args);
    }
}
