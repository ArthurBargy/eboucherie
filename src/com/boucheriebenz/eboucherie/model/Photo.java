package com.boucheriebenz.eboucherie.model;


public class Photo {

    private Integer id;
    private String libelle;
    private String lien;

    public Photo() {
        super();
    }

    public Photo(Integer id, String libelle, String lien) {
        this.id = id;
        this.libelle = libelle;
        this.lien = lien;
    }

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

    public String getLien() {
        return lien;
    }

    public void setLien(String lien) {
        this.lien = lien;
    }

}
