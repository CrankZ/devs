package com.devs.devs;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.devs.devs.dao")
public class DevsApplication {

  public static void main(String[] args) {
    SpringApplication.run(DevsApplication.class, args);
  }

}
