package com.bravi.prova.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Contact {
    @Id
    @GeneratedValue
    public Long id;
    @NotNull
    @Size(max = 20)
    public String type;
    @Size(max = 100)
    public String value;
    @NotNull
    public Long personId;
}
