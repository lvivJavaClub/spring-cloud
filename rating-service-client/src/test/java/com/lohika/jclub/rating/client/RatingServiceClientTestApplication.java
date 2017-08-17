package com.lohika.jclub.rating.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@EnableDiscoveryClient
@EnableRatingServiceClient
@EnableFeignClients(clients = {RatingServiceClient.class})
@SpringBootApplication
public class RatingServiceClientTestApplication {

  public static void main(String[] args) {
    SpringApplication.run(RatingServiceClientTestApplication.class, args);
  }
}
