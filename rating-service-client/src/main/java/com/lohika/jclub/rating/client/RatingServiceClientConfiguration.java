package com.lohika.jclub.rating.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RatingServiceClientConfiguration {

  @Bean
  public RatingServiceClientFallback RatingServiceClientFallback() {
    return new RatingServiceClientFallback();
  }
}
