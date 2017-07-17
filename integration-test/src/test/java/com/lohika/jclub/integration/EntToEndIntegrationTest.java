package com.lohika.jclub.integration;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.testcontainers.containers.DockerComposeContainer;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class EntToEndIntegrationTest {

  private static final int SLEEP = 5000;

  private static final String API_GATEWAY_SERVICE = "api-gateway-service_1";
  private static final int API_GATEWAY_SERVICE_PORT = 8090;

  private static final String STORAGE_SERVICE = "storage-service_1";
  private static final int STORAGE_SERVICE_PORT = 8091;

  private static final String COMPOSE = EntToEndIntegrationTest.class.getClassLoader()
      .getResource("./docker-compose.yml").getPath();

  @ClassRule
  public static DockerComposeContainer environment = new DockerComposeContainer(new File(COMPOSE))
      .withExposedService(API_GATEWAY_SERVICE, API_GATEWAY_SERVICE_PORT)
      .withExposedService(STORAGE_SERVICE, STORAGE_SERVICE_PORT)
      .withPull(false);


  @BeforeClass
  public static void init() throws InterruptedException {
    HttpClient client = HttpClientBuilder.create().build();
    String gateway = "http://localhost:" + API_GATEWAY_SERVICE_PORT + "/info";
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
    HttpClient client = HttpClientBuilder.create().build();

    String gateway = "http://localhost:" + API_GATEWAY_SERVICE_PORT + "/api/realtor-service/storeApartments";
    HttpPost httpPost = new HttpPost(gateway);
    httpPost.addHeader("Content-Type", "application/json");
    httpPost.setEntity(new StringEntity("{\n"
                                        + "\"location\" : \"location\",\n"
                                        + "\"price\" : 1500.5,\n"
                                        + "\"sqft\" : 1.5,\n"
                                        + "\"phone\" : \"123\",\n"
                                        + "\"realtorName\" : \"vas\",\n"
                                        + "\"mail\" : \"mail@exem.com\"\n"
                                        + "}"));
    HttpResponse httpResponse1 = client.execute(httpPost);
    assertThat(httpResponse1.getStatusLine().getStatusCode(), is(200));

    Thread.sleep(SLEEP);

    String storage = "http://localhost:" + STORAGE_SERVICE_PORT + "/apartments/1";
    HttpGet httpGet = new HttpGet(storage);
    HttpResponse httpResponse2 = client.execute(httpGet);
    assertThat(httpResponse2.getStatusLine().getStatusCode(), is(200));
  }
}
