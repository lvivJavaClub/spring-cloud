package com.lohika.jclub.rating.client;

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

import java.math.BigDecimal;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RatingServiceClientTestApplication.class)
@ContextConfiguration(initializers = RatingServiceClientTest.Initializer.class)
public class RatingServiceClientTest {

  @ClassRule
  public static GenericContainer RatingService = new GenericContainer("rating-service:latest")
      .withExposedPorts(8081)
      .withEnv("spring.cloud.config.fail-fast", "false")
      .withEnv("spring.cloud.config.discovery.enabled", "false")
      .withEnv("rate", "100")
      .withEnv("WAITING_FOR_DEPENDENCE", "false")
      .waitingFor(new LogMessageWaitStrategy().withRegEx(".*Started RatingServiceApplication in.*\\s"));

  @Autowired
  private RatingServiceClient ratingServiceClient;

  private Apartment apartment = Apartment.builder()
      .location("location")
      .price(1.5d)
      .sqft(1.3d)
      .build();


  @Test
  public void getRating() {
    Rating actual = ratingServiceClient.getRating(apartment);

    assertThat(actual, notNullValue());
    assertThat(actual.getRating(), notNullValue());
    assertThat(actual.getRating(), equalTo(BigDecimal.valueOf(86.7d)));
  }

  public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {

      EnvironmentTestUtils.addEnvironment("testcontainers", configurableApplicationContext.getEnvironment(),
          "rating-service.ribbon.servers=http://" + RatingService.getContainerIpAddress() + ":"
          + RatingService.getMappedPort(8081) + "/"
      );
    }
  }
}
