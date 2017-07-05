package com.lohika.jclub.hackster.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(
    value = "hackster-service",
    url = "${hackster-service.ribbon.servers:}",
    decode404 = true,
    fallback = HacksterServiceClientFallback.class)
@RequestMapping("/hackster")
public interface HacksterServiceClient {

  @GetMapping(value = "/{phone}")
  Boolean isHackster(@PathVariable("phone") String phone);
}
