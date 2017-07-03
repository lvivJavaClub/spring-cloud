package com.lohika.jclub.rating.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
@AllArgsConstructor
public class Apartment {
  @NonNull
  private String location;
  @NonNull
  private double price;
  @NonNull
  private double sqft;
}
