package com.icaro.payments.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter
public class Account implements Serializable {

    private static final long serialVersionUID = 2342342342342342341L;

    @Id
    @GeneratedValue(generator = "ACCOUNT_GENERATOR", strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    private Long number;

    @NonNull
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "fk_person")
    private Person person;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Payment> payments = new HashSet<>();
}
