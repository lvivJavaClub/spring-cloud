package com.lohika.jclub.integration;

import com.lohika.jclub.storage.client.Apartment;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

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
    /* GIVEN */
    final String email = "mail@domen.com";
    Map params = new HashMap() {
      {
        put("location", "location");
        put("price", 1500.5);
        put("sqft", 1.5);
        put("phone", "123");
        put("realtorName", "realtorName");
        put("mail", email);

      }
    };

    /* WHEN */
    ResponseEntity<Map> create = restTemplate.exchange(
        STORE_APARTMENTS,
        HttpMethod.POST,
        new HttpEntity<>(params),
        Map.class
    );

    /* THEN */
    assertThat(create, notNullValue());
    assertThat(create.getStatusCode().value(), is(200));
    String createdId = (String) create.getBody().get("id");
    assertThat(createdId, notNullValue());

    /* WHEN */
    Collection<Apartment> apartments = storageServiceClient.list().getContent();

    /* THEN */
    assertThat(apartments, notNullValue());
    assertThat(apartments, hasSize(1));

    /* WHEN */
    ResponseEntity<Map> get = restTemplate.exchange(
        GET_APARTMENTS,
        HttpMethod.GET,
        null,
        Map.class
    );

    /* THEN */
    assertThat(get.getStatusCode().value(), is(200));
  }
}
