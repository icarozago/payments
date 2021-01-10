package com.icaro.payments.dto;

import java.math.BigDecimal;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentDTO {

	private Long id;
	
	@NotNull
	private BigDecimal value;
	
	@NotNull
	private Long accountId;
	
}
