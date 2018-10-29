package com.bravi.prova.controllers;

import com.bravi.prova.models.Contact;
import com.bravi.prova.models.Person;
import com.bravi.prova.repositories.ContactRepository;
import com.bravi.prova.repositories.PersonRepository;
import com.bravi.prova.utils.contactlist.ContactUtils;
import com.bravi.prova.utils.contactlist.PersonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/contactlist")
public class ContactListController {

    @Autowired
    PersonRepository personRepository;
    @Autowired
    ContactRepository contactRepository;
    @Autowired
    PersonUtils personUtils;
    @Autowired
    ContactUtils contactUtils;

    @GetMapping(value = "/person/get/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllPeople() throws JsonProcessingException {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        List<Person> registros = personRepository.findAll();
        String jsonArray = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(registros);

        return new ResponseEntity<String>(jsonArray.toString(), httpHeaders, HttpStatus.OK);
    }

    @PostMapping(value = "/person/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createPerson(@RequestBody String jsonRequest) throws IOException {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        boolean status = personUtils.savePerson(jsonRequest);
        HttpStatus httpStat = (status) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;

        return new ResponseEntity<String>(httpHeaders, httpStat);
    }

    @PostMapping(value = "/person/update/id/{personId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updatePerson(@RequestBody String jsonRequest,
                                               @PathVariable("personId") Long personId) throws IOException {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        boolean status = personUtils.updatePerson(jsonRequest, personId);
        HttpStatus httpStat = (status) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;

        return new ResponseEntity<String>(httpHeaders, httpStat);
    }

    @GetMapping(value = "/person/delete/id/{personId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deletePerson(@PathVariable("personId") Long personId) throws IOException {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        boolean status = personUtils.deletePerson(personId);
        HttpStatus httpStat = (status) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;

        return new ResponseEntity<String>(httpHeaders, httpStat);
    }

    /*
     *
     * CONTROLLERS FOR CONTACTS
     *
     */

    @PostMapping(value = "/contact/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createContact(@RequestBody String jsonRequest) throws IOException {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        Contact result = contactUtils.saveContact(jsonRequest);
        HttpStatus httpStat = (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;

        return new ResponseEntity<String>(httpHeaders, httpStat);
    }

    @PostMapping(value = "/contact/update/id/{contactId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateContact(@RequestBody String jsonRequest,
                                                @PathVariable("contactId") Long contactId) throws IOException {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        Contact result = contactUtils.updateContact(jsonRequest, contactId);
        HttpStatus httpStat = (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;

        return new ResponseEntity<String>(httpHeaders, httpStat);
    }

    @GetMapping(value = "/contact/delete/id/{contactId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteContact(@PathVariable("contactId") Long contactId) throws IOException {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        boolean status = contactUtils.deleteContact(contactId);
        HttpStatus httpStat = (status) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;

        return new ResponseEntity<String>(httpHeaders, httpStat);
    }

    @GetMapping(value = "/contact/get/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllContacts() throws JsonProcessingException {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        List<Contact> registros = contactRepository.findAll();
        String jsonArray = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(registros);

        return new ResponseEntity<String>(jsonArray.toString(), httpHeaders, HttpStatus.OK);
    }

}
