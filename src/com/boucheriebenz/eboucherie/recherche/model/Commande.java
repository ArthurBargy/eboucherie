package com.boucheriebenz.eboucherie.recherche.model;

// Generated 17 janv. 2013 16:53:19 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Commande generated by hbm2java
 */
@Entity
@Table(name = "commande", catalog = "eBoucherie")
public class Commande implements java.io.Serializable {

	private Integer idCommande;
	private double montantTtc;
	private double montantHt;
	private double montantTva;
	private Date dateCommande;
	private Date datePreparation;
	private String commentaire;
	private int client;

	public Commande() {
	}

	public Commande(double montantTtc, double montantHt, double montantTva,
			Date dateCommande, Date datePreparation, int client) {
		this.montantTtc = montantTtc;
		this.montantHt = montantHt;
		this.montantTva = montantTva;
		this.dateCommande = dateCommande;
		this.datePreparation = datePreparation;
		this.client = client;
	}

	public Commande(double montantTtc, double montantHt, double montantTva,
			Date dateCommande, Date datePreparation, String commentaire,
			int client) {
		this.montantTtc = montantTtc;
		this.montantHt = montantHt;
		this.montantTva = montantTva;
		this.dateCommande = dateCommande;
		this.datePreparation = datePreparation;
		this.commentaire = commentaire;
		this.client = client;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_commande", unique = true, nullable = false)
	public Integer getIdCommande() {
		return this.idCommande;
	}

	public void setIdCommande(Integer idCommande) {
		this.idCommande = idCommande;
	}

	@Column(name = "montantTTC", nullable = false, precision = 22, scale = 0)
	public double getMontantTtc() {
		return this.montantTtc;
	}

	public void setMontantTtc(double montantTtc) {
		this.montantTtc = montantTtc;
	}

	@Column(name = "montantHT", nullable = false, precision = 22, scale = 0)
	public double getMontantHt() {
		return this.montantHt;
	}

	public void setMontantHt(double montantHt) {
		this.montantHt = montantHt;
	}

	@Column(name = "montantTVA", nullable = false, precision = 22, scale = 0)
	public double getMontantTva() {
		return this.montantTva;
	}

	public void setMontantTva(double montantTva) {
		this.montantTva = montantTva;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "date_commande", nullable = false, length = 0)
	public Date getDateCommande() {
		return this.dateCommande;
	}

	public void setDateCommande(Date dateCommande) {
		this.dateCommande = dateCommande;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "date_preparation", nullable = false, length = 0)
	public Date getDatePreparation() {
		return this.datePreparation;
	}

	public void setDatePreparation(Date datePreparation) {
		this.datePreparation = datePreparation;
	}

	@Column(name = "commentaire")
	public String getCommentaire() {
		return this.commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	@Column(name = "client", nullable = false)
	public int getClient() {
		return this.client;
	}

	public void setClient(int client) {
		this.client = client;
	}

}
