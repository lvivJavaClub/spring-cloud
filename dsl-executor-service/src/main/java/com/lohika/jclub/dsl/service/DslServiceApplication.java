package com.lohika.jclub.dsl.service;

import com.lohika.jclub.rating.client.EnableRatingServiceClient;
import com.lohika.jclub.rating.client.RatingServiceClient;
import com.lohika.jclub.storage.client.EnableStorageServiceClient;
import com.lohika.jclub.storage.client.StorageServiceClient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@EnableDiscoveryClient
@EnableStorageServiceClient
@EnableRatingServiceClient
@EnableFeignClients(clients = {StorageServiceClient.class, RatingServiceClient.class})
@SpringBootApplication
public class DslServiceApplication {
  public static void main(String[] args) {
    SpringApplication.run(DslServiceApplication.class, args);
  }
}
