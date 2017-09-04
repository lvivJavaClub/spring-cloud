package com.lohika.jclub.rating.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@EnableRatingServiceClient
@EnableFeignClients(clients = {RatingServiceClient.class})
@SpringBootApplication
public class RatingServiceClientTestApplication {

  public static void main(String[] args) {
    SpringApplication.run(RatingServiceClientTestApplication.class, args);
  }
}
