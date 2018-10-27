package com.bravi.prova.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ContactListTest {

    @Autowired
    protected WebApplicationContext webApplicationContext;
    protected MockMvc mockMvc;
    @Autowired
    ContactListController contactList;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        contactList.createPerson(getJsonCreatePerson());
    }

    @Test
    public void getAllPeople() {
        ResponseEntity<String> people = null;
        try {
            people = contactList.getAllPeople();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        people.toString();
    }

    @Test
    public void createPerson() {
    }

    @Test
    public void updatePerson() {
    }

    @Test
    public void updatePerson1() {
    }

    @Test
    public void createContact() {
    }

    @Test
    public void updateContact() {
    }

    @Test
    public void deleteContact() {
    }

    @Test
    public void getAllContacts() {
    }

    private String getJsonCreatePerson() {
        return "{" +
                "    \"name\": \"Pedro Silva\"," +
                "    \"cpf\": \"015.457.478-44\"," +
                "    \"birthDate\": 561340800000," +
                "    \"motherName\": \"Maria Alcides\"," +
                "    \"fatherName\": \"Alberto Medeiros\"," +
                "    \"gender\": \"M\"," +
                "    \"address\": \"Rua Andrades, 150 apto. 304, Funcionários\"" +
                "}";
    }
}