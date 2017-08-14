package com.lohika.jclub.integration;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.EnvironmentTestUtils;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.DockerComposeContainer;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntegrationTestApplication.class)
@ContextConfiguration(initializers = EntToEndIntegrationTest.Initializer.class)
public class EntToEndIntegrationTest {
  private static final int SLEEP = 5000;

  private static final String DISCOVERY_SERVER = "discovery-server";
  private static final int DISCOVERY_SERVER_PORT = 8761;

  private static final String API_GATEWAY_SERVICE = "API-GATEWAY-SERVICE";
  private static final int API_GATEWAY_SERVICE_PORT = 8090;

  private static final String COMPOSE = EntToEndIntegrationTest.class.getClassLoader()
      .getResource("./docker-compose.yml").getPath();

  @ClassRule
  public static DockerComposeContainer environment = new DockerComposeContainer(new File(COMPOSE))
      .withPull(false);


  @Autowired
  private RestTemplate restTemplate;

  @BeforeClass
  public static void init() throws InterruptedException {
    HttpClient client = HttpClientBuilder.create().build();
    String gateway = "http://localhost:" + DISCOVERY_SERVER_PORT + "/eureka/apps/" + API_GATEWAY_SERVICE;
    HttpGet httpGet = new HttpGet(gateway);

    HttpResponse httpResponse = null;
    do {
      Thread.sleep(SLEEP);
      try {
        httpResponse = client.execute(httpGet);
      } catch (IOException e) {
      }
    } while (httpResponse == null || httpResponse.getStatusLine().getStatusCode() != 200);
    Thread.sleep(SLEEP);
  }

  @Test
  public void checkIsStarted() throws IOException, InterruptedException {
    Map<String, Object> params = new HashMap() {{
      put("location", "location");
      put("price", 1500.5);
      put("sqft", 1.5);
      put("phone", "123");
      put("realtorName", "vas");
      put("mail", "mail@exem.comn");

    }};

    ResponseEntity<Map> create = restTemplate.exchange(
        "http://API-GATEWAY-SERVICE/api/realtor-service/storeApartments",
        HttpMethod.POST,
        new HttpEntity<>(params),
        Map.class
    );

    assertThat(create.getStatusCode().value(), is(200));
    String createdId = (String) create.getBody().get("id");
    assertThat(createdId, notNullValue());


    ResponseEntity<Map> get = restTemplate.exchange(
        "http://API-GATEWAY-SERVICE/api/clien-service/apartments",
        HttpMethod.GET,
        null,
        Map.class
    );

    assertThat(get.getStatusCode().value(), is(200));
    String id = (String) get.getBody().get("id");
    assertThat(createdId, is(id));
  }

  public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
      EnvironmentTestUtils.addEnvironment("testcontainers", configurableApplicationContext.getEnvironment(),
          "eureka.client.serviceUrl.defaultZone=http://localhost:" + DISCOVERY_SERVER_PORT + "/eureka"
      );
    }
  }
}
