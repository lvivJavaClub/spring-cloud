package com.lohika.jclub.storage.service;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class ApartmentRecord {
  @Id
  private String id;
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
