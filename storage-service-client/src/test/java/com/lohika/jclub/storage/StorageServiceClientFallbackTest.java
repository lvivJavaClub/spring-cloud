package com.lohika.jclub.storage;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.PagedResources;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.LogMessageWaitStrategy;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StorageServiceClientTestApplication.class)
public class StorageServiceClientFallbackTest {

  @ClassRule
  public static GenericContainer storageService = new GenericContainer("storage-service:latest")
      .withExposedPorts(8091)
      .waitingFor(new LogMessageWaitStrategy().withRegEx(".*Started StorageServiceApplication in.*\\s"));

  @Autowired
  private StorageServiceClient storageServiceClient;

  @Autowired
  private StorageServiceClientFallback storageServiceClientFallback;

  private Apartment apartment = Apartment.builder()
      .location("location")
      .mail("mail")
      .phone("phone")
      .price(1.5d)
      .realtorName("realtorName")
      .sqft(1.3d)
      .build();

  @Test
  public void list() {
    PagedResources<Apartment> actual = storageServiceClient.list();

    assertThat(actual, notNullValue());
    assertThat(actual.getContent(), notNullValue());

    assertThat(actual.getContent(), hasSize(0));
  }

  @Test
  public void create() {
    Apartment actual = storageServiceClient.create(apartment);

    assertThat(actual, nullValue());
  }

  @Test
  public void get() {
    Apartment actual = storageServiceClient.get("not-existing-id");

    assertThat(actual, nullValue());
  }

  @Test
  public void update() {
    Apartment actual = storageServiceClient.update("not-existing-id", apartment);

    assertThat(actual, nullValue());
  }

  @Test
  public void delete() {
    storageServiceClient.delete("not-existing-id");
  }
}
