package com.boucheriebenz.eboucherie.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.boucheriebenz.eboucherie.jdbc.JdbcConnector;
import com.boucheriebenz.eboucherie.model.Article;
import com.boucheriebenz.eboucherie.model.Photo;
import com.boucheriebenz.eboucherie.model.Promotion;
import com.boucheriebenz.eboucherie.model.PromotionPhoto;

@Service
public class PromotionService {

    private static final Logger logger = Logger
            .getLogger(PromotionService.class);

    public void save(Promotion promotion) throws Exception {
        // S'il n'y a pas d'ID, c'est une nouvelle promotion
        if (promotion.getId() == null) {
            insert(promotion);
        } else {
            update(promotion);
        }
    }

    private void insert(Promotion promotion) throws Exception {
        Connection connection = null;

        try {
            connection = JdbcConnector.getConnection();
            connection.setAutoCommit(false);

            // Insérer la promotion
            String sql = "INSERT INTO promotion(titre,texte,article," +
                    "date_debut,date_fin,en_ligne) "
                    + "VALUES(?,?,?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            setParams(promotion, ps);
            ps.execute();

            // Récupérer l'id de la promotion créée
            int idPromo = -1;
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                idPromo = rs.getInt(1);
            } else {
                throw new SQLException("Erreur d'insertion, clé primaire "
                        + "non récupérée");
            }

            // Insérer le lien vers la photo
            Integer idPhoto = promotion.getPromotionPhoto().getPhoto().getId();
            if (idPhoto != null) {
                sql = "INSERT INTO promotion_photo VALUES(?,?)";
                ps = connection.prepareStatement(sql);
                ps.setInt(1, idPromo);
                ps.setInt(2, idPhoto);
                ps.execute();
            }

            connection.commit();
        } catch (Exception e) {
            connection.rollback();
            logger.error(e);
            throw new SQLException("Erreur de Base de Données");
        } finally {
            JdbcConnector.closeConnection();
        }
    }

    private void update(Promotion promotion) throws Exception {
        Connection connection = null;

        try {
            connection = JdbcConnector.getConnection();
            connection.setAutoCommit(false);

            // Actualiser la promotion
            String sql = "UPDATE promotion SET titre=?,texte=?,article=?," +
                    "date_debut=?,date_fin=?,en_ligne=? WHERE id_promotion=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            setParams(promotion, ps);
            ps.execute();

            // Actualiser le lien vers la photo
            Integer idPhoto = promotion.getPromotionPhoto().getPhoto().getId();
            if (idPhoto != null) {
                sql = "UPDATE promotion_photo SET photo=? WHERE promotion=?";
                ps = connection.prepareStatement(sql);
                ps.setInt(1, idPhoto);
                ps.setInt(2, promotion.getId());
                ps.execute();
            } else {  // La photo a été supprimée
                deletePromotionPhoto(promotion.getId(), ps, connection);
            }

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
     * Méthode auxiliare pour supprimer la relation entre une promotion et une
     * photo.
     */
    private void deletePromotionPhoto(int idPromo, PreparedStatement ps,
            Connection connection) throws SQLException {
        String sql = "DELETE FROM promotion_photo WHERE promotion=?";
        ps = connection.prepareStatement(sql);
        ps.setInt(1, idPromo);
        ps.execute();
    }

    /**
     * Méthode auxiliare pour fixer les paramètres du query INSERT ou UPDATE
     * d'une promotion.
     */
    private void setParams(Promotion promotion, PreparedStatement ps)
            throws SQLException {
        Date debut = null;
        Date fin = null;
        if (promotion.getDebut() != null) {
            debut = new Date(promotion.getDebut().getTime());
        }
        if (promotion.getFin() != null) {
            fin = new Date(promotion.getFin().getTime());
        }

        ps.setString(1, promotion.getTitre());
        ps.setString(2, promotion.getTexte());
        ps.setInt(3, promotion.getArticle().getId());
        ps.setDate(4, debut);
        ps.setDate(5, fin);
        ps.setBoolean(6, promotion.isEnLigne());
        if (promotion.getId() != null) {
            ps.setInt(7, promotion.getId());
        }
    }

    /**
     * Obtient les Promotions pour le Front-Office (avec les filtres :
     * en ligne, dates, etc).
     */
    public List<Promotion> getPromotions() throws Exception {
        String sql = "SELECT * "
                + "FROM promotion p "
                + "LEFT JOIN promotion_photo pp ON pp.promotion=p.id_promotion "
                + "LEFT JOIN photo ph ON pp.photo=ph.id_photo "
                + "RIGHT JOIN article a ON a.id_article=p.article "
                + "WHERE p.id_promotion IS NOT NULL AND p.en_ligne=true "
                + "AND ((p.date_debut IS NULL AND p.date_fin IS NULL) OR "
                + "(SYSDATE() >= p.date_debut "
                + "AND SYSDATE() < p.date_fin + INTERVAL 1 DAY)) "
                + "ORDER BY p.id_promotion";
        return getPromotions(sql);
    }

    /**
     * Obtient les Promotions pour le Back-Office (sans filtres).
     */
    public List<Promotion> getPromotionsAll() throws Exception {
        String sql = "SELECT * "
                + "FROM promotion p "
                + "LEFT JOIN promotion_photo pp ON pp.promotion=p.id_promotion "
                + "LEFT JOIN photo ph ON pp.photo=ph.id_photo "
                + "RIGHT JOIN article a ON a.id_article=p.article "
                + "WHERE p.id_promotion IS NOT NULL "
                + "ORDER BY p.id_promotion";
        return getPromotions(sql);
    }

    private List<Promotion> getPromotions(String sql) throws Exception {
        List<Promotion> promotions = new ArrayList<Promotion>();
        Connection connection = null;
        ResultSet rs = null;

        try {
            connection = JdbcConnector.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                promotions.add(mapPromotion(rs));
            }
        } catch (Exception e) {
            logger.error(e);
            throw new SQLException("Erreur de Base de Données");
        } finally {
            rs.close();
            JdbcConnector.closeConnection();
        }
        return promotions;
    }

    /**
     * Méthode auxiliare pour faire le mapping entre le résultat d'une requête
     * et un objet Promotion.
     */
    private Promotion mapPromotion(ResultSet rs) throws SQLException {
        Promotion promotion = new Promotion();
        Article article = new Article();
        PromotionPhoto pp = new PromotionPhoto();
        Photo photo = new Photo();

        photo.setLibelle(rs.getString("ph.libelle"));
        photo.setLien(rs.getString("ph.lien"));
        photo.setId(rs.getInt("pp.photo"));
        pp.setPhoto(photo);

        article.setId(rs.getInt("p.article"));
        article.setLibelle(rs.getString("a.libelle"));

        promotion.setId(rs.getInt("p.id_promotion"));
        promotion.setTitre(rs.getString("p.titre"));
        promotion.setTexte(rs.getString("p.texte"));
        promotion.setArticle(article);
        promotion.setPromotionPhoto(pp);
        promotion.setDebut(rs.getDate("p.date_debut"));
        promotion.setFin(rs.getDate("p.date_fin"));
        promotion.setEnLigne(rs.getBoolean("p.en_ligne"));
        return promotion;
    }

    public void delete(Integer idPromo) throws Exception {
        Connection connection = null;

        try {
            connection = JdbcConnector.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement ps = null;

            deletePromotionPhoto(idPromo, ps, connection);

            // Éliminer la promotion
            String sql = "DELETE FROM promotion WHERE id_promotion=?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, idPromo);
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

    public Promotion getById(Integer id) throws Exception {
        String sql = "SELECT * FROM promotion p "
                + "LEFT JOIN promotion_photo pp ON pp.promotion=p.id_promotion "
                + "LEFT JOIN photo ph ON pp.photo=ph.id_photo "
                + "RIGHT JOIN article a ON a.id_article=p.article "
                + "WHERE p.id_promotion=?";
        Promotion promotion = new Promotion();
        ResultSet rs = null;

        try {
            Connection connection = JdbcConnector.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            rs.next();

            promotion = mapPromotion(rs);
        } catch (Exception e) {
            logger.error(e);
            throw new SQLException("Erreur de Base de Données");
        } finally {
            rs.close();
            JdbcConnector.closeConnection();
        }

        return promotion;
    }

}
