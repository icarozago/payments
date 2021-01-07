package com.icaro.payments.controllers;

import com.icaro.payments.model.Person;
import com.icaro.payments.services.impl.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/people")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService service;

    @RequestMapping("/hello")
    public String helloWorld() {
        return "Hello Manolo aaaaaa!!";
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Person createPerson(@RequestBody Person person) {
        return service.create(person);
    }

    @GetMapping
    public List<Person> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Person findById(@PathVariable long id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public Person update(@PathVariable long id, @RequestBody Person person) {
        Person dBPerson = service.findById(id);

        if (Objects.isNull(dBPerson)) {
            return null;
        }

        person.setId(dBPerson.getId());

        return service.update(person);
    }

//    @GetMapping
//    public List<Person> findByName(@RequestHeader String name) {
//        return repository.findByName(name);
//    }
}
