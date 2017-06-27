package com.lohika.jclub;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * @author Andriy Levchenko
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Apartment {
  @NonNull
  private String location;
  @NonNull
  private double price;
  @NonNull
  private double sqft;
}
