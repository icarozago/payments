package com.icaro.payments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.Matchers.is;

import org.junit.ClassRule;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.icaro.payments.containers.CustomPostgreSQLContainer;
import com.icaro.payments.containers.UserRepositoryTCIntegrationTest;
import com.icaro.payments.controllers.PersonController;
import com.icaro.payments.dto.PersonDTO;
import com.icaro.payments.services.impl.PersonService;


@RunWith(SpringRunner.class)
@WebMvcTest(controllers = PersonController.class)
class PersonControllerTest {

	private MockMvc mockMvc;
	
	private ObjectMapper objectMapper;
	
	private PersonDTO personDTO;
	
	private static final String PEOPLE_URL = "/people";
	
	@ClassRule
    public static PostgreSQLContainer postgreSQLContainer = CustomPostgreSQLContainer.getInstance();
	
	@MockBean
	private PersonService service;
	
	@Autowired
	public PersonControllerTest(MockMvc mockMvc, ObjectMapper objectMapper) {
		this.mockMvc = mockMvc;
		this.objectMapper = objectMapper;
		this.personDTO = new PersonDTO();
		personDTO.setName("Novo Nome");
		personDTO.setCpf("12345678915");
		personDTO.setEmail("naotenho@gmail.com");
		
		postgreSQLContainer.start();
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
