package com.lohika.jclub.dsl

import com.lohika.jclub.rating.client.Apartment
import com.lohika.jclub.rating.client.RatingServiceClient
import com.lohika.jclub.storage.client.StorageServiceClient
import groovy.transform.ToString
import groovy.util.logging.Log

@Log
@ToString(excludes = ["ratingServiceClient", "storageServiceClient"])
class MyDsl {

  private RatingServiceClient ratingServiceClient
  private StorageServiceClient storageServiceClient

  def list = []

  MyDsl(RatingServiceClient ratingServiceClient, StorageServiceClient storageServiceClient) {
    this.storageServiceClient = storageServiceClient
    this.ratingServiceClient = ratingServiceClient
  }

  def rating (location, myPrice, mySqft) {
    Apartment a =  Apartment.builder()
    .sqft (mySqft)
    .location (location)
    .price(myPrice)
    .build()

    ratingServiceClient.getRating(a).rating
  }

  def apartment (Closure closure) {
    ApartmentDsl a = new ApartmentDsl()
    closure.delegate = a
    closure()

    storageServiceClient.create(a.toEntity())
    list.add(a)
  }

  def apartment (String location, Closure closure) {
    ApartmentDsl a = new ApartmentDsl()
    a.location = location
    closure.delegate = a
    closure()

    storageServiceClient.create(a.toEntity())
    list.add(a)
  }
}
