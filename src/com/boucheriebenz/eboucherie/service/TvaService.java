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
import com.boucheriebenz.eboucherie.model.Tva;

@Service
public class TvaService {

    private static final Logger logger = Logger.getLogger(TvaService.class);

    public void save(Tva tva) throws Exception {
        if (tva.getId() == null) {
            insert(tva);
        } else {
            update(tva);
        }
    }

    private void insert(Tva tva) throws Exception {
        Connection connection = null;

        try {
            connection = JdbcConnector.getConnection();
            connection.setAutoCommit(false);

            // Insérer le TVA
            String sql = "INSERT INTO tva(pourcentage,libelle) VALUES(?,?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            setParams(tva, ps);
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

    private void update(Tva tva) throws Exception {
        Connection connection = null;

        try {
            connection = JdbcConnector.getConnection();
            connection.setAutoCommit(false);

            // Actualiser le TVA
            String sql = "UPDATE tva SET pourcentage=?,libelle=? "
                    + "WHERE id_tva=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            setParams(tva, ps);
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
    private void setParams(Tva tva, PreparedStatement ps)
            throws SQLException {
        ps.setDouble(1, tva.getPourcentage());
        ps.setString(2, tva.getLibelle());
        if (tva.getId() != null) {
            ps.setInt(3, tva.getId());
        }
    }


    public List<Tva> getTVAs() throws Exception {
        String sql = "SELECT * FROM tva ORDER BY libelle";
        List<Tva> tvas = new ArrayList<Tva>();
        Connection connection = null;
        ResultSet rs = null;

        try {
            connection = JdbcConnector.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                tvas.add(mapTVA(rs));
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

    /**
     * Méthode auxiliare pour faire le mapping entre le résultat d'une requête
     * et un objet TVA.
     */
    private Tva mapTVA(ResultSet rs) throws SQLException {
        Tva tva = new Tva();
        tva.setId(rs.getInt("id_tva"));
        tva.setLibelle(rs.getString("libelle"));
        tva.setPourcentage(rs.getDouble("pourcentage"));
        return tva;
    }

    public Tva getById(Integer id) throws Exception {
        String sql = "SELECT * FROM tva WHERE id_tva=?";
        Tva tva = new Tva();
        ResultSet rs = null;

        try {
            Connection connection = JdbcConnector.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            rs.next();

            tva = mapTVA(rs);
        } catch (Exception e) {
            logger.error(e);
            throw new SQLException("Erreur de Base de Données");
        } finally {
            rs.close();
            JdbcConnector.closeConnection();
        }

        return tva;
    }

    public void delete(Integer idTVA) throws Exception {
        Connection connection = null;

        try {
            connection = JdbcConnector.getConnection();
            connection.setAutoCommit(false);

            // Éliminer la TVA
            String sql = "DELETE FROM tva WHERE id_tva=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, idTVA);
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
