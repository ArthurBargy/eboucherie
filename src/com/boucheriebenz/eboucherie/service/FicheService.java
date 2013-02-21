package com.boucheriebenz.eboucherie.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.boucheriebenz.eboucherie.jdbc.JdbcConnector;
import com.boucheriebenz.eboucherie.model.Animal;

@Service
public class FicheService {
	
    public Animal getById(Integer id) throws Exception {
        Connection connection = JdbcConnector.getConnection();
        CallableStatement cs = connection.prepareCall("select * from animal a,photo p where a.id_animal = " + id 
        		+ " and p.id_photo = " + id);
        ResultSet rs = cs.executeQuery();
        rs.next();

        Animal animal = new Animal();
        animal.setId(id);
		animal.setAge(rs.getInt("age"));
		animal.setPoids(rs.getDouble("poids"));
		animal.setSexe(rs.getString("sexe"));
		animal.setType(rs.getString("type"));
		animal.setRace(rs.getString("race"));
		animal.setPrix_min(rs.getDouble("prix_min"));
		animal.setPrix_max(rs.getDouble("prix_max"));
		animal.setPhoto1(rs.getString("lien"));

        rs.close();
        JdbcConnector.closeConnection();
        return animal;
    }

}
