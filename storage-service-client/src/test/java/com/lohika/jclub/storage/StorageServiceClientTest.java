package com.lohika.jclub.storage;

import org.junit.ClassRule;
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
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.LogMessageWaitStrategy;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StorageServiceClientTestApplication.class)
@ContextConfiguration(initializers = StorageServiceClientTest.Initializer.class)
public class StorageServiceClientTest {

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
    Apartment created1 = storageServiceClient.create(apartment.toBuilder().phone("1").build());
    Apartment created2 = storageServiceClient.create(apartment.toBuilder().phone("2").build());
    Apartment created3 = storageServiceClient.create(apartment.toBuilder().phone("3").build());

    PagedResources<Apartment> actual = storageServiceClient.list();

    assertThat(actual, notNullValue());
    assertThat(actual.getContent(), notNullValue());

    assertThat(actual.getContent(), hasItem(created1));
    assertThat(actual.getContent(), hasItem(created2));
    assertThat(actual.getContent(), hasItem(created3));
  }

  @Test
  public void create() {
    Apartment actual = storageServiceClient.create(apartment);

    assertThat(actual, notNullValue());
    assertThat(actual.getId(), notNullValue());
  }

  @Test
  public void get() {
    Apartment created = storageServiceClient.create(apartment);

    Apartment actual = storageServiceClient.get(created.getId());

    assertThat(actual, notNullValue());
    assertThat(actual.getId(), notNullValue());
  }

  @Test
  public void getNotExisting() {
    Apartment actual = storageServiceClient.get("not-existing-id");

    assertThat(actual, nullValue());
  }

  @Test
  public void update() {
    Apartment created = storageServiceClient.create(apartment);
    created.setMail("new mail");

    Apartment actual = storageServiceClient.update(created.getId(), created);

    assertThat(actual, notNullValue());
    assertThat(actual.getId(), notNullValue());
    assertThat(actual.getMail(), is("new mail"));
  }

  @Test
  public void delete() {
    Apartment created = storageServiceClient.create(apartment);
    created.setMail("new mail");

    storageServiceClient.delete(created.getId());

    Apartment actual = storageServiceClient.get(created.getId());
    assertThat(actual, nullValue());
  }

  public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {

      EnvironmentTestUtils.addEnvironment("testcontainers", configurableApplicationContext.getEnvironment(),
          "storage-service.ribbon.servers=http://" + storageService.getContainerIpAddress() + ":"
          + storageService.getMappedPort(8091) + "/"
      );
    }
  }
}
