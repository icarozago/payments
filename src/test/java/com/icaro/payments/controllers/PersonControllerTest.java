package com.icaro.payments.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import com.icaro.payments.PaymentApplicationTest;
import com.icaro.payments.dto.PersonDTO;

class PersonControllerTest extends PaymentApplicationTest {

	private PersonDTO personDTO;
	
	private static final String PEOPLE_URL = "/people";
	
	public PersonControllerTest() {
		this.personDTO = new PersonDTO();
		personDTO.setName("Novo Nome");
		personDTO.setCpf("12345678915");
		personDTO.setEmail("naotenho@gmail.com");
	}
	
	@Test
	void createNewPersonWithSuccess() throws Exception {
		MvcResult andReturn = mockMvc.perform(post(PEOPLE_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(personDTO)))
				.andExpect(status().isCreated())
				.andReturn();
//				.andExpect(jsonPath("$.name", is(personDTO.getName())));
		
		String a = andReturn.getResponse().getContentAsString();
		andReturn.getResponse().getContentAsString();
	}
	
	@Test
	void createNewPersonWithoutBody() throws Exception {
		mockMvc.perform(post(PEOPLE_URL)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	void updatePersonWithoutBody() throws Exception {
		mockMvc.perform(put(PEOPLE_URL + "/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	void updatePersonWithSuccess() throws Exception {
		mockMvc.perform(post(PEOPLE_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(personDTO)))
				.andExpect(status().isCreated());
		
		mockMvc.perform(put(PEOPLE_URL + "/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	void findAllPeopleWithSuccess() throws Exception {
		mockMvc.perform(get(PEOPLE_URL)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	void findPersonByIdWithSuccess() throws Exception {
		mockMvc.perform(get(PEOPLE_URL + "/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

}
