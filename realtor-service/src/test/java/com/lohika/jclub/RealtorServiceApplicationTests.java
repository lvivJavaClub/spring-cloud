package com.lohika.jclub;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import javax.ws.rs.core.MediaType;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RealtorServiceApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void contextLoads() throws Exception {
		ApartmentRecord apartmentRecord = ApartmentRecord.builder()
				.phone("123")
				.realtorName("Anna Realtor")
				.sqft(44)
				.price(100)
				.location("Lviv").build();

		mockMvc.perform(MockMvcRequestBuilders
					.post("/apartments")
					.contentType(MediaType.APPLICATION_JSON)
					.content(new ObjectMapper().writeValueAsBytes(apartmentRecord)))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk());
	}
}
