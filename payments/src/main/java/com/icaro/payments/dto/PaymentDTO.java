package com.icaro.payments.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PaymentDTO {

	private Long id;
	
	private BigDecimal value;
	
	private Long accountId;
	
}
