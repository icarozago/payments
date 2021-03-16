package com.icaro.payments.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Getter @Setter
public class Payment implements Serializable {

    private static final long serialVersionUID = 2342342342342342341L;

    @Id
    @GeneratedValue(generator = "PAYMENT_GENERATOR", strategy = GenerationType.AUTO)
    private Long id;

    private BigDecimal value;

    @ManyToOne
    private Account account;
    
    public String getKafkaMessage(BigDecimal... oldValue) {
    	StringBuilder result = new StringBuilder("{");
    	result.append("\"value\": ")
    	.append(value)
    	.append(", \"account\": ")
    	.append(account.getNumber())
    	.append(", \"amount\": ")
    	.append(account.getAmount())
    	.append(", \"email\": \"")
    	.append(account.getPerson().getEmail())
    	.append("\", \"personName\": \"")
    	.append(account.getPerson().getName())
    	.append("\"");
    	
    	if (oldValue.length > 0) {
    		result.append(", \"oldValue\": ")
    		.append(oldValue[0]);
    	}
    	
    	result.append("}");
    	
    	return result.toString();
    }
}
