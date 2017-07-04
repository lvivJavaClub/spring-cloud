package com.lohika.jclub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/hackster")
public class HacksterController {

  @Autowired
  private HacksterService hacksterService;

  /**
   * Check if current number correspond to hackster Rrealtor.
   *
   * @param phone Realtor phone number.
   * @return Is current number correspond to hackster realtor.
   */
  @GetMapping(path = "/{phone}")
  public boolean isHackster(@PathVariable(name = "phone") String phone) {
    return hacksterService.isHackster(phone);
  }
}
