package com.icaro.payments.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Person extends GenericEntity {

    @Id
    @GeneratedValue(generator = "PERSON_GENERATOR", strategy= GenerationType.AUTO)
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private String cpf;

    @NonNull
    private String email;


    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<Account> accounts;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Collection<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Collection<Account> accounts) {
        this.accounts = accounts;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
