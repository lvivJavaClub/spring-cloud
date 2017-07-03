package com.lohika.jclub.rating.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(
    value = "rating-service",
    url = "${rating-service.ribbon.servers:}",
    decode404 = true,
    fallback = RatingServiceClientFallback.class)
@RequestMapping("/rating")
public interface RatingServiceClient {

  @PostMapping
  Rating getRating(Apartment apartment);
}
