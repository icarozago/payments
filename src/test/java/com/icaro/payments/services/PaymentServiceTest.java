package com.icaro.payments.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.icaro.payments.dto.PaymentDTO;
import com.icaro.payments.model.Account;
import com.icaro.payments.model.Payment;
import com.icaro.payments.repositories.AccountRepository;
import com.icaro.payments.repositories.PaymentRepository;
import com.icaro.payments.services.impl.PaymentService;
import com.icaro.payments.utils.TestUtils;

import lombok.AllArgsConstructor;

@RunWith(MockitoJUnitRunner.class)
class PaymentServiceTest {

	@Mock
	private PaymentRepository paymentRepository;
	
	@Mock
	private AccountRepository accountRepository;
	
	@Autowired
	private PaymentService service;
	
	@Test
	void reprocessPaymentValueWithSuccess() {
		Account account = new Account();
		account.setAmount(new BigDecimal(80));
		Payment payment = new Payment();
		payment.setAccount(account);
		payment.setValue(new BigDecimal(20));
		
		Account newAccount = new Account();
		newAccount.setAmount(new BigDecimal(30));
		Payment newPayment = new Payment();
		newPayment.setAccount(newAccount);
		newPayment.setValue(new BigDecimal(70));
		
		Mockito.when(paymentRepository.findById(1L)).thenReturn(Optional.of(payment));
		Mockito.when(accountRepository.save(newAccount)).thenReturn(newAccount);
		Mockito.when(paymentRepository.save(newPayment)).thenReturn(newPayment);
		
		PaymentDTO updatedPayment = service.update(TestUtils.getPayment());
		
		assertEquals(newPayment.getValue(), updatedPayment.getValue());
	}
}
