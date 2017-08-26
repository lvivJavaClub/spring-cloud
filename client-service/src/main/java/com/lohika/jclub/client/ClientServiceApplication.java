package com.lohika.jclub.client;

import com.lohika.jclub.storage.client.EnableStorageServiceClient;
import com.lohika.jclub.storage.client.StorageServiceClient;
import com.lohika.jclub.zipkin.client.EnableZipkinServerClient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@EnableDiscoveryClient
@EnableStorageServiceClient
@EnableFeignClients(clients = {StorageServiceClient.class})
@SpringBootApplication
@EnableZipkinServerClient
public class ClientServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientServiceApplication.class, args);
	}

}
