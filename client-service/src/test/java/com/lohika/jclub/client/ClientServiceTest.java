package com.lohika.jclub.client;

import com.lohika.jclub.storage.client.StorageServiceClient;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
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
//            .waitingFor(new LogMessageWaitStrategy().withRegEx(".*Registered instance STORAGE-SERVICE.*\\s"))
            .waitingFor(new LogMessageWaitStrategy().withRegEx(".*Started EurekaServerApplication in.*\\s"))
            .withExposedPorts(8761);

    @Rule
    public GenericContainer storageService = new GenericContainer("storage-service:latest")
//            .withStartupTimeout(Duration.ofSeconds(20))
            .withExposedPorts(8091)
            .withEnv("eureka.client.serviceUrl.defaultZone", "http://" + discoveryService.getContainerIpAddress() + ":" + discoveryService.getMappedPort(8761) + "/eureka")
            .withEnv("eureka.instance.preferIpAddress", "true")
            .waitingFor(new LogMessageWaitStrategy().withRegEx(".*Started EurekaServerApplication in.*\\s"));
//            .waitingFor(new LogMessageWaitStrategy().withRegEx(".*DiscoveryClient_STORAGE-SERVICE.*registration status: 204.*\\s"));

//    @ClassRule
//    public static TestRule chain = RuleChain
//            .outerRule(discoveryService)
//            .around(storageService);

    @Autowired
    private StorageServiceClient storageServiceClient;

    @Autowired
    private MockMvc mockMvc;

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {

//            EnvironmentTestUtils.addEnvironment("testcontainers", configurableApplicationContext.getEnvironment(),
//                    "storage-service.ribbon.servers=http://" + storageService.getContainerIpAddress() + ":"
//                            + storageService.getMappedPort(8091) + "/"
//            );
            EnvironmentTestUtils.addEnvironment("testcontainers", configurableApplicationContext.getEnvironment(),
                    "eureka.client.serviceUrl.defaultZone=http://" + discoveryService.getContainerIpAddress() +
                            ":" + discoveryService.getMappedPort(8761) + "/eureka");


            EnvironmentTestUtils.addEnvironment("testcontainers", configurableApplicationContext.getEnvironment(),
                    "eureka.instance.preferIpAddress=true");

        }
    }

    @Test
    public void testGetApartments() throws Exception {
        // Given

        // When
        mockMvc.perform(MockMvcRequestBuilders.get("/apartments"))
                .andDo(print());

        // Then
    }
}
