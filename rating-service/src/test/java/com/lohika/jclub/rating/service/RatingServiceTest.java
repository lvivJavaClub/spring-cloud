package com.lohika.jclub.rating.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RatingServiceTest {

  @Autowired
  private RatingService ratingService;

  @Test
  public void calculateRating() {
    Apartment apartment = Apartment.builder()
        .price(1.5678d)
        .sqft(2d)
        .location("location")
        .build();

    BigDecimal actual = ratingService.calculateRating(apartment);

    assertThat(actual, notNullValue());
    assertThat(actual, equalTo(new BigDecimal(128d, new MathContext(3, RoundingMode.HALF_UP))));
  }
}
