package com.lohika.jclub.storage.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageServiceClientConfiguration {

  @Bean
  public StorageServiceClientFallback storageServiceClientFallback() {
    return new StorageServiceClientFallback();
  }
}
