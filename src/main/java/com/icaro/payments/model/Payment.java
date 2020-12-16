package com.icaro.payments.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Payment extends GenericEntity {

    @Id
    @GeneratedValue(generator = "PAYMENT_GENERATOR", strategy = GenerationType.AUTO)
    private Long id;

    private BigDecimal value;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
