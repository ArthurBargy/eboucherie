package com.boucheriebenz.eboucherie.recherche.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactory {
	 private static final SessionFactory sessionFactory;

	 static {
	   try {
	   // Cr�e la SessionFactory
	   sessionFactory =
		 new Configuration().configure().buildSessionFactory();
	   } catch (HibernateException ex) {
	   throw new RuntimeException("Probleme de configuration : "
	   + ex.getMessage(), ex);
	   }
	   }

	 public static final ThreadLocal session = new ThreadLocal();

	 public static Session currentSession()
			throws HibernateException {
	   Session s = (Session) session.get();
	   // Ouvre une nouvelle Session, si ce Thread n'en a aucune
	   if (s == null) {
	   s = sessionFactory.openSession();
	   session.set(s);
	   }
	   return s;
	   }

	 public static void closeSession()
			throws HibernateException {
	   Session s = (Session) session.get();
	   session.set(null);
	   if (s != null)
	   s.close();
	   }
}