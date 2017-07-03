package com.lohika.jclub.rating.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * @author Andriy Levchenko
 */
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
