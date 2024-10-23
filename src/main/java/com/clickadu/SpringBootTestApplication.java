package com.clickadu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableSpringConfigured
public class SpringBootTestApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringBootTestApplication.class, args);
  }
}
