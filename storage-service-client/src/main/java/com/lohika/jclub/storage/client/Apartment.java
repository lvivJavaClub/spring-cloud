package com.lohika.jclub.storage.client;

import org.springframework.hateoas.ResourceSupport;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Apartment {
  private String id;
  private String location;
  private double price;
  private double sqft;
  private String phone;
  private String realtorName;
  private String mail;
}
