package com.devs.devs.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.devs.devs"})
@MapperScan(value = "com.devs.devs.mapper")
public class DevsApplication {

    public static void main(String[] args) {
        SpringApplication.run(DevsApplication.class, args);
    }

}
