package com.bravi.prova.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Size;

@Entity
public class Contact {
    @Id
    @GeneratedValue
    public Long id;
    @Size(max = 20)
    public String type;
    @Size(max = 100)
    public String value;
}
