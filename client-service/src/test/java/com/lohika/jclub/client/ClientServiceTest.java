package com.lohika.jclub.client;

import com.lohika.jclub.storage.client.Apartment;
import com.lohika.jclub.storage.client.StorageServiceClient;

import org.junit.ClassRule;
import org.junit.Rule;
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
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.LogMessageWaitStrategy;

import java.time.Duration;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ClientServiceApplication.class)
@ContextConfiguration(initializers = ClientServiceTest.Initializer.class)
@AutoConfigureMockMvc
public class ClientServiceTest {

    @ClassRule
    public static GenericContainer discoveryService = new GenericContainer("discovery-service:latest")
            .waitingFor(new LogMessageWaitStrategy().withRegEx(".*Started EurekaServerApplication in.*\\s"))
            .withExposedPorts(8761);

    @Rule
    public GenericContainer storageService = new GenericContainer("storage-service:latest")
            .withExposedPorts(8091)
            .withEnv("eureka.client.serviceUrl.defaultZone", "http://" + discoveryService.getContainerIpAddress() + ":" + discoveryService.getMappedPort(8761) + "/eureka")
            .withEnv("eureka.instance.preferIpAddress", "true")
            .waitingFor(new LogMessageWaitStrategy().withRegEx(".*DiscoveryClient_STORAGE-SERVICE.*registration status: 204.*\\s"));

    @Autowired
    private StorageServiceClient storageServiceClient;

    @Autowired
    private MockMvc mockMvc;

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            EnvironmentTestUtils.addEnvironment("testcontainers", configurableApplicationContext.getEnvironment(),
                    "eureka.client.serviceUrl.defaultZone=http://" + discoveryService.getContainerIpAddress() +
                            ":" + discoveryService.getMappedPort(8761) + "/eureka",
                    "storage-service.ribbon.servers=http://");
        }
    }

    @Test
    public void testGetApartments() throws Exception {
        // Given
        Apartment lviv = storageServiceClient.create(
                Apartment.builder()
                        .location("LVIV")
                        .mail("asfafs@asf.com")
                        .phone("02510505001")
                        .price(5225)
                        .realtorName("Bariga")
                        .sqft(55)
                        .build());

        // When
        mockMvc.perform(MockMvcRequestBuilders.get("/apartments"))
                .andDo(print());

        // Then
        //TODO storageServiceClient does not creating the records.
        // fallback is working with 0 paging
    }
}
