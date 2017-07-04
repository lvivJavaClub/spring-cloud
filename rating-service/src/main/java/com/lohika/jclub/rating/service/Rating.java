package com.lohika.jclub.rating.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
public class Rating {
  private BigDecimal rating;
}
