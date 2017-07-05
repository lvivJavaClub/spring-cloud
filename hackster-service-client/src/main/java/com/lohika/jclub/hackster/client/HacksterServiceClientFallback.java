package com.lohika.jclub.hackster.client;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HacksterServiceClientFallback implements HacksterServiceClient {

  @Override
  public Boolean isHackster(String phone) {
    log.error("Can't check is hackster");
    return true;
  }
}
