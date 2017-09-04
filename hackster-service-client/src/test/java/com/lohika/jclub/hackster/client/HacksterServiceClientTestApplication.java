package com.lohika.jclub.hackster.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@EnableHacksterServiceClient
@EnableFeignClients(clients = {HacksterServiceClient.class})
@SpringBootApplication
public class HacksterServiceClientTestApplication {

  public static void main(String[] args) {
    SpringApplication.run(HacksterServiceClientTestApplication.class, args);
  }
}
