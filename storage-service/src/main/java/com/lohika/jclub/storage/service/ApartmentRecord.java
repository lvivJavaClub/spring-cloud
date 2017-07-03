package com.lohika.jclub.storage.service;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class ApartmentRecord {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
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
