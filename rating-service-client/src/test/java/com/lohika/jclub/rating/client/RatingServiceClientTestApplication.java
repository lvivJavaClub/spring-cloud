package com.lohika.jclub.rating.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRatingServiceClient
@SpringBootApplication
public class RatingServiceClientTestApplication {

  public static void main(String[] args) {
    SpringApplication.run(RatingServiceClientTestApplication.class, args);
  }
}
