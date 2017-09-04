package com.lohika.jclub.rating.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.EnvironmentTestUtils;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RatingServiceClientTestApplication.class)
@ContextConfiguration(initializers = RatingServiceClientFallbackTest.Initializer.class)
public class RatingServiceClientFallbackTest {

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

    assertThat(actual, nullValue());
  }

  public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {

      EnvironmentTestUtils.addEnvironment("testcontainers", configurableApplicationContext.getEnvironment(),
          "rating-service.ribbon.servers=http://not-existing-url/"
      );
    }
  }
}
