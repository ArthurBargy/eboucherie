package com.boucheriebenz.eboucherie.model;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class FichierPhoto {

    private Integer id;
    @NotEmpty
    private String libelle;
    private CommonsMultipartFile fichier;

    public FichierPhoto() {
        super();
    }

    public FichierPhoto(Integer id, String libelle,
            CommonsMultipartFile fichier) {
        this.id = id;
        this.libelle = libelle;
        this.fichier = fichier;
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

    public CommonsMultipartFile getFichier() {
        return fichier;
    }

    public void setFichier(CommonsMultipartFile fichier) {
        this.fichier = fichier;
    }

}
