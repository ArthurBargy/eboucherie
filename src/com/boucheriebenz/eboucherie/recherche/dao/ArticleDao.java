package com.boucheriebenz.eboucherie.recherche.dao;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;

import com.boucheriebenz.eboucherie.recherche.model.Article;
import com.boucheriebenz.eboucherie.recherche.model.ArticlePhoto;
import com.boucheriebenz.eboucherie.recherche.model.ArticlePhotoId;

public class ArticleDao  {
//TODO ajouter les log 4j
	private static final Log log = LogFactory.getLog(ArticleDao.class);
	
	public static List<Article> getArticlesByLibelle(String libelle) {
		if (null == libelle) {
			throw new IllegalArgumentException("Libelle est null.");
		}
		Session session = HibernateSessionFactory.currentSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			System.out.println("get Article with libelle: "+libelle);
			// create a new criteria
			Criteria crit = session.createCriteria(Article.class);
			
			List<Article> lArticles=crit.add(Expression.like("libelle", "%"+libelle+"%").ignoreCase()).list();
			
			return lArticles;
		}
		 catch(Exception e)
	        {
	            e.printStackTrace();
	            return null;
	        }
	        finally{
	        	HibernateSessionFactory.closeSession();
	        }
	}
	public static List<Article> getListArticles()
    {
		Session session = HibernateSessionFactory.currentSession();
        Transaction transaction = null;
        try
        {
            transaction = session.beginTransaction();
            List<Article> aList = session.createQuery("from Article").list();
            for(Iterator<Article> iterator = aList.iterator(); iterator.hasNext();)
            {
            	Article a = (Article) iterator.next();
            	System.out.println("Libelle Article - "+a.getLibelle());
            }
            return aList;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
        finally{
        	HibernateSessionFactory.closeSession();
        }
    }
        public static List<Article> getListArticles(String debutMot)
        {
        	Session session = HibernateSessionFactory.currentSession();
        	Transaction transaction = null;
        	try
        	{
        		transaction = session.beginTransaction();
        		Criteria crit = session.createCriteria(Article.class);
        		
        		List<Article> lArticles=crit.add(Expression.like("libelle", debutMot+"%").ignoreCase()).list();
        		for(Iterator<Article> iterator = lArticles.iterator(); iterator.hasNext();)
        		{
        			Article a = (Article) iterator.next();
        			System.out.println("Libelle Article - "+a.getLibelle());
        		}
        		return lArticles;
        	}
        	catch(Exception e)
        	{
        		e.printStackTrace();
        		return null;
        	}
        	finally{
        		HibernateSessionFactory.closeSession();
        	}
        
    }
        public static Article findById(int idArticle) {
    		System.out.println("getting ArticlePhoto instance with id: " + idArticle);
    		Session session = HibernateSessionFactory.currentSession();
    		Transaction transaction = null;
    		try {
    			transaction = session.beginTransaction();
    			Query query = session.createQuery("select a from Article as a where a.idArticle=:id");
    	        query.setLong("id", idArticle);
    	        //TODO verifier qu'un article a une ou plusieurs photos
    	        Article article = (Article) query.uniqueResult();
    	        
    			if (article == null) {
    				System.out.println("get successful, no instance found");
    			} else {
    				System.out.println("get successful, instance found");
    			}
    			return article;
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
