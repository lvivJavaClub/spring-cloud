package com.lohika.jclub;

import lombok.*;

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
