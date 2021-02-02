package com.icaro.payments.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.math.BigDecimal;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import com.icaro.payments.BasicApplicationIntegrationTest;
import com.icaro.payments.dto.AccountDTO;
import com.icaro.payments.dto.PaymentDTO;
import com.icaro.payments.dto.PersonDTO;
import com.icaro.payments.utils.TestUtils;

@TestMethodOrder(OrderAnnotation.class)
class PaymentControllerTest extends BasicApplicationIntegrationTest {
	
	private PaymentDTO paymentDTO;
	
	private PersonDTO personDTO;
	
	private AccountDTO accountDTO;
	
	private static final String PAYMENT_URL = "/payments";
	
	public PaymentControllerTest() {
		this.personDTO = TestUtils.getPerson();
		
		this.accountDTO = TestUtils.getAccount();
		
		this.paymentDTO = TestUtils.getPayment();
	}
	
	@Test
	@Order(1)
	void createNewPaymentWithSuccess() throws Exception {
		mockMvc.perform(post(PersonControllerTest.PEOPLE_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(personDTO)))
				.andExpect(status().isCreated());
		
		mockMvc.perform(post(AccountControllerTest.ACCOUNT_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(accountDTO)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.number", is(accountDTO.getNumber())));
		
		mockMvc.perform(post(PAYMENT_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(paymentDTO)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.value", is(paymentDTO.getValue().intValue())));
	}
	
	@Test
	void createNewPaymentWithoutBody() throws Exception {
		mockMvc.perform(post(PAYMENT_URL)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	void updatePaymentWithoutBody() throws Exception {
		mockMvc.perform(put(PAYMENT_URL + "/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	void updatePaymentWithSuccess() throws Exception {
		paymentDTO.setValue(new BigDecimal(70));
		
		mockMvc.perform(put(PAYMENT_URL + "/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(paymentDTO)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.value", is(70)));
		
		mockMvc.perform(get(AccountControllerTest.ACCOUNT_URL + "/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.amount", is(30.0)));
	}
	
	@Test
	void findAllPaymentsWithSuccess() throws Exception {
		mockMvc.perform(get(PAYMENT_URL)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)));
	}

	@Test
	void findPaymentByIdWithSuccess() throws Exception {
		mockMvc.perform(get(PAYMENT_URL + "/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.value", is(20.0)))
				.andExpect(jsonPath("$.accountId", is(paymentDTO.getAccountId().intValue())));
	}
}
