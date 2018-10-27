package com.bravi.prova.utils.contactlist;

import com.bravi.prova.models.Contact;
import com.bravi.prova.repositories.ContactRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class ContactUtils {
    @Autowired
    ContactRepository contactRepository;

    private Contact bindContact(String json) throws IOException {
        ObjectMapper objectMappernew = new ObjectMapper();
        return objectMappernew.readValue(json, Contact.class);
    }

    public Contact saveContact(String jsonRequest) throws IOException {
        Contact contactBind = bindContact(jsonRequest);
        return contactRepository.save(contactBind);
    }

    public Contact updateContact(String jsonRequest, Long id) throws IOException {
        Optional<Contact> contact = contactRepository.findById(id);

        if (!contact.isPresent()) return null;

        Contact contactBind = bindContact(jsonRequest);
        contactBind.id = contact.get().id;
        contactBind.type = (contactBind.type != null) ? contactBind.type : contact.get().type;
        contactBind.value = (contactBind.value != null) ? contactBind.value : contact.get().value;
        contactBind.personId = contact.get().personId;

        return contactRepository.save(contactBind);
    }

    public boolean deleteContact(Long id) {
        Optional<Contact> contact = contactRepository.findById(id);

        if (!contact.isPresent()) return false;

        contactRepository.delete(contact.get());
        return true;
    }
}
