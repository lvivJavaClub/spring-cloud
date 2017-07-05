package com.lohika.jclub.hackster.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HacksterServiceClientTestApplication.class)
public class HacksterServiceClientApplicationTests {

  @Autowired
  private HacksterServiceClientFallback hacksterServiceClientFallback;

  @Test
  public void contextLoads() {
  }
}
