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
import com.boucheriebenz.eboucherie.model.ArticlePhoto;
import com.boucheriebenz.eboucherie.model.Photo;


@Service
public class ArticleService {

    private static final Logger logger = Logger.getLogger(ArticleService.class);

	public void save(Article article) throws Exception {
        if (article.getId() == null) {
            insert(article);
        } else {
            update(article);
        }
    }

    private void insert(Article article) throws Exception {
        Connection connection = null;

        try {
            connection = JdbcConnector.getConnection();
            connection.setAutoCommit(false);

            // Insérer un article
            String sql = "INSERT INTO article(libelle,type,race,description,"
                    + "date_debut,date_fin,en_ligne) "
                    + "VALUES(?,?,?,?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            setParams(article, ps);
            ps.execute();

            // Récupérer l'id de l'article crée
            int idArticle = -1;
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                idArticle = rs.getInt(1);
            } else {
                throw new SQLException("Erreur d'insertion, clé primaire "
                        + "non récupérée");
            }

            // Insérer le lien vers la photo
            Integer idPhoto = article.getArticlePhoto().getPhoto().getId();
            if (idPhoto != null) {
                sql = "INSERT INTO article_photo VALUES(?,?)";
                ps = connection.prepareStatement(sql);
                ps.setInt(1, idArticle);
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

    private void update(Article article) throws Exception {
        Connection connection = null;

        try {
            connection = JdbcConnector.getConnection();
            connection.setAutoCommit(false);

            // Actualiser article
            String sql = "UPDATE article SET libelle=?,type=?,race=?,description=?,"
                    + "date_debut=?,date_fin=?,en_ligne=? WHERE id_article=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            setParams(article, ps);
            ps.execute();

            // Actualiser le lien vers la photo
            Integer idPhoto = article.getArticlePhoto().getPhoto().getId();
            if (idPhoto != null) {
                deleteArticlePhoto(article.getId(), ps, connection);
                sql = "INSERT INTO article_photo VALUES (?,?)";
                ps = connection.prepareStatement(sql);
                ps.setInt(1, article.getId());
                ps.setInt(2, idPhoto);
                ps.execute();
            } else { // La photo a été supprimée
                deleteArticlePhoto(article.getId(), ps, connection);
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
     * Méthode auxiliare pour supprimer la relation entre un article et une
     * photo.
     */
    private void deleteArticlePhoto(int idArticle, PreparedStatement ps,
            Connection connection) throws SQLException {
        String sql = "DELETE FROM article_photo WHERE article=?";
        ps = connection.prepareStatement(sql);
        ps.setInt(1, idArticle);
        ps.execute();
    }

    /**
     * Méthode auxiliare pour fixer les paramètres du query INSERT ou UPDATE.
     */
    private void setParams(Article article, PreparedStatement ps)
            throws SQLException {
        Date debut = null;
        Date fin = null;
        if (article.getDebut() != null) {
            debut = new Date(article.getDebut().getTime());
        }
        if (article.getFin() != null) {
            fin = new Date(article.getFin().getTime());
        }

        ps.setString(1, article.getLibelle());
        ps.setString(2, article.getType());
        ps.setString(3, article.getRace());
        ps.setString(4, article.getDescription()) ;
        ps.setDate(5, debut);
        ps.setDate(6, fin);
        ps.setBoolean(7, article.isEnLigne());
        if (article.getId() != null) {
            ps.setInt(8, article.getId());
        }
    }

    /**
     * Obtient les Articles pour le Front-Office (avec les filtres :
     * en ligne, dates, etc).
     */
    public List<Article> getArticles() throws Exception {
        String sql = "SELECT * "
                + "FROM article a "
                + "LEFT JOIN article_photo ap ON ap.article=a.id_article "
                + "LEFT JOIN photo ph ON ap.photo=ph.id_photo "
                + "WHERE a.id_article IS NOT NULL AND a.en_ligne=true "
                + "AND ((a.date_debut IS NULL AND a.date_fin IS NULL) OR "
                + "(SYSDATE() >= a.date_debut "
                + "AND SYSDATE() < a.date_fin + INTERVAL 1 DAY)) "
                + "ORDER BY a.id_article";
        return getArticles(sql);
    }

    /**
     * Obtient les Articles pour le Back-Office (sans filtres).
     */
    public List<Article> getArticlesAll() throws Exception {
        String sql = "SELECT * "
                + "FROM article a "
                + "LEFT JOIN article_photo ap ON ap.article=a.id_article "
                + "LEFT JOIN photo ph ON ap.photo=ph.id_photo "
                + "WHERE a.id_article IS NOT NULL "
                + "ORDER BY a.id_article";
        return getArticles(sql);
    }

    private List<Article> getArticles(String sql) throws Exception {
        List<Article> articles = new ArrayList<Article>();
        Connection connection = null;
        ResultSet rs = null;

        try {
            connection = JdbcConnector.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                articles.add(mapArticle(rs));
            }
        } catch (Exception e) {
            logger.error(e);
            throw new SQLException("Erreur de Base de Données");
        } finally {
            rs.close();
            JdbcConnector.closeConnection();
        }
        return articles;
    }

    /**
     * Méthode auxiliare pour faire le mapping entre le résultat d'une requête
     * et un objet Article.
     */
    private Article mapArticle(ResultSet rs) throws SQLException {
        Article article = new Article();
        ArticlePhoto ap = new ArticlePhoto();
        Photo photo = new Photo();

        photo.setLibelle(rs.getString("ph.libelle"));
        photo.setLien(rs.getString("ph.lien"));
        photo.setId(rs.getInt("ap.photo"));
        ap.setPhoto(photo);

        article.setId(rs.getInt("a.id_article"));
        article.setLibelle(rs.getString("a.libelle"));
        article.setType(rs.getString("a.type"));
        article.setRace(rs.getString("a.race"));
        article.setDescription(rs.getString("a.description"));
        article.setDebut(rs.getDate("a.date_debut"));
        article.setFin(rs.getDate("a.date_fin"));
        article.setEnLigne(rs.getBoolean("a.en_ligne"));
        article.setArticlePhoto(ap);
        return article;
    }

    public Article getById(Integer id) throws Exception {
        String sql = "SELECT * FROM article a "
                + "LEFT JOIN article_photo ap ON ap.article=a.id_article "
                + "LEFT JOIN photo ph ON ap.photo=ph.id_photo "
                + "WHERE a.id_article=?";
        Article article = new Article();
        ResultSet rs = null;

        try {
            Connection connection = JdbcConnector.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            rs.next();

            article = mapArticle(rs);
        } catch (Exception e) {
            logger.error(e);
            throw new SQLException("Erreur de Base de Données");
        } finally {
            rs.close();
            JdbcConnector.closeConnection();
        }

        return article;
    }

    public void delete(Integer idArticle) throws Exception {
        Connection connection = null;

        try {
            connection = JdbcConnector.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement ps = null;

            deleteArticlePhoto(idArticle, ps, connection);

            // Éliminer article
            String sql = "DELETE FROM article WHERE id_article=?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, idArticle);
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
