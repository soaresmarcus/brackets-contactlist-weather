package com.bravi.prova.utils.contactlist;

import com.bravi.prova.repositories.ContactRepository;
import com.bravi.prova.repositories.PersonRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ContactUtilsTest {
    private final Long personId = 1L;
    @Autowired
    protected WebApplicationContext webApplicationContext;
    protected MockMvc mockMvc;
    @Autowired
    ContactUtils contactUtils;
    @Autowired
    PersonUtils personUtils;
    @Autowired
    PersonRepository personRepository;
    @Autowired
    ContactRepository contactRepository;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        createPerson();
    }

    @Test
    public void saveFullContact() throws JSONException {
        JSONObject jsonObj = new JSONObject() {
            {
                put("type", "phone");
                put("value", "(38) 99988-4441");
                put("personId", personId);
            }
        };
        Assert.assertTrue(saveAndCheck(jsonObj.toString()));
    }

    @Test
    public void saveContactWithNoType() throws JSONException {
        JSONObject jsonObj = new JSONObject() {
            {
                put("value", "(38) 99988-4441");
                put("personId", Long.parseLong("1"));
            }
        };
        Assert.assertFalse(saveAndCheck(jsonObj.toString()));
    }

    @Test
    public void saveContactWithNoPersonId() throws JSONException {
        JSONObject jsonObj = new JSONObject() {
            {
                put("type", "phone");
                put("value", "(38) 99988-4441");
            }
        };
        Assert.assertFalse(saveAndCheck(jsonObj.toString()));
    }

    @Test
    public void updateContact() {
    }

    @Test
    public void deleteContact() {
    }

    private boolean saveAndCheck(String jp) {
        try {
            return contactUtils.saveContact(jp);
        } catch (Exception e) {
            return false;
        }
    }

    private void createPerson() throws JSONException, IOException {
        JSONObject jsonObj = new JSONObject() {
            {
                put("id", personId);
                put("name", "Pablo Alves");
                put("cpf", "015.457.478-44");
                put("birthDate", "1987-10-16");
                put("motherName", "Maria Alcides");
                put("fatherName", "Alberto Medeiros");
                put("gender", "M");
                put("address", "Rua Andrades, 150 apto. 304, Funcionários");
            }
        };
        personUtils.savePerson(jsonObj.toString());
        Assert.assertFalse(personRepository.findAll().isEmpty());
    }
}