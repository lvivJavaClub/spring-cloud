package com.lohika.jclub.storage.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@EnableDiscoveryClient
@EnableStorageServiceClient
@EnableFeignClients(clients = {StorageServiceClient.class})
@SpringBootApplication
public class StorageServiceClientTestApplication {

  public static void main(String[] args) {
    SpringApplication.run(StorageServiceClientTestApplication.class, args);
  }
}
