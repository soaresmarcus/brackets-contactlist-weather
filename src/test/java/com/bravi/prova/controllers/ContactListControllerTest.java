package com.bravi.prova.controllers;

import com.bravi.prova.models.Person;
import com.bravi.prova.repositories.ContactRepository;
import com.bravi.prova.repositories.PersonRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ContactListControllerTest {
    private final String apiAddr = "http://localhost:1147/contactlist";

    @Autowired
    protected WebApplicationContext webApplicationContext;
    protected MockMvc mockMvc;
    @Autowired
    PersonRepository personRepository;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        performPost(apiAddr + "/person/create", getPersonProfile().toString());
    }

    private JSONObject getPersonProfile() throws JSONException {
        return new JSONObject() {
            {
                {
                    put("name", "Alessandro Silva");
                    put("cpf", "015.457.478-44");
                    put("birthDate", "19871016");
                    put("motherName", "Maria Alcides");
                    put("fatherName", "Alberto Medeiros");
                    put("gender", "M");
                    put("address", "Rua dos Andrades, 150 apto. 304, Funcionários");
                }
            }
        };
    }

    @Test
    public void getAllPeople() throws Exception {
        JSONObject pf = getPersonProfile();
        pf.put("name", "Fabiano Silva");
        pf.put("cpf", "300.400.500-60");

        performPost(apiAddr + "/person/create", pf.toString());

        String response = performGet(apiAddr + "/person/get/all");
        List<Person> people = new ObjectMapper().readValue(response, new TypeReference<List<Person>>() {
        });
        Assert.assertTrue(people.size() >= 2);
    }

    @Test
    public void createPerson() throws Exception {
        JSONObject pf = getPersonProfile();
        pf.put("name", "aaaaaaaaaaaaa bbbbbbbbbbb ccccccccccc dddddddddddd");
        pf.put("cpf", "000.100.200-30");

        performPost(apiAddr + "/person/create", pf.toString());
        List<Person> result = personRepository.findAllByName(pf.getString("name"));
        Assert.assertTrue(result.size() == 1);
    }

    //TODO: fazer createPerson retornar falso

    @Test
    public void updatePerson() throws Exception {
        String addr = apiAddr + "/person/update/id/{personId}";
    }

    @Test
    public void createContact() throws Exception {
        String addr = apiAddr + "/contact/create";
    }

    @Test
    public void updateContact() throws Exception {
        String addr = apiAddr + "/contact/update/id/{contactId}";
    }

    @Test
    public void deleteContact() throws Exception {
        String addr = apiAddr + "/contact/delete/id/{contactId}";
    }

    @Test
    public void getAllContacts() throws Exception {
        String addr = apiAddr + "/contact/get/all";
    }

    public String performPost(String addr, String content) throws Exception {
        MvcResult sendReceiveDoc = mockMvc.perform(post(addr)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andReturn();
        return new String(sendReceiveDoc.getResponse().getContentAsByteArray());
    }

    public String performGet(String addr) throws Exception {
        MvcResult sendReceiveDoc = mockMvc.perform(get(addr)).andReturn();
        return new String(sendReceiveDoc.getResponse().getContentAsByteArray());
    }
}