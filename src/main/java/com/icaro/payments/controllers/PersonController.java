package com.icaro.payments.controllers;

import com.icaro.payments.model.Person;
import com.icaro.payments.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/people")
public class PersonController {

    @Autowired
    private PersonRepository repository;

    @RequestMapping("/hello")
    public String helloWorld() {
        return "Hello Manolo!!";
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Person createPerson(@RequestBody Person person) {
        repository.save(person);

        return person;
    }

    @GetMapping
    public List<Person> findAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Person findById(@PathVariable long id) {
        return repository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Person update(@PathVariable long id, @RequestBody Person person) {
        Optional<Person> dBPerson = repository.findById(id);

        if (!dBPerson.isPresent()) {
            return null;
        }

        person.setId(dBPerson.get().getId());

        repository.save(person);

        return person;
    }

//    @GetMapping
//    public List<Person> findByName(@RequestHeader String name) {
//        return repository.findByName(name);
//    }
}
