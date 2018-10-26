package com.bravi.prova.controllers;

import com.bravi.prova.models.Person;
import com.bravi.prova.repositories.PersonRepository;
import com.bravi.prova.utils.contactlist.PersonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class ContactList {

    @Autowired
    PersonRepository personRepository;
    @Autowired
    PersonUtils personUtils;

    @GetMapping(value = "/contactlist/get/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllPeople() throws JsonProcessingException {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        List<Person> registros = personRepository.findAll();
        String jsonArray = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(registros);

        return new ResponseEntity<String>(jsonArray.toString(), httpHeaders, HttpStatus.OK);
    }

    @PostMapping(value = "/contactlist/set/person", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> setPerson(@RequestBody String jsonRequest) throws IOException {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        boolean status = personUtils.savePerson(jsonRequest);
        HttpStatus httpStat = (status) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;

        return new ResponseEntity<String>(httpHeaders, httpStat);
    }

}
