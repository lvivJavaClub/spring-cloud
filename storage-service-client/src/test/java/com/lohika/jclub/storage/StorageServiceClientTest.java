package com.lohika.jclub.storage;

import com.github.dockerjava.api.command.CreateContainerCmd;

import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.PagedResources;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.Wait;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StorageServiceClientTestApplication.class)
public class StorageServiceClientTest {

  @ClassRule
  public static GenericContainer storageService = new GenericContainer("storage-service:latest")
      .withExposedPorts(8091)
      .withCreateContainerCmdModifier(
          cmd -> ((CreateContainerCmd) cmd)
              .withPublishAllPorts(true)
              .withName("storage-service"))
      .waitingFor(Wait.forListeningPort());

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
}
