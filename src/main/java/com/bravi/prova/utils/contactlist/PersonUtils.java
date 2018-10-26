package com.bravi.prova.utils.contactlist;

import com.bravi.prova.models.Person;
import com.bravi.prova.repositories.PersonRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class PersonUtils {
    @Autowired
    PersonRepository personRepository;

    public PersonUtils() {
    }

    public Person bind(String json) throws IOException {
        ObjectMapper objectMappernew = new ObjectMapper();
        return objectMappernew.readValue(json, Person.class);
    }

    public boolean savePerson(String jsonRequest) throws IOException {
        Person person = new PersonUtils().bind(jsonRequest);

        personRepository.save(person);
        return true;
    }
}
