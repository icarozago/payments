package com.icaro.payments.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.springframework.http.MediaType;

import com.icaro.payments.BasicApplicationIntegrationTest;
import com.icaro.payments.dto.AccountDTO;
import com.icaro.payments.dto.PersonDTO;
import com.icaro.payments.utils.TestUtils;

@TestMethodOrder(OrderAnnotation.class)
class AccountControllerTest extends BasicApplicationIntegrationTest {
	
	private AccountDTO accountDTO;
	
	private PersonDTO personDTO;
	
	public static final String ACCOUNT_URL = "/accounts";
	
	public AccountControllerTest() {
		this.personDTO = TestUtils.getPerson();
		
		this.accountDTO = TestUtils.getAccount();
	}
	
	@Test
	@Order(1)
	void createNewAccountWithSuccess() throws Exception {
		mockMvc.perform(post(PersonControllerTest.PEOPLE_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(personDTO)))
				.andExpect(status().isCreated());
		
		mockMvc.perform(post(ACCOUNT_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(accountDTO)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.number", is(accountDTO.getNumber())));
	}
	
	@Test
	void createNewAccountWithoutBody() throws Exception {
		mockMvc.perform(post(ACCOUNT_URL)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	void createNewAccountWithoutAmount() throws Exception {
		accountDTO.setAmount(null);
		mockMvc.perform(post(ACCOUNT_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(accountDTO)))
				.andExpect(status().isBadRequest())
				.andExpect(status().reason(bundle.getString("account.amount.required")));
	}
	
	@Test
	@Order(4)
	void createNewAccountWithoutPersonId() throws Exception {
		accountDTO.setPersonId(null);
		mockMvc.perform(post(ACCOUNT_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(accountDTO)))
				.andExpect(status().isBadRequest())
				.andExpect(status().reason(bundle.getString("account.personId.required")));
	}
	
	@Test
	@Order(5)
	void createNewAccountWithInvalidNumber() throws Exception {
		accountDTO.setNumber(-1L);
		mockMvc.perform(post(ACCOUNT_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(accountDTO)))
				.andExpect(status().isBadRequest())
				.andExpect(status().reason(bundle.getString("account.number.invalid")));
	}
	
	@Test
	void updateAccountWithoutBody() throws Exception {
		mockMvc.perform(put(ACCOUNT_URL + "/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	@Order(3)
	void updateAccountWithSuccess() throws Exception {
		accountDTO.setAmount(new BigDecimal(200));
		
		mockMvc.perform(put(ACCOUNT_URL + "/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(accountDTO)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.amount", is(200)));
	}
	
	@Test
	void findAllAccountsWithSuccess() throws Exception {
		mockMvc.perform(get(ACCOUNT_URL)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)));
	}

	@Test
	@Order(2)
	void findAccountByIdWithSuccess() throws Exception {
		mockMvc.perform(get(ACCOUNT_URL + "/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.number", is(accountDTO.getNumber())));
	}
}
