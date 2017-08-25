package com.lohika.jclub.integration;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class DslIntegrationTest extends BaseIntegrationTest {
  private static final String SIMPLE_DSL_EXECUTE = "http://" + DSL_EXECUTOR_SERVICE + "/dsl/end-to-end";
  private static final String GET_APARTMENTS = "http://" + API_GATEWAY_SERVICE + "/api/client-service/apartments";

  @Autowired
  private RestTemplate restTemplate;

  @BeforeClass
  public static void setUpTests() throws InterruptedException {
    waitFor(API_GATEWAY_SERVICE);
    waitFor(DSL_EXECUTOR_SERVICE);
  }

  @Test
  public void apartmentShouldBeAvailableAfterDslExecution() {
    ResponseEntity<Map> dslResult = restTemplate.exchange(
        SIMPLE_DSL_EXECUTE,
        HttpMethod.GET,
        null,
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
