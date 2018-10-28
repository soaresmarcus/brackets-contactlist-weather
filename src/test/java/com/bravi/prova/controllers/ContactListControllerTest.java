package com.bravi.prova.controllers;

import com.bravi.prova.models.Contact;
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
    @Autowired
    ContactRepository contactRepository;

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
        List<Person> people = personRepository.findAll();
        Assert.assertTrue(people.size() > 0);
        Long personId = people.get(0).id;

        JSONObject jsonObj = new JSONObject() {
            {
                put("type", "phone");
                put("value", "(38) 97777-7456");
                put("personId", personId);
            }
        };

        performPost(apiAddr + "/contact/create", jsonObj.toString());
        List<Contact> result = contactRepository.findByValue(jsonObj.getString("value"));
        Assert.assertTrue(result.size() > 0);
    }

    @Test
    public void updateContact() throws Exception {
        Long personId = genAndGetPersonId("456.248.698.22");
        Long contactId = genAndGetContactId(personId, "whatsapp", "(61) 91456-4578");

        JSONObject jsonObj = new JSONObject() {
            {
                put("value", "(61) 91111-8888");
            }
        };

        performPost(apiAddr + "/contact/update/id/" + contactId, jsonObj.toString());
        List<Contact> result = contactRepository.findByValue(jsonObj.getString("value"));
        Assert.assertTrue(result.size() > 0);
    }

    @Test
    public void deleteContact() throws Exception {
        Long personId = genAndGetPersonId("778.547.249-80");
        Long contactId = genAndGetContactId(personId, "whatsapp", "(37) 91498-5621");

        performGet(apiAddr + "/contact/delete/id/"+ contactId);
        Optional<Contact> result = contactRepository.findById(contactId);
        Assert.assertFalse(result.isPresent());
    }

    @Test
    public void getAllContacts() throws Exception {
        Long personId = genAndGetPersonId("425.745.658-20");
        genAndGetContactId(personId, "phone", "(37) 91498-5621");
        genAndGetContactId(personId, "phone", "(37) 99223-1111");
        genAndGetContactId(personId, "whatsapp", "(37) 9877-6521");

        performGet(apiAddr + "/contact/get/all");
        List<Contact> result = contactRepository.findAll();
        Assert.assertTrue(result.size() >= 3);
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

    private Long genAndGetContactId(Long personId, String type, String value) {
        Contact contact = new Contact();
        contact.type = type;
        contact.value = value;
        contact.personId = personId;
        Contact contactDb = contactRepository.save(contact);
        return contactDb.id;
    }

    private Long genAndGetPersonId(String cpf) throws Exception {
        JSONObject person = getPersonProfile();
        person.put("cpf", cpf);
        performPost(apiAddr + "/person/create", person.toString());
        List<Person> people = personRepository.findAllByCpf(person.getString("cpf"));
        return (people.size() > 0) ? people.get(0).id : null;
    }
}