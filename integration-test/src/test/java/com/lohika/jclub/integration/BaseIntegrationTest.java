package com.lohika.jclub.integration;

import lombok.extern.log4j.Log4j;

import com.lohika.jclub.storage.client.StorageServiceClient;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.EnvironmentTestUtils;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import javax.annotation.PostConstruct;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@Log4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntegrationTestApplication.class)
@ContextConfiguration(initializers = BaseIntegrationTest.Initializer.class)
public abstract class BaseIntegrationTest {

  protected static final String DSL_EXECUTOR_SERVICE = "DSL-EXECUTOR-SERVICE";
  protected static final String API_GATEWAY_SERVICE = "API-GATEWAY-SERVICE";

  private static final String DISCOVERY_SERVER = "localhost";
  private static final int DISCOVERY_SERVER_PORT = 8761;
  private static final int SLEEP = 5000;

  @Autowired
  protected StorageServiceClient storageServiceClient;

  @BeforeClass
  public static void init() {
    assertThat(EntToEndIntegrationTestSuite.environment, notNullValue());
  }

  @Before
  public void warmUp() {
    storageServiceClient.list();
  }

  @Before
  public void cleanUp() {
    storageServiceClient.list().getContent()
        .forEach(apartment -> storageServiceClient.delete(apartment.getId()));
  }

  public static void waitFor(String service) throws InterruptedException {
    HttpClient client = HttpClientBuilder.create().build();
    String gateway = "http://" + DISCOVERY_SERVER + ":" + DISCOVERY_SERVER_PORT + "/eureka/apps/" + service;
    HttpGet httpGet = new HttpGet(gateway);

    log.info("Wait for :" + service);
    HttpResponse httpResponse = null;
    do {
      Thread.sleep(SLEEP);
      try {
        httpResponse = client.execute(httpGet);
      } catch (IOException ignored) {
      }
    } while (httpResponse == null || httpResponse.getStatusLine().getStatusCode() != 200);
    Thread.sleep(SLEEP);
  }

  public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
      EnvironmentTestUtils.addEnvironment("testcontainers", configurableApplicationContext.getEnvironment(),
          "eureka.client.serviceUrl.defaultZone=http://" + DISCOVERY_SERVER + ":" + DISCOVERY_SERVER_PORT + "/eureka"
      );
    }
  }
}
