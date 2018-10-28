package com.bravi.prova.models;

import javax.persistence.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Person {
    @Id
    @GeneratedValue
    public Long id;
    @NotNull
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
    @OneToMany(mappedBy = "personId", cascade = CascadeType.ALL)
    public List<Contact> contacts = new ArrayList<>();

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Person) {
            Person p = (Person) obj;

            p.birthDate = new Date(p.birthDate.getTime());
            this.birthDate = new Date(this.birthDate.getTime());

            return (p.name.equals(this.name) &&
                    p.cpf.equals(this.cpf) &&
                    p.birthDate.equals(this.birthDate) &&
                    p.motherName.equals(this.motherName) &&
                    p.fatherName.equals(this.fatherName) &&
                    p.gender.equals(this.gender) &&
                    p.address.equals(this.address));
        }
        return false;
    }
}