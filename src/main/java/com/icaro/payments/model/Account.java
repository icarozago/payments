package com.icaro.payments.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter @Setter
public class Account extends GenericEntity {

    @Id
    @GeneratedValue(generator = "ACCOUNT_GENERATOR", strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    private Long number;

    @NonNull
    private BigDecimal amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_person")
    private Person person;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Payment> payments;
}
