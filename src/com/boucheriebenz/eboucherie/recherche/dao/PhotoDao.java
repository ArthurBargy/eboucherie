package com.boucheriebenz.eboucherie.recherche.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.boucheriebenz.eboucherie.recherche.model.Photo;

public class PhotoDao {
	public static Photo findById(int idPhoto) {
		System.out.println("getting ArticlePhoto instance with id: " + idPhoto);
		Session session = HibernateSessionFactory.currentSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			Query query = session.createQuery("select p from Photo as p where p.idPhoto=:id");
	        query.setLong("id", idPhoto);
	        Photo photo = (Photo) query.uniqueResult();
	        
			if (photo == null) {
				System.out.println("get successful, no instance found");
			} else {
				System.out.println("get successful, instance found");
			}
			return photo;
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
