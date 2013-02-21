package com.boucheriebenz.eboucherie.model;

public class Animal {

	private int id;
	private int age;
	private double poids;
	private String sexe;
	private String type;
	private String race;
	private int num_trac;
	private double prix_min;
	private double prix_max;
	private String photo1;
	private String photo2;
	private String photo3;

	
	public Animal() {
		super();
		
	}


	public int getAge() {
		return age;
	}


	public void setAge(int age) {
		this.age = age;
	}


	public double getPoids() {
		return poids;
	}


	public void setPoids(double poids) {
		this.poids = poids;
	}


	public String getSexe() {
		return sexe;
	}


	public void setSexe(String sexe) {
		this.sexe = sexe;
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


	public int getNum_trac() {
		return num_trac;
	}


	public void setNum_trac(int num_trac) {
		this.num_trac = num_trac;
	}


	public double getPrix_min() {
		return prix_min;
	}


	public void setPrix_min(double prix_min) {
		this.prix_min = prix_min;
	}


	public double getPrix_max() {
		return prix_max;
	}


	public void setPrix_max(double prix_max) {
		this.prix_max = prix_max;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getPhoto1() {
		return photo1;
	}


	public void setPhoto1(String photo1) {
		this.photo1 = photo1;
	}


	public String getPhoto2() {
		return photo2;
	}


	public void setPhoto2(String photo2) {
		this.photo2 = photo2;
	}


	public String getPhoto3() {
		return photo3;
	}


	public void setPhoto3(String photo3) {
		this.photo3 = photo3;
	}
	
	
	
	
}
