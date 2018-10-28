package com.bravi.prova.controllers;

import com.bravi.prova.models.Person;
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
import java.util.Optional;

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
        Assert.assertEquals(1, result.size());
    }

    @Test
    public void createPersonWithNoName() throws Exception {
        JSONObject pf = getPersonProfile();
        pf.put("name", null);
        pf.put("cpf", "012.345.678-97");

        try {
            performPost(apiAddr + "/person/create", pf.toString());
        } catch (Exception ignored) {
        }
        List<Person> result = personRepository.findAllByCpf(pf.getString("cpf"));
        Assert.assertNotEquals(1, result.size());
    }

    @Test
    public void updatePerson() throws Exception {
        List<Person> people = personRepository.findAllByCpf("015.457.478-44");
        Assert.assertTrue(people.size() > 0);
        Long personId = people.get(0).id;

        JSONObject pf = getPersonProfile();
        pf.put("name", "Alessandra Souza");
        pf.put("cpf", "015.457.478-55");
        pf.put("birthDate", "19901120");
        pf.put("motherName", "Maria Rita");
        pf.put("fatherName", "Alberto Silvano");
        pf.put("gender", "F");
        pf.put("address", "Rua Jonas, 200 apto. 110, Operários");

        performPost(apiAddr + "/person/update/id/" + personId, pf.toString());
        Person result = personRepository.findById(personId).get();

        pf.put("id", personId);
        Person person = new ObjectMapper().readValue(pf.toString(), Person.class);

        Assert.assertEquals(person, result);
    }

    @Test
    public void deletePerson() throws Exception {
        Person person = new Person();
        person.name = "Roberta Andrade";
        person.cpf = "015.457.478-55";

        Person personDb = personRepository.save(person);
        Assert.assertNotNull(personDb.id);
        Long personId = personDb.id;

        performGet(apiAddr + "/person/delete/id/" + personId);
        Optional<Person> result = personRepository.findById(personId);
        Assert.assertFalse(result.isPresent());
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

    private String performPost(String addr, String content) throws Exception {
        MvcResult sendReceiveDoc = mockMvc.perform(post(addr)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andReturn();
        return new String(sendReceiveDoc.getResponse().getContentAsByteArray());
    }

    private String performGet(String addr) throws Exception {
        MvcResult sendReceiveDoc = mockMvc.perform(get(addr)).andReturn();
        return new String(sendReceiveDoc.getResponse().getContentAsByteArray());
    }
}