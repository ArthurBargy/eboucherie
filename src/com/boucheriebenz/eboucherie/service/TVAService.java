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
import com.boucheriebenz.eboucherie.model.TVA;

@Service
public class TVAService {

    private static final Logger logger = Logger.getLogger(TVAService.class);

    public List<TVA> getTvas() throws Exception {
        String sql = "SELECT * FROM tva ORDER BY libelle";
        List<TVA> tvas = new ArrayList<TVA>();
        ResultSet rs = null;

        try {
            Connection connection = JdbcConnector.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                TVA tva = new TVA();
                tva.setId(rs.getInt("id_tva"));
                tva.setPourcentage(rs.getDouble("pourcentage"));
                tva.setLibelle(rs.getString("libelle"));
                tvas.add(tva);
            }
        } catch (Exception e) {
            logger.error(e);
            throw new SQLException("Erreur de Base de Données");
        } finally {
            rs.close();
            JdbcConnector.closeConnection();
        }

        return tvas;
    }

}
