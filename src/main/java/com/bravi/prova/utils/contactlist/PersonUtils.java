package com.bravi.prova.utils.contactlist;

import com.bravi.prova.models.Contact;
import com.bravi.prova.models.Person;
import com.bravi.prova.repositories.ContactRepository;
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
    @Autowired
    ContactRepository contactRepository;

    public PersonUtils() {
    }

    private Person bindPerson(String json) throws IOException {
        ObjectMapper objectMappernew = new ObjectMapper();
        return objectMappernew.readValue(json, Person.class);
    }

    private Contact bindContact(String json) throws IOException {
        ObjectMapper objectMappernew = new ObjectMapper();
        return objectMappernew.readValue(json, Contact.class);
    }

    public boolean savePerson(String jsonRequest) throws IOException {
        Person personBind = bindPerson(jsonRequest);
        Person personDB = personRepository.save(personBind);
        return (personDB.id != null);
    }

    public boolean saveContact(String jsonRequest) throws IOException {
        Contact contactBind = bindContact(jsonRequest);
        Contact contactDB = contactRepository.save(contactBind);
        return (contactDB.id != null);    }

    public boolean updatePerson(String jsonRequest, Long id) throws IOException {
        Optional<Person> person = personRepository.findById(id);

        if (person.isPresent()) {
            Person personBind = bindPerson(jsonRequest);
            Person personDB = personRepository.save(person.get());
            return (personDB.id != null);
        } else
            return false;
    }
}
