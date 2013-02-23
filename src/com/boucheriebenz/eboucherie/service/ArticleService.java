package com.boucheriebenz.eboucherie.service;

import java.sql.Connection;
import java.sql.Date;
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
	        
	        Date debut = null ;
	        Date fin = null ;

	        // Ins�rer un article
	        String sql = "INSERT INTO article(libelle, type, race,description, date_debut, date_fin, en_ligne) " +
	                "VALUES(?,?,?,?,?,?,?)";
	        PreparedStatement ps = connection.prepareStatement(sql,
	                Statement.RETURN_GENERATED_KEYS);
	        ps.setString(1, article.getLibelle());
	        ps.setString(2, article.getType());
	        ps.setString(3, article.getRace());
	        ps.setString(4, article.getDescription()) ;
	        if (article.getDebut() != null){
	        	debut = new Date(article.getDebut().getTime()) ;
	        }
	        ps.setDate(5, debut);
	        if (article.getFin() != null){
	        	fin = new Date(article.getFin().getTime()) ;
	        }
	        ps.setDate(6, fin) ;
	        ps.setBoolean(7, article.getEnLigne()) ;
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
	        
	        Date debut = null ;
	        Date fin = null ;

	        // Actualiser article
	        String sql = "UPDATE article SET libelle=?,type=?,race=?,description=?,date_debut=?,date_fin=?,en_ligne=?" +
	                "WHERE id_article=?";
	        PreparedStatement ps = connection.prepareStatement(sql);
	        ps.setString(1, article.getLibelle());
	        ps.setString(2, article.getType());
	        ps.setString(3, article.getRace());
	        ps.setString(4, article.getDescription()) ;
	        if (article.getDebut() != null){
	        	debut = new Date(article.getDebut().getTime()) ;
	        }
	        ps.setDate(5, debut);
	        if (article.getFin() != null){
	        	fin = new Date(article.getFin().getTime()) ;
	        }
	        ps.setDate(6, fin) ;
	        ps.setBoolean(7, article.getEnLigne()) ;
	        ps.setInt(8, article.getId()) ;
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
	  

	  public List<Article> getArticles() throws Exception {
	        Connection connection = JdbcConnector.getConnection();
	        String sql = "SELECT * "
	                + "FROM article a "
	                + "LEFT JOIN article_photo aa ON aa.article=a.id_article "
	                + "LEFT JOIN photo ph ON aa.photo=ph.id_photo "
	                + "WHERE a.id_article IS NOT NULL "
	                + "ORDER BY a.id_article";
	        PreparedStatement ps = connection.prepareStatement(sql);
	        ResultSet rs = ps.executeQuery();

	        List<Article> articles = new ArrayList<Article>();
	        while (rs.next()) {
	            Article article = new Article();
	            ArticlePhoto aa = new ArticlePhoto();
	            Photo photo = new Photo();

	            photo.setLibelle(rs.getString("ph.libelle"));
	            photo.setLien(rs.getString("lien"));
	            aa.setPhoto(photo);

	            article.setId(rs.getInt("id_article"));
	            article.setLibelle(rs.getString("a.libelle"));
	            article.setType(rs.getString("a.type"));
	            article.setRace(rs.getString("a.race"));
	            article.setDescription(rs.getString("a.description")) ;
	            article.setDebut(rs.getDate("a.date_debut")) ;
	            article.setFin(rs.getDate("a.date_fin")) ;
	            article.setArticlePhoto(aa);
	            article.setEnLigne(rs.getBoolean("en_ligne")) ;
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
        ArticlePhoto aa = new ArticlePhoto();
        Photo photo = new Photo();
        
        photo.setId(rs.getInt("photo"));
        aa.setPhoto(photo);

        article.setId(rs.getInt("id_article"));
        article.setLibelle(rs.getString("a.libelle"));
        article.setType(rs.getString("a.type"));
        article.setRace(rs.getString("a.race"));
        article.setDescription(rs.getString("a.description")) ;
        article.setDebut(rs.getDate("a.date_debut")) ;
        article.setFin(rs.getDate("a.date_fin")) ;
        article.setArticlePhoto(aa);
        article.setEnLigne(rs.getBoolean("en_ligne")) ;
        article.setArticlePhoto(aa);

        rs.close();
        JdbcConnector.closeConnection();
        return article;
    }
    
    

}
