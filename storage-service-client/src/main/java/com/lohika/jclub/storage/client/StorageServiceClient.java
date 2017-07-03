package com.lohika.jclub.storage.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.hateoas.PagedResources;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(
    value = "storage-service",
    url = "${storage-service.ribbon.servers:}",
    decode404 = true,
    fallback = StorageServiceClientFallback.class)
@RequestMapping("/apartments")
public interface StorageServiceClient {

  @GetMapping
  PagedResources<Apartment> list();

  @PostMapping
  Apartment create(Apartment apartment);

  @GetMapping("/{id}")
  Apartment get(@PathVariable("id") String id);

  @PutMapping("/{id}")
  Apartment update(@PathVariable("id") String id, Apartment apartment);

  @DeleteMapping("/{id}")
  void delete(@PathVariable("id") String id);
}
