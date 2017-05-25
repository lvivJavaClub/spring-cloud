package com.lohika.jclub;

import lombok.Data;
import lombok.NonNull;

/**
 * @author Andriy Levchenko
 */
@Data
public class Apartment {
  @NonNull
  private String location;
  @NonNull
  private double price;
  @NonNull
  private double sqft;
}
