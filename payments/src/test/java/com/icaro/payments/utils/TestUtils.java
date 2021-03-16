package com.icaro.payments.utils;

import java.math.BigDecimal;

import com.icaro.payments.dto.AccountDTO;
import com.icaro.payments.dto.PaymentDTO;
import com.icaro.payments.dto.PersonDTO;

public class TestUtils {
	
	private static PaymentDTO paymentDTO;
	
	private static PersonDTO personDTO;
	
	private static AccountDTO accountDTO;
	
	public static PersonDTO getPerson() {
		if (personDTO == null) {
			personDTO = new PersonDTO();
			personDTO.setName("Name Test");
			personDTO.setCpf("12345678910");
			personDTO.setEmail("naotenho@gmail.com");
		}
		
		return personDTO;
	}
	
	public static AccountDTO getAccount() {
		if (accountDTO == null) {
			accountDTO = new AccountDTO();
			accountDTO.setAmount(new BigDecimal(100));
			accountDTO.setNumber(12345678915L);
			accountDTO.setPersonId(1L);
		}
		
		return accountDTO;
	}
	
	public static PaymentDTO getPayment() {
		if (paymentDTO == null) {
			paymentDTO = new PaymentDTO();
			paymentDTO.setValue(new BigDecimal(20));
			paymentDTO.setAccountId(1L);
		}
		
		return paymentDTO;
	}

}
