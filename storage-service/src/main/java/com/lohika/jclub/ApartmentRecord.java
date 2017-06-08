package com.lohika.jclub;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
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
  @Id
  private String mail;
}
