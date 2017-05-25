package com.lohika.jclub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@EnableDiscoveryClient
@SpringBootApplication
public class HacksterServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(HacksterServiceApplication.class, args);
	}

	// dummy data
	@Component
	class DummyCLR implements CommandLineRunner {

		@Autowired
		private HacksterRepository hacksterRepository;

		@Override
		public void run(String... strings) throws Exception {
			List<Hackster> hacksters = Stream.of("123", "456", "789").map(Hackster::new).collect(Collectors.toList());
			hacksterRepository.save(hacksters);


			hacksterRepository.findAll().forEach(System.out::println);
		}
	}
}
