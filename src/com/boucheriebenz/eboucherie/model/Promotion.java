package com.boucheriebenz.eboucherie.model;

import org.hibernate.validator.constraints.NotEmpty;

public class Promotion {

    private Integer id;
    @NotEmpty
    private String titre;
    @NotEmpty
    private String texte;
    private Article article;
    private PromotionPhoto promotionPhoto;

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

}
