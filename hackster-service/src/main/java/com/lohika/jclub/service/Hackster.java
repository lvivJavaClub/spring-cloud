package com.lohika.jclub.service;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class Hackster {
  @Id
  @GeneratedValue
  private long id;

  @NonNull
  private String phone;

  @NonNull
  private int numberOfApartments;
}
