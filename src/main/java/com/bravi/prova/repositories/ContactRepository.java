package com.bravi.prova.repositories;

import com.bravi.prova.models.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findByValue(String value);
}
