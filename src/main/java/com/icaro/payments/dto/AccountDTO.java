package com.icaro.payments.dto;

import java.math.BigDecimal;

import org.springframework.lang.NonNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AccountDTO {
	
	private Long id;
	
	@NonNull
	private Long number;
	
	@NonNull
	private BigDecimal amount;
	
	private Long personId;

}
