package com.boucheriebenz.eboucherie.model;

public class Article {

    private Integer id;
    private String libelle;
    private String type;
    private String race;
    private Tarif tarif;
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

    public Tarif getTarif() {
        return tarif;
    }

    public void setTarif(Tarif tarif) {
        this.tarif = tarif;
    }

	public ArticlePhoto getArticlePhoto() {
		return articlePhoto;
	}

	public void setArticlePhoto(ArticlePhoto articlePhoto) {
		this.articlePhoto = articlePhoto;
	}
	@Override
    public int hashCode() {
        return this.getLibelle().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        Article ar = new Article();
        if (obj instanceof Article) {
            ar = (Article) obj;
        }

        return (ar.getLibelle().equals(this.getLibelle()));
    }
    
}
