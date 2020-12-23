package com.icaro.payments.services.impl;

import com.icaro.payments.model.Person;
import com.icaro.payments.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    @Autowired
    private PersonRepository repository;

    public List<Person> findAll() {
        return repository.findAll();
    }

    public Person findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Person create(Person person) {
        return repository.save(person);
    }

    public Person update(Person person) {
        return repository.save(person);
    }
}
