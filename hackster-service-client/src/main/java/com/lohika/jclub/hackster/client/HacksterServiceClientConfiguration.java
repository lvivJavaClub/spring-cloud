package com.lohika.jclub.hackster.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HacksterServiceClientConfiguration {

  @Bean
  public HacksterServiceClientFallback hacksterServiceClientFallback() {
    return new HacksterServiceClientFallback();
  }
}
