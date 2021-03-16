package com.icaro.payments.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AccountDTO {
	
	private Long id;
	
	private Long number;
	
	private BigDecimal amount;
	
	private Long personId;

}
