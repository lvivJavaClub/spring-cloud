package com.lohika.jclub;

import com.netflix.discovery.DiscoveryClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * @author Andriy Levchenko
 */
@RestController
@RequestMapping(path = "/rating")
public class RatingController {

  @Value("${rate}")
  private int rate;

  @PostMapping
  public BigDecimal calculateRating(@RequestBody Apartment ap) {
    //TODO: add more meaningful calculations
    return new BigDecimal((ap.getSqft()/ap.getPrice())*rate, new MathContext(3, RoundingMode.HALF_UP));
  }

}
