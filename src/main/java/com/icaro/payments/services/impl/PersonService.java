package com.icaro.payments.services.impl;

import com.icaro.payments.model.Person;
import com.icaro.payments.repositories.PersonRepository;
import com.icaro.payments.services.IGenericService;
import com.icaro.payments.services.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService implements IPersonService {

    @Autowired
    private PersonRepository repository;

    @Override
    public List<Person> findAll() {
        return repository.findAll();
    }

    @Override
    public Person findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Person create(Person person) {
        return repository.save(person);
    }

    @Override
    public Person update(Person person) {
        return repository.save(person);
    }
}
