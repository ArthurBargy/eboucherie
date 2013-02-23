package com.boucheriebenz.eboucherie.model;

import org.hibernate.validator.constraints.NotEmpty;

public class Texte {
    @NotEmpty
    private String nom;
    @NotEmpty
    private String texte;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getTexte() {
        return texte;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

}
