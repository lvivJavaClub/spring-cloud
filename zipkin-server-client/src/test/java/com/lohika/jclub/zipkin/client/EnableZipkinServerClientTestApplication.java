package com.lohika.jclub.zipkin.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableZipkinServerClient
@SpringBootApplication
public class EnableZipkinServerClientTestApplication {

  public static void main(String[] args) {
    SpringApplication.run(EnableZipkinServerClientTestApplication.class, args);
  }
}
