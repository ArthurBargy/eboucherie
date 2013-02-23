package com.boucheriebenz.eboucherie.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;

import com.boucheriebenz.eboucherie.jdbc.JdbcConnector;
import com.boucheriebenz.eboucherie.model.Texte;

@Service
public class TexteService implements ServletContextAware {

    private static final Logger logger = Logger.getLogger(TexteService.class);
    private ServletContext context;

    @Override
    public void setServletContext(ServletContext context) {
        this.context = context;
        try {
            this.context.setAttribute("textes", getTextes());
        } catch (Exception e) {
            logger.error(e);
        }
    }

    public void refreshContext() {
        try {
            context.setAttribute("textes", getTextes());
        } catch (Exception e) {
            logger.error(e);
        }
    }

    public void save(Texte texte) throws Exception {
        Connection connection = null;

        try {
            connection = JdbcConnector.getConnection();
            connection.setAutoCommit(false);

            // Actualiser le Texte
            String sql = "INSERT INTO texte(nom,texte) VALUES(?,?) "
                    + "ON DUPLICATE KEY UPDATE texte=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, texte.getNom());
            ps.setString(2, texte.getTexte());
            ps.setString(3, texte.getTexte());
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

    public Map<String, String> getTextes() throws Exception {
        String sql = "SELECT * FROM texte";
        Map<String, String> textes = new HashMap<String, String>();
        Connection connection = null;
        ResultSet rs = null;

        try {
            connection = JdbcConnector.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                textes.put(rs.getString("nom"), rs.getString("texte"));
            }
        } catch (Exception e) {
            logger.error(e);
            throw new SQLException("Erreur de Base de Données");
        } finally {
            rs.close();
            JdbcConnector.closeConnection();
        }
        return textes;
    }

    public Texte getByNom(String nom) throws Exception {
        String sql = "SELECT * FROM texte WHERE nom=?";
        Texte texte = new Texte();
        ResultSet rs = null;

        try {
            Connection connection = JdbcConnector.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, nom);
            rs = ps.executeQuery();
            rs.next();

            texte.setNom(rs.getString("nom"));
            texte.setTexte(rs.getString("texte"));
        } catch (Exception e) {
            logger.error(e);
            throw new SQLException("Erreur de Base de Données");
        } finally {
            rs.close();
            JdbcConnector.closeConnection();
        }

        return texte;
    }

    public void delete(String nom) throws Exception {
        Connection connection = null;

        try {
            connection = JdbcConnector.getConnection();
            connection.setAutoCommit(false);

            // Éliminer la Texte
            String sql = "DELETE FROM texte WHERE nom=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, nom);
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
