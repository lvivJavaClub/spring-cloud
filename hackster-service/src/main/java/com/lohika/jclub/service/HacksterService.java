package com.lohika.jclub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

@RefreshScope
@Service
public class HacksterService {

  @Value("${maxAllowedApartmentsPerRealtor}")
  private int maxAllowedApartmentsPerRealtor;

  @Autowired
  private HacksterRepository hacksterRepository;

  /**
   * Check if current number exists in database and is the number of apartments related to this number
   * satisfy our limit. If number exists and didn't satisfy condition then this number will correspond
   * to hackster realtor otherwise no. If phone number doesn't exist then it will be added with apartment
   * amount = 1;
   *
   * @param phone Phone number of Realtor.
   * @return Is this phone number correspond to Hackster Realtor or no.
   */
  public boolean isHackster(String phone) {
    Hackster hackster = hacksterRepository.findByPhone(phone)
        .map(this::incrementApartmentCounterAndGet)
        .orElse(new Hackster(phone, 1));

    hacksterRepository.save(hackster);

    return hackster.getNumberOfApartments() > maxAllowedApartmentsPerRealtor;
  }

  private Hackster incrementApartmentCounterAndGet(Hackster hackster) {
    hackster.setNumberOfApartments(hackster.getNumberOfApartments() + 1);
    return hackster;
  }
}
