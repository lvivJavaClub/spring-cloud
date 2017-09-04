package com.lohika.jclub.dsl.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.EnvironmentTestUtils;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(initializers = DslServiceApplicationTests.Initializer.class)
public class DslServiceApplicationTests {

  @Test
  public void testContextLoad() {
  }

  public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {

      EnvironmentTestUtils.addEnvironment("testcontainers", configurableApplicationContext.getEnvironment(),
          "storage-service.ribbon.servers=http://not-existing-url/",
          "rating-service.ribbon.servers=http://not-existing-url/",
          "hackster-service.ribbon.servers=http://not-existing-url/"
      );
    }
  }
}
