package com.lohika.jclub.dsl.core

import com.lohika.jclub.storage.client.Apartment
import groovy.transform.builder.Builder
import groovy.transform.builder.SimpleStrategy

@Builder(prefix = "", builderStrategy = SimpleStrategy.class)
class ApartmentDsl {
  String location
  double price
  double sqft
  String phone
  String realtorName
  String mail

  def toEntity() {
    Apartment.builder()
        .location(location)
        .price(price)
        .sqft(sqft)
        .phone(phone)
        .realtorName(realtorName)
        .mail(mail)
        .build()
  }
}
