package com.lohika.jclub.realtor;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.EnvironmentTestUtils;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.LogMessageWaitStrategy;

import javax.ws.rs.core.MediaType;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(initializers = RealtorServiceApplicationTests.Initializer.class)
public class RealtorServiceApplicationTests {
	private static final int MAX_ALLOWED_APARTMENTS_PER_REALTOR = 4;

	@ClassRule
	public static GenericContainer storageService = new GenericContainer("storage-service:latest")
			.withExposedPorts(8091)
			.withEnv("WAITING_FOR_DEPENDENCE", "false")
			.waitingFor(new LogMessageWaitStrategy().withRegEx(".*Started StorageServiceApplication in.*\\s"));

	@ClassRule
	public static GenericContainer HacksterService = new GenericContainer("hackster-service:latest")
			.withExposedPorts(8082)
			.withEnv("maxAllowedApartmentsPerRealtor", Integer.toString(MAX_ALLOWED_APARTMENTS_PER_REALTOR))
			.withEnv("spring.cloud.config.discovery.enabled", "false")
			.withEnv("spring.cloud.config.fail-fast", "false")
			.withEnv("WAITING_FOR_DEPENDENCE", "false")
			.waitingFor(new LogMessageWaitStrategy().withRegEx(".*Started HacksterServiceApplication in.*\\s"));

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void contextLoads() throws Exception {
	}

	@Test
	public void apartments() throws Exception {
		ApartmentRecord apartmentRecord = ApartmentRecord.builder()
				.phone("123")
				.realtorName("Anna Realtor")
				.sqft(44)
				.price(100)
				.mail("mail")
				.location("Lviv").build();

		mockMvc.perform(MockMvcRequestBuilders
				.post("/storeApartments")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsBytes(apartmentRecord)))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk());
	}

	public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
		@Override
		public void initialize(ConfigurableApplicationContext configurableApplicationContext) {

			EnvironmentTestUtils.addEnvironment("testcontainers", configurableApplicationContext.getEnvironment(),
					"storage-service.ribbon.servers=http://" + storageService.getContainerIpAddress() + ":"
					+ storageService.getMappedPort(8091) + "/",
					"hackster-service.ribbon.servers=http://" + HacksterService.getContainerIpAddress() + ":"
					+ HacksterService.getMappedPort(8082) + "/"
			);
		}
	}
}
