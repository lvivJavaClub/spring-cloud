package com.lohika.jclub.integration;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class GetWayIntegrationTest extends BaseIntegrationTest {


  private static final String STORE_APARTMENTS = "http://" + API_GATEWAY_SERVICE +
                                                 "/api/realtor-service/storeApartments";
  private static final String GET_APARTMENTS = "http://" + API_GATEWAY_SERVICE + "/api/client-service/apartments";

  @Autowired
  private RestTemplate restTemplate;

  @BeforeClass
  public static void setUpTests() throws InterruptedException {
    waitFor(API_GATEWAY_SERVICE);
  }

  @Test
  public void apartmentShouldBeAvailableAfterAdding() {
    Map<String, Object> params = new HashMap() {
      {
        put("location", "location");
        put("price", 1500.5);
        put("sqft", 1.5);
        put("phone", "123");
        put("realtorName", "vas");
        put("mail", "mail@exem.comn");

      }
    };

    ResponseEntity<Map> create = restTemplate.exchange(
        STORE_APARTMENTS,
        HttpMethod.POST,
        new HttpEntity<>(params),
        Map.class
    );

    ResponseEntity<Map> get = restTemplate.exchange(
        GET_APARTMENTS,
        HttpMethod.GET,
        null,
        Map.class
    );

    assertThat(get.getStatusCode().value(), is(200));
    assertThat(get.getBody().get("id"), notNullValue());
  }
}
