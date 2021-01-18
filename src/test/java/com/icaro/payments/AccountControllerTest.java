package com.icaro.payments;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.icaro.payments.controllers.AccountController;
import com.icaro.payments.dto.AccountDTO;
import com.icaro.payments.services.impl.PersonService;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = AccountController.class)
class AccountControllerTest {
	
	private MockMvc mockMvc;
	
	private ObjectMapper objectMapper;
	
	private AccountDTO accountDTO;
	
	private static final String ACCOUNT_URL = "/accounts";
	
	@MockBean
	private PersonService service;
	
	@Autowired
	public AccountControllerTest(MockMvc mockMvc, ObjectMapper objectMapper) {
		this.mockMvc = mockMvc;
		this.objectMapper = objectMapper;
		this.accountDTO = new AccountDTO();
		accountDTO.setAmount(new BigDecimal(100));
		accountDTO.setNumber(12345678915L);
		accountDTO.setPersonId(1L);
	}
	
	@Test
	void createNewAccountWithSuccess() throws Exception {
		mockMvc.perform(post(ACCOUNT_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(accountDTO)))
				.andExpect(status().isCreated());
//				.andExpect(jsonPath("$.number", is(accountDTO.getNumber())));
	}
	
	@Test
	void createNewAccountWithoutBody() throws Exception {
		mockMvc.perform(post(ACCOUNT_URL)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	void updateAccountWithoutBody() throws Exception {
		mockMvc.perform(put(ACCOUNT_URL + "/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	void updateAccountWithSuccess() throws Exception {
		mockMvc.perform(post(ACCOUNT_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(accountDTO)))
				.andExpect(status().isCreated());
		
		mockMvc.perform(put(ACCOUNT_URL + "/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	void findAllAccountsWithSuccess() throws Exception {
		mockMvc.perform(get(ACCOUNT_URL)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	void findAccountByIdWithSuccess() throws Exception {
		mockMvc.perform(get(ACCOUNT_URL + "/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
}
