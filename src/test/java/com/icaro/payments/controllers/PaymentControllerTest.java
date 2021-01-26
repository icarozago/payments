package com.icaro.payments.controllers;

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
import com.icaro.payments.controllers.PaymentController;
import com.icaro.payments.dto.PaymentDTO;
import com.icaro.payments.services.impl.PaymentService;
import com.icaro.payments.services.impl.PersonService;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = PaymentController.class)
class PaymentControllerTest {
	
	private MockMvc mockMvc;
	
	private ObjectMapper objectMapper;
	
	private PaymentDTO paymentDTO;
	
	private static final String PAYMENT_URL = "/payments";
	
	@MockBean
	private PaymentService service;
	
	@Autowired
	public PaymentControllerTest(MockMvc mockMvc, ObjectMapper objectMapper) {
		this.mockMvc = mockMvc;
		this.objectMapper = objectMapper;
		this.paymentDTO = new PaymentDTO();
		paymentDTO.setValue(new BigDecimal(20));
		paymentDTO.setAccountId(1L);
	}
	
	@Test
	void createNewPaymentWithSuccess() throws Exception {
		mockMvc.perform(post(PAYMENT_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(paymentDTO)))
				.andExpect(status().isCreated());
//				.andExpect(jsonPath("$.value", is(paymentDTO.getValue())));
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
		mockMvc.perform(post(PAYMENT_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(paymentDTO)))
				.andExpect(status().isCreated());
		
		mockMvc.perform(put(PAYMENT_URL + "/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	void findAllPaymentsWithSuccess() throws Exception {
		mockMvc.perform(get(PAYMENT_URL)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	void findPaymentByIdWithSuccess() throws Exception {
		mockMvc.perform(get(PAYMENT_URL + "/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
}
