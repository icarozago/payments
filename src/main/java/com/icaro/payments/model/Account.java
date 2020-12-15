package com.icaro.payments.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "account")
public class Account extends GenericEntity {

    @Id
    @GeneratedValue(generator = "ACCOUNT_GENERATOR", strategy = GenerationType.AUTO)
    private Long id;

    private Long number;

    private BigDecimal amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_person")
    private Person person;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
