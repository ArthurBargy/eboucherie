package com.boucheriebenz.eboucherie.model;

import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

public class Article {

    private Integer id;
    @NotEmpty
    private String libelle;
    @NotEmpty
    private String type;
    @NotEmpty
    private String race;
    private String description;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date debut;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date fin;
    private boolean enLigne;
    private ArticlePhoto articlePhoto;

    public Article() {
        super();
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }


    public String getDescription() {
        return description;
    }

    public Date getDebut() {
        return debut;
    }

    public Date getFin() {
        return fin;
    }

    public boolean isEnLigne() {
        return enLigne;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDebut(Date debut) {
        this.debut = debut;
    }

    public void setFin(Date fin) {
        this.fin = fin;
    }

    public void setEnLigne(boolean enLigne) {
        this.enLigne = enLigne;
    }

    public ArticlePhoto getArticlePhoto() {
        return articlePhoto;
    }

    public void setArticlePhoto(ArticlePhoto articlePhoto) {
        this.articlePhoto = articlePhoto;
    }

}
