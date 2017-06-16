package com.lohika.jclub.realtor;


import com.lohika.jclub.storage.EnableStorageServiceClient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableStorageServiceClient
@SpringBootApplication
public class RealtorServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(RealtorServiceApplication.class, args);
  }

  @Bean
  @LoadBalanced
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
}
