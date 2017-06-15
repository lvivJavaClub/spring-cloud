package com.lohika.jclub;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class RealtorServiceApplication {

/*	@Bean
	public FeignApartmentFallback createFeignFallback() {
		return new FeignApartmentFallback();
	}*/

	public static void main(String[] args) {
		SpringApplication.run(RealtorServiceApplication.class, args);
	}

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}

@FeignClient(value = "storage-service", fallback = FeignApartmentFallback.class)
interface RealtorService {
	@PostMapping("/apartmentRecords")
	void storeApartment(ApartmentRecord apartmentRecord);
}

@Slf4j
@Component
class FeignApartmentFallback implements RealtorService {

	@Override
	public void storeApartment(ApartmentRecord apartmentRecord) {
		log.error("Error: {}", apartmentRecord);
	}
}
