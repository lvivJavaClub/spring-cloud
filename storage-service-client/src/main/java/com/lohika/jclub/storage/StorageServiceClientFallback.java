package com.lohika.jclub.storage;

import lombok.extern.slf4j.Slf4j;

import org.springframework.hateoas.PagedResources;

@Slf4j
public class StorageServiceClientFallback implements StorageServiceClient {

  @Override
  public PagedResources<Apartment> list() {
    log.error("Can not get list");
    return null;
  }

  @Override
  public Apartment create(Apartment apartment) {
    log.error("Can not create");
    return null;
  }

  @Override
  public Apartment get(String id) {
    return null;
  }

  @Override
  public Apartment update(String id, Apartment apartment) {
    log.error("Can not update");
    return null;
  }

  @Override
  public void delete(String id) {
    log.error("Can not delete");
  }
}
