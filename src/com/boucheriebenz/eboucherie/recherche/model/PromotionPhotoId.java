package com.boucheriebenz.eboucherie.recherche.model;

// Generated 17 janv. 2013 16:53:19 by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * PromotionPhotoId generated by hbm2java
 */
@Embeddable
public class PromotionPhotoId implements java.io.Serializable {

	private int promotion;
	private int photo;

	public PromotionPhotoId() {
	}

	public PromotionPhotoId(int promotion, int photo) {
		this.promotion = promotion;
		this.photo = photo;
	}

	@Column(name = "promotion", nullable = false)
	public int getPromotion() {
		return this.promotion;
	}

	public void setPromotion(int promotion) {
		this.promotion = promotion;
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
		if (!(other instanceof PromotionPhotoId))
			return false;
		PromotionPhotoId castOther = (PromotionPhotoId) other;

		return (this.getPromotion() == castOther.getPromotion())
				&& (this.getPhoto() == castOther.getPhoto());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getPromotion();
		result = 37 * result + this.getPhoto();
		return result;
	}

}
