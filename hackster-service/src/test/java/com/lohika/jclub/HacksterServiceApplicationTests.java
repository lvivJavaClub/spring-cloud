package com.lohika.jclub;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HacksterServiceApplicationTests {
  @Autowired
  private HacksterService hacksterService;

  @Test
  public void testContextLoad() throws Exception {
    assertTrue(hacksterService != null);
  }
}
