package com.boucheriebenz.eboucherie.model;

import java.util.Date;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

public class Promotion {

    private Integer id;
    @NotEmpty
    private String titre;
    @NotEmpty
    private String texte;
    @Valid
    private Article article;
    private PromotionPhoto promotionPhoto;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date debut;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date fin;
    private boolean enLigne;

    public Promotion() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getTexte() {
        return texte;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public PromotionPhoto getPromotionPhoto() {
        return promotionPhoto;
    }

    public void setPromotionPhoto(PromotionPhoto promotionPhoto) {
        this.promotionPhoto = promotionPhoto;
    }

    public Date getDebut() {
        return debut;
    }

    public void setDebut(Date debut) {
        this.debut = debut;
    }

    public Date getFin() {
        return fin;
    }

    public void setFin(Date fin) {
        this.fin = fin;
    }

    public boolean isEnLigne() {
        return enLigne;
    }

    public void setEnLigne(boolean enLigne) {
        this.enLigne = enLigne;
    }

}
