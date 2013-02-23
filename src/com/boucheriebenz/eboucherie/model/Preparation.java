package com.boucheriebenz.eboucherie.model;

import org.hibernate.validator.constraints.NotEmpty;

public class Preparation {
    private Integer id;
    @NotEmpty
    private String libelle;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
