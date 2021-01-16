package com.icaro.payments.dto;

import java.math.BigDecimal;

import org.springframework.lang.NonNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PaymentDTO {

	private Long id;
	
	@NonNull
	private BigDecimal value;
	
	@NonNull
	private Long accountId;
	
}
