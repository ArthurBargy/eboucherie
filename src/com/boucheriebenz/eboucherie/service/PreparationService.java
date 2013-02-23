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

    public void save(Preparation preparation) throws Exception {
        if (preparation.getId() == null) {
            insert(preparation);
        } else {
            update(preparation);
        }
    }

    private void insert(Preparation preparation) throws Exception {
        Connection connection = null;

        try {
            connection = JdbcConnector.getConnection();
            connection.setAutoCommit(false);

            // Insérer le Preparation
            String sql = "INSERT INTO preparation(libelle) VALUES(?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            setParams(preparation, ps);
            ps.execute();

            connection.commit();
        } catch (Exception e) {
            connection.rollback();
            logger.error(e);
            throw new SQLException("Erreur de Base de Données");
        } finally {
            JdbcConnector.closeConnection();
        }
    }

    private void update(Preparation preparation) throws Exception {
        Connection connection = null;

        try {
            connection = JdbcConnector.getConnection();
            connection.setAutoCommit(false);

            // Actualiser le Preparation
            String sql = "UPDATE preparation SET libelle=? "
                    + "WHERE id_preparation=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            setParams(preparation, ps);
            ps.execute();

            connection.commit();
        } catch (Exception e) {
            connection.rollback();
            logger.error(e);
            throw new SQLException("Erreur de Base de Données");
        } finally {
            JdbcConnector.closeConnection();
        }
    }

    /**
     * Méthode auxiliare pour fixer les paramètres du query INSERT ou UPDATE.
     */
    private void setParams(Preparation preparation, PreparedStatement ps)
            throws SQLException {
        ps.setString(1, preparation.getLibelle());
        if (preparation.getId() != null) {
            ps.setInt(2, preparation.getId());
        }
    }


    public List<Preparation> getPreparations() throws Exception {
        String sql = "SELECT * FROM preparation ORDER BY libelle";
        List<Preparation> preparations = new ArrayList<Preparation>();
        Connection connection = null;
        ResultSet rs = null;

        try {
            connection = JdbcConnector.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                preparations.add(mapPreparation(rs));
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

    /**
     * Méthode auxiliare pour faire le mapping entre le résultat d'une requête
     * et un objet Preparation.
     */
    private Preparation mapPreparation(ResultSet rs) throws SQLException {
        Preparation preparation = new Preparation();
        preparation.setId(rs.getInt("id_preparation"));
        preparation.setLibelle(rs.getString("libelle"));
        return preparation;
    }

    public Preparation getById(Integer id) throws Exception {
        String sql = "SELECT * FROM preparation WHERE id_preparation=?";
        Preparation preparation = new Preparation();
        ResultSet rs = null;

        try {
            Connection connection = JdbcConnector.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            rs.next();

            preparation = mapPreparation(rs);
        } catch (Exception e) {
            logger.error(e);
            throw new SQLException("Erreur de Base de Données");
        } finally {
            rs.close();
            JdbcConnector.closeConnection();
        }

        return preparation;
    }

    public void delete(Integer idPreparation) throws Exception {
        Connection connection = null;

        try {
            connection = JdbcConnector.getConnection();
            connection.setAutoCommit(false);

            // Éliminer la Preparation
            String sql = "DELETE FROM preparation WHERE id_preparation=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, idPreparation);
            ps.execute();

            connection.commit();
        } catch (Exception e) {
            connection.rollback();
            logger.error(e);
            throw new SQLException("Erreur de Base de Données");
        } finally {
            JdbcConnector.closeConnection();
        }
    }

}
