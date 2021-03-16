package com.icaro.payments.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.icaro.payments.dto.AccountDTO;
import com.icaro.payments.dto.PaymentDTO;
import com.icaro.payments.model.Account;
import com.icaro.payments.model.Payment;
import com.icaro.payments.repositories.PaymentRepository;
import com.icaro.payments.services.impl.AccountService;
import com.icaro.payments.services.impl.PaymentService;
import com.icaro.payments.utils.TestUtils;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

	@Mock
	private PaymentRepository paymentRepository;
	
	@Mock
	private AccountService accountService;
	
	@Mock
	private ModelMapper modelMapper;
	
	@InjectMocks
	private PaymentService service;
	
	@Test
	void reprocessPaymentValueWithSuccess() {
		Account account = new Account();
		account.setAmount(new BigDecimal(80));
		Payment payment = new Payment();
		payment.setAccount(account);
		payment.setValue(new BigDecimal(30));
		
		Account newAccount = new Account();
		newAccount.setAmount(new BigDecimal(90));
		newAccount.setId(1L);
		Payment newPayment = new Payment();
		newPayment.setAccount(newAccount);
		newPayment.setValue(new BigDecimal(20));
		
		PaymentDTO paymentDTO = TestUtils.getPayment();
		paymentDTO.setId(1L);
		
		AccountDTO accountDTO = new AccountDTO();
		accountDTO.setAmount(new BigDecimal(90));
		
		Mockito.when(paymentRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(payment));
		Mockito.when(accountService.convertToDTO(Mockito.any(Account.class))).thenReturn(accountDTO);
		Mockito.when(accountService.createOrUpdate(Mockito.any(AccountDTO.class))).thenReturn(accountDTO);
		Mockito.when(paymentRepository.save(Mockito.any(Payment.class))).thenReturn(newPayment);
		Mockito.when(modelMapper.map(Mockito.any(Payment.class), Mockito.any())).thenReturn(paymentDTO);
		
		PaymentDTO updatedPayment = service.update(paymentDTO);
		
		assertEquals(paymentDTO.getValue(), updatedPayment.getValue());
		assertEquals(newPayment.getAccount().getAmount(), accountDTO.getAmount());
	}
}
