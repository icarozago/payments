package com.icaro.payments.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

@Entity
@Getter @Setter
public class Person implements Serializable {

    private static final long serialVersionUID = 2342342342342342341L;

    @Id
    @GeneratedValue(generator = "PERSON_GENERATOR", strategy= GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private String cpf;

    @NonNull
    private String email;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<Account> accounts = new HashSet<>();
}
