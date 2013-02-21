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
import com.boucheriebenz.eboucherie.model.ArticlePhoto;
import com.boucheriebenz.eboucherie.model.Photo;
import com.boucheriebenz.eboucherie.model.Tarif;


@Service
public class ArticleService {
	
	public void save(Article article) throws Exception {
        if (article.getId() == null) {
            insert(article);
        } else {
            update(article);
        }
    }
	
	  private void insert(Article article) throws Exception {
	        Connection connection = JdbcConnector.getConnection();
	        connection.setAutoCommit(false);

	        // Ins�rer un article
	        String sql = "INSERT INTO article(libelle, type, race,tarif) " +
	                "VALUES(?,?,?,?)";
	        PreparedStatement ps = connection.prepareStatement(sql,
	                Statement.RETURN_GENERATED_KEYS);
	        ps.setString(1, article.getLibelle());
	        ps.setString(2, article.getType());
	        ps.setString(3, article.getRace());
	        ps.setInt(4, article.getTarif().getId());
	        ps.execute();

	        // R�cup�rer l'id de l'article cr�e
	        int idArticle = -1;
	        ResultSet rs = ps.getGeneratedKeys();
	        if (rs.next()) {
	            idArticle = rs.getInt(1);
	        } else {
	            throw new SQLException("Erreur d'insertion, clé primaire " +
	                    "non récupérée");
	        }
	        
	     // Insérer le lien vers la photo
	        sql = "INSERT INTO article_photo(article, photo) VALUES(?,?)";
	        ps = connection.prepareStatement(sql);
	        ps.setInt(1, idArticle);
	        ps.setInt(2, article.getArticlePhoto().getPhoto().getId());
	        ps.execute();

	        connection.commit();
	        connection.setAutoCommit(true);
	        JdbcConnector.closeConnection();
	    }
	  
	  private void update(Article article) throws Exception {
	        Connection connection = JdbcConnector.getConnection();
	        connection.setAutoCommit(false);

	        // Actualiser article
	        String sql = "UPDATE article SET libelle=?,type=?,race=?,tarif=?" +
	                "WHERE id_article=?";
	        PreparedStatement ps = connection.prepareStatement(sql);
	        ps.setString(1, article.getLibelle());
	        ps.setString(2, article.getType());
	        ps.setString(3, article.getRace());
	        ps.setInt(4, article.getTarif().getId());
	        ps.execute();

	        // Actualiser le lien vers la photo
	        sql = "UPDATE article_photo SET photo=? WHERE article=?";
	        ps = connection.prepareStatement(sql);
	        ps.setInt(1, article.getArticlePhoto().getPhoto().getId());
	        ps.setInt(2, article.getId());
	        ps.execute();

	        connection.commit();
	        connection.setAutoCommit(true);
	        JdbcConnector.closeConnection();
	    }
	  
//	  public List<Article> getArticlesByLibelle(String libelle) throws Exception {
//		  Connection connection = JdbcConnector.getConnection();
//		  String sql = "SELECT * from Article where libelle like '%"+libelle+"%'";
//		  PreparedStatement ps = connection.prepareStatement(sql) ;
//		  ResultSet rs = ps.executeQuery() ;
//		  
//		  List<Article> articles = new ArrayList<Article>();
//	        while (rs.next()) {
//	            Article article = new Article();
//	            article.setId(rs.getInt("id_article")) ;
//	            article.setLibelle(rs.getString("libelle")) ;
//	            article.setType(rs.getString("type")) ;
//	            article.setRace(rs.getString("race")) ;
//	            articles.add(article) ;
//	        }
//	        
//	        rs.close();
//	        JdbcConnector.closeConnection();
//	        return articles;
//	  }

	  public List<Article> getArticles() throws Exception {
	        Connection connection = JdbcConnector.getConnection();
	        String sql = "SELECT * "
	                + "FROM article a "
	                + "LEFT JOIN article_photo aa ON aa.article=a.id_article "
	                + "LEFT JOIN photo ph ON aa.photo=ph.id_photo "
	                + "RIGHT JOIN tarif t ON t.id_tarif=a.tarif "
	                + "WHERE a.id_article IS NOT NULL "
	                + "ORDER BY a.id_article";
	        PreparedStatement ps = connection.prepareStatement(sql);
	        ResultSet rs = ps.executeQuery();

	        List<Article> articles = new ArrayList<Article>();
	        while (rs.next()) {
	            Article article = new Article();
	            Tarif tarif = new Tarif();
	            ArticlePhoto aa = new ArticlePhoto();
	            Photo photo = new Photo();

	            photo.setLibelle(rs.getString("ph.libelle"));
	            photo.setLien(rs.getString("lien"));
	            aa.setPhoto(photo);

	            tarif.setId(rs.getInt("id_tarif"));
	            tarif.setTarif1(rs.getDouble("t.tarif1"));
	            tarif.setTarif2(rs.getDouble("t.tarif2"));
	            tarif.setTarif3(rs.getDouble("t.tarif3"));
	            tarif.setTarif4(rs.getDouble("t.tarif4"));

	            article.setId(rs.getInt("id_article"));
	            article.setLibelle(rs.getString("a.libelle"));
	            article.setType(rs.getString("a.type"));
	            article.setRace(rs.getString("a.race"));
	            article.setTarif(tarif);
	            article.setArticlePhoto(aa);
	            articles.add(article);
	        }

	        rs.close();
	        JdbcConnector.closeConnection();
	        return articles;
	    }
    
    public void delete(Integer id) throws Exception {
        Connection connection = JdbcConnector.getConnection();
        connection.setAutoCommit(false);

        // Éliminer le lien vers la photo
        String sql = "DELETE FROM article_photo WHERE article=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.execute();

        // Éliminer article
        sql = "DELETE FROM article WHERE id_article=?";
        ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.execute();

        connection.commit();
        connection.setAutoCommit(true);
        JdbcConnector.closeConnection();
    }
    
    public Article getById(Integer id) throws Exception {
        Connection connection = JdbcConnector.getConnection();
        String sql = "SELECT * FROM article a "
                + "LEFT JOIN article_photo aa ON aa.article=a.id_article "
                + "WHERE a.id_article=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        rs.next();

        Article article = new Article();
        Tarif tarif = new Tarif();
        ArticlePhoto aa = new ArticlePhoto();
        Photo photo = new Photo();
        
        photo.setId(rs.getInt("photo"));
        aa.setPhoto(photo);

        tarif.setId(rs.getInt("tarif"));

        article.setId(rs.getInt("id_article"));
        article.setLibelle(rs.getString("libelle"));
        article.setType(rs.getString("type"));
        article.setRace(rs.getString("race"));
        article.setTarif(tarif);
        article.setArticlePhoto(aa);

        rs.close();
        JdbcConnector.closeConnection();
        return article;
    }
    
    

}
