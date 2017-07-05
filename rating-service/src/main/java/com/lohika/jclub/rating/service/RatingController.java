package com.lohika.jclub.rating.service;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/rating")
@RequiredArgsConstructor
public class RatingController {

  private final RatingService ratingService;

  @PostMapping
  public Rating calculateRating(@RequestBody Apartment apartment) {
    return new Rating(ratingService.calculateRating(apartment));
  }
}
