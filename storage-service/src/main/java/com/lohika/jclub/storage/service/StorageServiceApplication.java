package com.lohika.jclub.storage.service;

import com.lohika.jclub.zipkin.client.EnableZipkinServerClient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableZipkinServerClient
@EnableDiscoveryClient
@SpringBootApplication
public class StorageServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(StorageServiceApplication.class, args);
	}
}
