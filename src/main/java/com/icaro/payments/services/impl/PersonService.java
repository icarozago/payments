package com.icaro.payments.services.impl;

import com.icaro.payments.model.Person;
import com.icaro.payments.repositories.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository repository;

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
