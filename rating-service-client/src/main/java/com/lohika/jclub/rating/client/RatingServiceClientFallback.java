package com.lohika.jclub.rating.client;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RatingServiceClientFallback implements RatingServiceClient {

  @Override
  public Rating getRating(Apartment apartment) {
    log.error("Can get rating");
    return null;
  }
}
