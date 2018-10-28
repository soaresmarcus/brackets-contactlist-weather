package com.bravi.prova.repositories;

import com.bravi.prova.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findAllByName(String name);

    List<Person> findAllByCpf(String cpf);
}
