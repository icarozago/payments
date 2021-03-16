package com.icaro.payments.repositories;

import com.icaro.payments.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    public List<Person> findByName(String name);
}
