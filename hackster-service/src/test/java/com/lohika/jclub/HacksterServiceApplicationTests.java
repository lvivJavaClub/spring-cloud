package com.lohika.jclub;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HacksterServiceApplicationTests {
  @Value("${maxAllowedApartmentsPerRealtor}")
  private int maxAllowedApartmentsPerRealtor;

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void testIfNewPhoneNumberIsNotHakster() throws Exception {
    mockMvc.perform(get("/hackster/123123123"))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isOk())
        .andExpect(content().string("false"));
  }

  @Test
  public void testIfPhoneNumberBecomeAHakster() throws Exception {
    mockMvc.perform(get("/hackster/321321"))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isOk())
        .andExpect(content().string("false"));

    for (int i = 0; i < maxAllowedApartmentsPerRealtor - 1; i++) {
      mockMvc.perform(get("/hackster/321321"));
    }

    mockMvc.perform(get("/hackster/321321"))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isOk())
        .andExpect(content().string("true"));
  }
}
