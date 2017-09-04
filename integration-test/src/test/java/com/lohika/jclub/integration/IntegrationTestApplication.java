package com.lohika.jclub.integration;

import com.lohika.jclub.storage.client.EnableStorageServiceClient;
import com.lohika.jclub.storage.client.StorageServiceClient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableDiscoveryClient
@EnableStorageServiceClient
@EnableFeignClients(clients = {StorageServiceClient.class})
@SpringBootApplication
public class IntegrationTestApplication {

  public static void main(String[] args) {
    SpringApplication.run(IntegrationTestApplication.class, args);
  }

  @Bean
  @LoadBalanced
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
}
