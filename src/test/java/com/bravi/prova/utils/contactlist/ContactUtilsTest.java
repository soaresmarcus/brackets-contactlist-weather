package com.bravi.prova.utils.contactlist;

import com.bravi.prova.models.Contact;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ContactUtilsTest {
    private final Long personId = 1L;
    @Autowired
    ContactUtils contactUtils;
    @Autowired
    PersonUtils personUtils;
    @Autowired
    PersonRepository personRepository;
    @Autowired
    ContactRepository contactRepository;
    private Long contactId;

    @Before
    public void setup() throws Exception {
        createPerson();
        createContact();
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
    public void updateFullContact() throws JSONException {
        JSONObject jsonObj = new JSONObject() {
            {
                put("type", "teste");
                put("value", "(38) 99988-0000");
            }
        };
        updateAndCheck(jsonObj.toString(), contactId);
        Contact contact = contactRepository.findById(contactId).get();
        Assert.assertEquals(contact.type, "teste");
        Assert.assertEquals(contact.value, "(38) 99988-0000");
    }

    @Test
    public void updateContactValue() throws JSONException {
        JSONObject jsonObj = new JSONObject() {
            {
                put("value", "(38) 99988-7898");
            }
        };
        updateAndCheck(jsonObj.toString(), contactId);
        Contact contact = contactRepository.findById(contactId).get();
        Assert.assertEquals(contact.value, "(38) 99988-7898");
    }

    @Test
    public void deleteContact() {
        contactUtils.deleteContact(contactId);
        Assert.assertFalse(contactRepository.findById(contactId).isPresent());
    }

    private boolean saveAndCheck(String jp) {
        try {
            return contactUtils.saveContact(jp) != null;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean updateAndCheck(String jp, Long contactId) {
        try {
            return contactUtils.updateContact(jp, contactId) != null;
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

    private void createContact() throws JSONException, IOException {
        JSONObject jsonObj = new JSONObject() {
            {
                put("type", "phone");
                put("value", "(38) 99988-4441");
                put("personId", personId);
            }
        };
        Contact contact = contactUtils.saveContact(jsonObj.toString());
        Assert.assertNotNull(contact);
        this.contactId = contact.id;
    }
}