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

  def apartments = []

  MyDsl(RatingServiceClient ratingServiceClient, StorageServiceClient storageServiceClient) {
    this.storageServiceClient = storageServiceClient
    this.ratingServiceClient = ratingServiceClient
  }

  def rating(String location, double myPrice, double mySqft) {
    Apartment apartment = Apartment.builder()
        .sqft(mySqft)
        .location(location)
        .price(myPrice)
        .build()

    ratingServiceClient.getRating(apartment).rating
  }

  def apartment(Closure closure) {
    ApartmentDsl apartment = new ApartmentDsl()
    closure.delegate = apartment
    closure()

    storageServiceClient.create(apartment.toEntity())
    apartments.add(apartment)
  }

  def apartment(String location, Closure closure) {
    ApartmentDsl apartment = new ApartmentDsl()
    apartment.location = location

    closure.delegate = apartment
    closure()

    storageServiceClient.create(apartment.toEntity())
    apartments.add(apartment)
  }
}
