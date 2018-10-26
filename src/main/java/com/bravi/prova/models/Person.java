package com.bravi.prova.models;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Person {
    @Id
    @GeneratedValue
    public Long id;
    @Size(max = 100)
    public String name;
    @Size(max = 20)
    public String cpf;
    public Date birthDate;
    @Size(max = 100)
    public String motherName;
    @Size(max = 100)
    public String fatherName;
    public Character gender;
    @Size(max = 200)
    public String address;
    @OneToMany(cascade = CascadeType.ALL)
    public List<Contact> contacts = new ArrayList<>();
}
