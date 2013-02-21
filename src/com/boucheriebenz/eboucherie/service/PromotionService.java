package com.boucheriebenz.eboucherie.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.boucheriebenz.eboucherie.jdbc.JdbcConnector;
import com.boucheriebenz.eboucherie.model.Article;
import com.boucheriebenz.eboucherie.model.Photo;
import com.boucheriebenz.eboucherie.model.Promotion;
import com.boucheriebenz.eboucherie.model.PromotionPhoto;

@Service
public class PromotionService {

    public void save(Promotion promotion) throws Exception {
        if (promotion.getId() == null) {
            insert(promotion);
        } else {
            update(promotion);
        }
    }

    private void insert(Promotion promotion) throws Exception {
        Connection connection = JdbcConnector.getConnection();
        connection.setAutoCommit(false);

        // Insérer la promotion
        String sql = "INSERT INTO promotion(titre, texte, article) " +
                "VALUES(?,?,?)";
        PreparedStatement ps = connection.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, promotion.getTitre());
        ps.setString(2, promotion.getTexte());
        ps.setInt(3, promotion.getArticle().getId());
        ps.execute();

        // Récupérer l'id de la promotion créée
        int idPromo = -1;
        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            idPromo = rs.getInt(1);
        } else {
            throw new SQLException("Erreur d'insertion, clé primaire " +
                    "non récupérée");
        }

        // Insérer le lien vers la photo
        sql = "INSERT INTO promotion_photo(promotion, photo) VALUES(?,?)";
        ps = connection.prepareStatement(sql);
        ps.setInt(1, idPromo);
        ps.setInt(2, promotion.getPromotionPhoto().getPhoto().getId());
        ps.execute();

        connection.commit();
        connection.setAutoCommit(true);
        JdbcConnector.closeConnection();
    }

    private void update(Promotion promotion) throws Exception {
        Connection connection = JdbcConnector.getConnection();
        connection.setAutoCommit(false);

        // Actualiser la promotion
        String sql = "UPDATE promotion SET titre=?,texte=?,article=? " +
                "WHERE id_promotion=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, promotion.getTitre());
        ps.setString(2, promotion.getTexte());
        ps.setInt(3, promotion.getArticle().getId());
        ps.setInt(4, promotion.getId());
        ps.execute();

        // Actualiser le lien vers la photo
        sql = "UPDATE promotion_photo SET photo=? WHERE promotion=?";
        ps = connection.prepareStatement(sql);
        ps.setInt(1, promotion.getPromotionPhoto().getPhoto().getId());
        ps.setInt(2, promotion.getId());
        ps.execute();

        connection.commit();
        connection.setAutoCommit(true);
        JdbcConnector.closeConnection();
    }

    public List<Promotion> getPromotions() throws Exception {
        Connection connection = JdbcConnector.getConnection();
        String sql = "SELECT * "
                + "FROM promotion p "
                + "LEFT JOIN promotion_photo pp ON pp.promotion=p.id_promotion "
                + "LEFT JOIN photo ph ON pp.photo=ph.id_photo "
                + "RIGHT JOIN article a ON a.id_article=p.article "
                + "WHERE p.id_promotion IS NOT NULL "
                + "ORDER BY p.id_promotion";
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        List<Promotion> promotions = new ArrayList<Promotion>();
        while (rs.next()) {
            Promotion promotion = new Promotion();
            Article article = new Article();
            PromotionPhoto pp = new PromotionPhoto();
            Photo photo = new Photo();

            photo.setLibelle(rs.getString("ph.libelle"));
            photo.setLien(rs.getString("lien"));
            pp.setPhoto(photo);

            article.setId(rs.getInt("id_article"));
            article.setLibelle(rs.getString("a.libelle"));

            promotion.setId(rs.getInt("id_promotion"));
            promotion.setTitre(rs.getString("p.titre"));
            promotion.setTexte(rs.getString("p.texte"));
            promotion.setArticle(article);
            promotion.setPromotionPhoto(pp);
            promotions.add(promotion);
        }

        rs.close();
        JdbcConnector.closeConnection();
        return promotions;
    }

    public void delete(Integer id) throws Exception {
        Connection connection = JdbcConnector.getConnection();
        connection.setAutoCommit(false);

        // Éliminer le lien vers la photo
        String sql = "DELETE FROM promotion_photo WHERE promotion=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.execute();

        // Éliminer la promotion
        sql = "DELETE FROM promotion WHERE id_promotion=?";
        ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.execute();

        connection.commit();
        connection.setAutoCommit(true);
        JdbcConnector.closeConnection();
    }

    public Promotion getById(Integer id) throws Exception {
        Connection connection = JdbcConnector.getConnection();
        String sql = "SELECT * FROM promotion p "
                + "LEFT JOIN promotion_photo pp ON pp.promotion=p.id_promotion "
                + "WHERE p.id_promotion=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        rs.next();

        Promotion promotion = new Promotion();
        Article article = new Article();
        PromotionPhoto pp = new PromotionPhoto();
        Photo photo = new Photo();

        photo.setId(rs.getInt("photo"));
        pp.setPhoto(photo);

        article.setId(rs.getInt("article"));

        promotion.setId(rs.getInt("id_promotion"));
        promotion.setTitre(rs.getString("titre"));
        promotion.setTexte(rs.getString("texte"));
        promotion.setArticle(article);
        promotion.setPromotionPhoto(pp);

        rs.close();
        JdbcConnector.closeConnection();
        return promotion;
    }

}
