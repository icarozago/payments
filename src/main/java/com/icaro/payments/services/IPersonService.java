package com.icaro.payments.services;

import com.icaro.payments.model.Person;

import java.util.List;

public interface IPersonService {

    public List<Person> findAll();

    public Person findById(Long id);

    public Person create(Person person);

    public Person update(Person person);
}
