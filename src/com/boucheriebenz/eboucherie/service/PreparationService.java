package com.boucheriebenz.eboucherie.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.boucheriebenz.eboucherie.jdbc.JdbcConnector;
import com.boucheriebenz.eboucherie.model.Preparation;

@Service
public class PreparationService {

    private static final Logger logger = Logger
            .getLogger(PreparationService.class);

    public List<Preparation> getPreparations() throws Exception {
        String sql = "SELECT * FROM preparation ORDER BY libelle";
        List<Preparation> preparations = new ArrayList<Preparation>();
        ResultSet rs = null;

        try {
            Connection connection = JdbcConnector.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Preparation preparation = new Preparation();
                preparation.setId(rs.getInt("id_preparation"));
                preparation.setLibelle(rs.getString("libelle"));
                preparations.add(preparation);
            }
        } catch (Exception e) {
            logger.error(e);
            throw new SQLException("Erreur de Base de Données");
        } finally {
            rs.close();
            JdbcConnector.closeConnection();
        }

        return preparations;
    }

}
