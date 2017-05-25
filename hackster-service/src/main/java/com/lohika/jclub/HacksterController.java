package com.lohika.jclub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Andriy Levchenko
 */
@RestController
@RequestMapping(path = "/hackster")
public class HacksterController {

  @Autowired
  private HacksterRepository hacksterRepository;

  @GetMapping(path = "/{phone}")
  public boolean isHackster(@PathVariable(name = "phone") String phone) {
    return hacksterRepository.findByPhone(phone).isPresent();
  }
}
