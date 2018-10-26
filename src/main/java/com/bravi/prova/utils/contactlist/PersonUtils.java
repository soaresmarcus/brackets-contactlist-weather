package com.bravi.prova.utils.contactlist;

import com.bravi.prova.models.Person;
import com.bravi.prova.repositories.PersonRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class PersonUtils {
    @Autowired
    PersonRepository personRepository;

    public PersonUtils() {
    }

    private Person bindPerson(String json) throws IOException {
        ObjectMapper objectMappernew = new ObjectMapper();
        return objectMappernew.readValue(json, Person.class);
    }

    public boolean savePerson(String jsonRequest) throws IOException {
        Person personBind = bindPerson(jsonRequest);
        Person personDB = personRepository.save(personBind);
        return (personDB.id != null);
    }

    public boolean updatePerson(String jsonRequest, Long id) throws IOException {
        Optional<Person> person = personRepository.findById(id);

        if (!person.isPresent()) return false;

        Person personBind = bindPerson(jsonRequest);
        personBind.id = person.get().id;
        personBind.name = (personBind.name != null) ? personBind.name : person.get().name;
        personBind.cpf = (personBind.cpf != null) ? personBind.cpf : person.get().cpf;
        personBind.birthDate = (personBind.birthDate != null) ? personBind.birthDate : person.get().birthDate;
        personBind.motherName = (personBind.motherName != null) ? personBind.motherName : person.get().motherName;
        personBind.fatherName = (personBind.fatherName != null) ? personBind.fatherName : person.get().fatherName;
        personBind.gender = (personBind.gender != null) ? personBind.gender : person.get().gender;
        personBind.address = (personBind.address != null) ? personBind.address : person.get().address;

        Person personDB = personRepository.save(personBind);
        return (personDB.id != null);
    }

    public boolean deletePerson(Long id) {
        Optional<Person> person = personRepository.findById(id);

        if (!person.isPresent()) return false;

        personRepository.delete(person.get());
        return true;
    }
}
