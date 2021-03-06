package com.boucheriebenz.eboucherie.recherche.model;

// Generated 17 janv. 2013 16:53:19 by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * ArticlePhotoId generated by hbm2java
 */
@Embeddable
public class ArticlePhotoId implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int article;
	private int photo;

	public ArticlePhotoId() {
	}

	public ArticlePhotoId(int article, int photo) {
		this.article = article;
		this.photo = photo;
	}

	@Column(name = "article", nullable = false)
	public int getArticle() {
		return this.article;
	}

	public void setArticle(int article) {
		this.article = article;
	}

	@Column(name = "photo", nullable = false)
	public int getPhoto() {
		return this.photo;
	}

	public void setPhoto(int photo) {
		this.photo = photo;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ArticlePhotoId))
			return false;
		ArticlePhotoId castOther = (ArticlePhotoId) other;

		return (this.getArticle() == castOther.getArticle())
				&& (this.getPhoto() == castOther.getPhoto());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getArticle();
		result = 37 * result + this.getPhoto();
		return result;
	}

}
