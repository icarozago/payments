package com.icaro.payments.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import com.icaro.payments.BasicApplicationIntegrationTest;
import com.icaro.payments.dto.PersonDTO;
import com.icaro.payments.utils.TestUtils;

@TestMethodOrder(OrderAnnotation.class)
class PersonControllerTest extends BasicApplicationIntegrationTest {

	private PersonDTO personDTO;
	
	public static final String PEOPLE_URL = "/people";
	
	public PersonControllerTest() {
		this.personDTO = TestUtils.getPerson();
	}
	
	@Test
	@Order(1)
	void createNewPersonWithSuccess() throws Exception {
		mockMvc.perform(post(PEOPLE_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(personDTO)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.name", is(personDTO.getName())));
	}
	
	@Test
	void createNewPersonWithoutBody() throws Exception {
		mockMvc.perform(post(PEOPLE_URL)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	void createNewPersonWithEmptyName() throws Exception {
		personDTO.setName("");
		mockMvc.perform(post(PEOPLE_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(personDTO)))
				.andExpect(status().isBadRequest())
				.andExpect(status().reason(bundle.getString("person.name.required")));
	}
	
	@Test
	void createNewPersonWithEmptyCpf() throws Exception {
		personDTO.setCpf("");
		mockMvc.perform(post(PEOPLE_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(personDTO)))
				.andExpect(status().isBadRequest())
				.andExpect(status().reason(bundle.getString("person.cpf.required")));
	}
	
	@Test
	@Order(3)
	void createNewPersonWithEmptyEmail() throws Exception {
		personDTO.setEmail("");
		mockMvc.perform(post(PEOPLE_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(personDTO)))
				.andExpect(status().isBadRequest())
				.andExpect(status().reason(bundle.getString("person.email.required")));
	}
	
	@Test
	void updatePersonWithoutBody() throws Exception {
		mockMvc.perform(put(PEOPLE_URL + "/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	@Order(2)
	void updatePersonWithSuccess() throws Exception {
		String newName = "OTHER NAME";
		personDTO.setName(newName);
		
		mockMvc.perform(put(PEOPLE_URL + "/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(personDTO)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is(newName)));
	}
	
	@Test
	void findPersonByIdWithSuccess() throws Exception {
		mockMvc.perform(get(PEOPLE_URL + "/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is(personDTO.getName())));
	}
	
	@Test
	void findAllPeopleWithSuccess() throws Exception {
		mockMvc.perform(get(PEOPLE_URL)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)));
	}
	
	

}
