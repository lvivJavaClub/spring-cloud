package com.lohika.jclub.rating.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class RatingServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(RatingServiceApplication.class, args);
  }
}
