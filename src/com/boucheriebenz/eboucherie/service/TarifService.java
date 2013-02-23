package com.boucheriebenz.eboucherie.service;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.boucheriebenz.eboucherie.jdbc.JdbcConnector;
import com.boucheriebenz.eboucherie.model.Article;
import com.boucheriebenz.eboucherie.model.Preparation;
import com.boucheriebenz.eboucherie.model.TVA;
import com.boucheriebenz.eboucherie.model.Tarif;

@Service
public class TarifService {
    private static final Logger logger = Logger.getLogger(TarifService.class);

	public void save(Tarif tarif) throws Exception {
        if (tarif.getId() == null) {
            insert(tarif);
        } else {
            update(tarif);
        }
    }

	private void insert(Tarif tarif) throws Exception {
        Connection connection = null;

        try {
            connection = JdbcConnector.getConnection();
            connection.setAutoCommit(false);

            // Insérer le tarif
            String sql = "INSERT INTO tarif(article,preparation,tva,"
                    + "type_tarif,groupe_tarif,date_debut,date_fin,"
                    + "quantite_min,quantite_max) VALUES(?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            setParams(tarif, ps);
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

    private void update(Tarif tarif) throws Exception {
        Connection connection = null;

        try {
            connection = JdbcConnector.getConnection();
            connection.setAutoCommit(false);

            // Actualiser le tarif
            String sql = "UPDATE tarif SET article=?,preparation=?,tva=?,"
                    + "type_tarif=?,groupe_tarif=?,date_debut=?,date_fin=?,"
                    + "quantite_min=?,quantite_max=? WHERE id_tarif=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            setParams(tarif, ps);
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
    private void setParams(Tarif tarif, PreparedStatement ps)
            throws SQLException {
        Date debut = null;
        Date fin = null;
        if (tarif.getDebut() != null) {
            debut = new Date(tarif.getDebut().getTime());
        }
        if (tarif.getFin() != null) {
            fin = new Date(tarif.getFin().getTime());
        }

        ps.setInt(1, tarif.getArticle().getId());
        ps.setInt(2, tarif.getPreparation().getId());
        ps.setInt(3, tarif.getTva().getId());
        ps.setString(4, tarif.getType());
        ps.setInt(5, tarif.getGroupe());
        ps.setDate(6, debut);
        ps.setDate(7, fin);
        ps.setDouble(8, tarif.getMinimum());
        ps.setDouble(9, tarif.getMaximum());
        if (tarif.getId() != null) {
            ps.setInt(10, tarif.getId());
        }
    }


    public List<Tarif> getTarifs() throws Exception {
        String sql = "SELECT * FROM tarif t "
                + "LEFT JOIN preparation p ON t.preparation=p.id_preparation "
                + "LEFT JOIN tva ON t.tva=tva.id_tva "
                + "RIGHT JOIN article a ON a.id_article=t.article "
                + "WHERE t.id_tarif IS NOT NULL "
                + "ORDER BY t.id_tarif";
        List<Tarif> tarifs = new ArrayList<Tarif>();
        Connection connection = null;
        ResultSet rs = null;

        try {
            connection = JdbcConnector.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                tarifs.add(mapTarif(rs));
            }
        } catch (Exception e) {
            logger.error(e);
            throw new SQLException("Erreur de Base de Données");
        } finally {
            rs.close();
            JdbcConnector.closeConnection();
        }
        return tarifs;
    }

    /**
     * Méthode auxiliare pour faire le mapping entre le résultat d'une requête
     * et un objet Tarif.
     */
    private Tarif mapTarif(ResultSet rs) throws SQLException {
        Tarif tarif = new Tarif();
        Article article = new Article();
        Preparation preparation = new Preparation();
        TVA tva = new TVA();

        article.setId(rs.getInt("a.id_article"));
        article.setLibelle(rs.getString("a.libelle"));

        preparation.setId(rs.getInt("p.id_preparation"));
        preparation.setLibelle(rs.getString("p.libelle"));

        tva.setId(rs.getInt("tva.id_tva"));
        tva.setLibelle(rs.getString("tva.libelle"));

        tarif.setId(rs.getInt("t.id_tarif"));
        tarif.setArticle(article);
        tarif.setPreparation(preparation);
        tarif.setTva(tva);
        tarif.setType(rs.getString("t.type_tarif"));
        tarif.setGroupe(rs.getInt("t.groupe_tarif"));
        tarif.setDebut(rs.getDate("t.date_debut"));
        tarif.setFin(rs.getDate("t.date_fin"));
        tarif.setMinimum(rs.getDouble("t.quantite_min"));
        tarif.setMaximum(rs.getDouble("t.quantite_max"));

        return tarif;
    }

    public Tarif getById(Integer id) throws Exception {
        String sql = "SELECT * FROM tarif t "
                + "LEFT JOIN preparation p ON t.preparation=p.id_preparation "
                + "LEFT JOIN tva ON t.tva=tva.id_tva "
                + "RIGHT JOIN article a ON a.id_article=t.article "
                + "WHERE t.id_tarif=?";
        Tarif tarif = new Tarif();
        ResultSet rs = null;

        try {
            Connection connection = JdbcConnector.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            rs.next();

            tarif = mapTarif(rs);
        } catch (Exception e) {
            logger.error(e);
            throw new SQLException("Erreur de Base de Données");
        } finally {
            rs.close();
            JdbcConnector.closeConnection();
        }

        return tarif;
    }

    public void delete(Integer idTarif) throws Exception {
        Connection connection = null;

        try {
            connection = JdbcConnector.getConnection();
            connection.setAutoCommit(false);

            // Éliminer la promotion
            String sql = "DELETE FROM tarif WHERE id_tarif=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, idTarif);
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
