package com.boucheriebenz.eboucherie.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.boucheriebenz.eboucherie.jdbc.JdbcConnector;
import com.boucheriebenz.eboucherie.model.Animal;

@Service
public class AnimauxService {

	private int tailleTable;

	public List<Animal> getAnimaux(int indice_debut, int noOfRecords)
			throws Exception {
		Connection connection = JdbcConnector.getConnection();
		String query = "select SQL_CALC_FOUND_ROWS * from animal_photo ap,animal a,photo p where ap.animal=a.id_animal and ap.photo=p.id_photo group by ap.animal limit "
				+ indice_debut + ", " + noOfRecords;

		PreparedStatement ps = connection.prepareStatement(query);
		ResultSet rs = ps.executeQuery();

		List<Animal> animaux = new LinkedList<Animal>();
		while (rs.next()) {
			Animal animal = new Animal();
			animal.setId(rs.getInt("id_animal"));
			animal.setAge(rs.getInt("age"));
			animal.setPoids(rs.getDouble("poids"));
			animal.setSexe(rs.getString("sexe"));
			animal.setType(rs.getString("type"));
			animal.setRace(rs.getString("race"));
			animal.setPrix_min(rs.getDouble("prix_min"));
			animal.setPrix_max(rs.getDouble("prix_max"));
			animal.setPhoto1(rs.getString("lien"));
			animaux.add(animal);
		}
		rs = ps.executeQuery("SELECT FOUND_ROWS()");
		if (rs.next())
			this.tailleTable = rs.getInt(1);

		rs.close();
		JdbcConnector.closeConnection();
		return animaux;
	}

	public int getTailleTable() {
		return tailleTable;
	}

}
