package com.lohika.jclub.hackster.client;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.EnvironmentTestUtils;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.LogMessageWaitStrategy;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HacksterServiceClientTestApplication.class)
@ContextConfiguration(initializers = HacksterServiceClientTest.Initializer.class)
public class HacksterServiceClientTest {
  private static final int MAX_ALLOWED_APARTMENTS_PER_REALTOR = 4;

  @ClassRule
  public static GenericContainer HacksterService = new GenericContainer("hackster-service:latest")
      .withExposedPorts(8082)
      .withEnv("maxAllowedApartmentsPerRealtor", Integer.toString(MAX_ALLOWED_APARTMENTS_PER_REALTOR))
      .withEnv("spring.cloud.config.discovery.enabled", "false")
      .withEnv("spring.cloud.config.fail-fast", "false")
      .withEnv("WAITING_FOR_DEPENDENCE", "false")
      .waitingFor(new LogMessageWaitStrategy().withRegEx(".*Started HacksterServiceApplication in.*\\s"));

  @Autowired
  private HacksterServiceClient hacksterServiceClient;

  @Test
  public void testIfNewNumberIsNotHackster() {
    boolean isHackster = hacksterServiceClient.isHackster("123123123");

    assertThat(isHackster, equalTo(false));
  }

  @Test
  public void testIfNumberBecomeAHackster() {
    for (int i = 0; i < MAX_ALLOWED_APARTMENTS_PER_REALTOR; i++) {
      hacksterServiceClient.isHackster("321321312");
    }

    boolean isHacksterTrue = hacksterServiceClient.isHackster("321321312");

    assertThat(isHacksterTrue, equalTo(true));
  }

  public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {

      EnvironmentTestUtils.addEnvironment("testcontainers", configurableApplicationContext.getEnvironment(),
          "hackster-service.ribbon.servers=http://" + HacksterService.getContainerIpAddress() + ":"
              + HacksterService.getMappedPort(8082) + "/"
      );
    }
  }
}
