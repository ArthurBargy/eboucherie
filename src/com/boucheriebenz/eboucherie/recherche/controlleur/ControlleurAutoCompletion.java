package com.boucheriebenz.eboucherie.recherche.controlleur;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.boucheriebenz.eboucherie.recherche.dao.ArticleDao;
import com.boucheriebenz.eboucherie.recherche.model.Article;

/**
 * Servlet implementation class ControlleurAutoCompletion
 */
public class ControlleurAutoCompletion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ControlleurAutoCompletion() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//on r�cup�re le ou les mots-cl�s saisis 
				String mots = request.getParameter("q");
				if(null!=mots&&!mots.isEmpty()){
					mots=mots.toLowerCase();
					
				}
				PrintWriter out = response.getWriter();
		        response.setContentType("text/html");
		        response.setHeader("Cache-control", "no-cache, no-store");
		        response.setHeader("Pragma", "no-cache");
		        response.setHeader("Expires", "-1");
		 
		        response.setHeader("Access-Control-Allow-Origin", "*");
		        response.setHeader("Access-Control-Allow-Methods", "POST");
		        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
		        response.setHeader("Access-Control-Max-Age", "86400");
		        
		        List<Article> lArticles=ArticleDao.getListArticles(mots) ;				
		        for (Article arr : lArticles) {
		        	if (!arr.getLibelle().equals("article_non_trouve")) {
		        		
		        		out.println((String) arr.getLibelle());
		        	}
					}
		        
		        out.close();
				}
}
