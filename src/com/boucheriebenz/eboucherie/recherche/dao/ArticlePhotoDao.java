package com.boucheriebenz.eboucherie.recherche.dao;

// Generated 22 janv. 2013 16:11:59 by Hibernate Tools 3.4.0.CR1

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.boucheriebenz.eboucherie.recherche.model.ArticlePhotoId;

/**
 * Home object for domain model class ArticlePhoto.
 * @see dao.ArticlePhoto
 * @author Hibernate Tools
 */
public class ArticlePhotoDao {


	public static ArticlePhotoId findByIdArticle(int idArticle) {
		System.out.println("getting ArticlePhoto instance with id: " + idArticle);
		Session session = HibernateSessionFactory.currentSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			Query query = session.createQuery("select a from ArticlePhotoId as a where a.article=:id");
	        query.setLong("id", idArticle);
	        //TODO verifier qu'un article a une ou plusieurs photos
	        ArticlePhotoId articlePhotoId = (ArticlePhotoId) query.uniqueResult();
	        
			if (articlePhotoId == null) {
				System.out.println("get successful, no instance found");
				
			} else {
				System.out.println("get successful, instance found");
			}
			return articlePhotoId;
		}catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
        finally{
        	HibernateSessionFactory.closeSession();
        }
		
	}

}
