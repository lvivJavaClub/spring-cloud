package com.lohika.jclub.storage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableStorageServiceClient
@SpringBootApplication
public class StorageServiceClientTestApplication {

  public static void main(String[] args) {
    SpringApplication.run(StorageServiceClientTestApplication.class, args);
  }
}
