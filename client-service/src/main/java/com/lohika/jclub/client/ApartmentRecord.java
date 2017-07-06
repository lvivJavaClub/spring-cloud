package com.lohika.jclub.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class ApartmentRecord {
  @NonNull
  private String location;
  @NonNull
  private double price;
  @NonNull
  private double sqft;
  @NonNull
  private String phone;
  @NonNull
  private String realtorName;
  @NonNull
  private String mail;
}
