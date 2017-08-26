package com.lohika.jclub.realtor;

import com.lohika.jclub.storage.client.EnableStorageServiceClient;
import com.lohika.jclub.storage.client.StorageServiceClient;
import com.lohika.jclub.zipkin.client.EnableZipkinServerClient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableZipkinServerClient
@EnableDiscoveryClient
@EnableStorageServiceClient
@EnableFeignClients(clients = {StorageServiceClient.class})
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
