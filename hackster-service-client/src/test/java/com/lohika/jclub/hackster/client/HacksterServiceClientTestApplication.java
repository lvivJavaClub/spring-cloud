package com.lohika.jclub.hackster.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableHacksterServiceClient
@SpringBootApplication
public class HacksterServiceClientTestApplication {

  public static void main(String[] args) {
    SpringApplication.run(HacksterServiceClientTestApplication.class, args);
  }
}
