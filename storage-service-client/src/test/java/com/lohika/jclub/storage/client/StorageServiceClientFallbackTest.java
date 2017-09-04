package com.lohika.jclub.storage.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.EnvironmentTestUtils;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.hateoas.PagedResources;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StorageServiceClientTestApplication.class)
@ContextConfiguration(initializers = StorageServiceClientFallbackTest.Initializer.class)
public class StorageServiceClientFallbackTest {

  @Autowired
  private StorageServiceClient storageServiceClient;

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

  public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {

      EnvironmentTestUtils.addEnvironment("testcontainers", configurableApplicationContext.getEnvironment(),
          "storage-service.ribbon.servers=http://not-existing-url/"
      );
    }
  }
}
