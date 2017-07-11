package com.lohika.jclub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class HacksterServiceApplication {
  public static void main(String[] args) {
    SpringApplication.run(HacksterServiceApplication.class, args);
  }
}
