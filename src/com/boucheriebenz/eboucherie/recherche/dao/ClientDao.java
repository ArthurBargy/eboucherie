package com.boucheriebenz.eboucherie.recherche.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.boucheriebenz.eboucherie.recherche.model.Client;

public class ClientDao {
	
	public static List<Client> getClientByName(String name) {
		if (null == name) {
			throw new IllegalArgumentException("name null.");
		}
		Session session = HibernateSessionFactory.currentSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			Query query = session.createQuery("select c from Client as c where c.nom like ? OR c.prenom like ?");
	        query.setString(0, "%"+name+"%");
	        query.setString(1, "%"+name+"%");
	        
	        //TODO verifier qu'un article a une ou plusieurs photos
	        List<Client> clients = (List<Client>) query.list();
	        
			if (clients.size()== 0) {
				System.out.println("get successful, no instance found");
				
			} else {
				System.out.println("get successful, instance found");
			}
			return clients;
		}catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
        finally{
        	HibernateSessionFactory.closeSession();
        }
	}
	
	public static Client getClientByID(int idC) {
		Session session = HibernateSessionFactory.currentSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			Query query = session.createQuery("select a from Client as a where a.idClient=:id");
	        query.setLong("id", idC);
	        
	        //TODO verifier qu'un article a une ou plusieurs photos
	        Client client = (Client) query.uniqueResult();
	        
			if (client==null) {
				System.out.println("get successful, no instance found");
				
			} else {
				System.out.println("get successful, instance found");
			}
			return client;
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
