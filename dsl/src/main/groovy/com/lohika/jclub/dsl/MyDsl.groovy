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

  MyDsl(RatingServiceClient ratingServiceClient, StorageServiceClient storageServiceClient) {
    this.storageServiceClient = storageServiceClient
    this.ratingServiceClient = ratingServiceClient
  }
}
