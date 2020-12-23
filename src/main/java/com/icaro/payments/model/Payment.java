package com.icaro.payments.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter @Setter
public class Payment extends GenericEntity {

    @Id
    @GeneratedValue(generator = "PAYMENT_GENERATOR", strategy = GenerationType.AUTO)
    private Long id;

    private BigDecimal value;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;
}
